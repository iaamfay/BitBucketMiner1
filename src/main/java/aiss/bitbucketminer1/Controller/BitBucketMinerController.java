package aiss.bitbucketminer1.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import aiss.bitbucketminer1.etl.BitbucketTransformer;
import aiss.bitbucketminer1.model.BitBucket.comment.CommentJava;
import aiss.bitbucketminer1.model.BitBucket.commit.CommitJava;
import aiss.bitbucketminer1.model.BitBucket.issues.IssuesJava;
import aiss.bitbucketminer1.model.BitBucket.project.ProjectJava;
import aiss.bitbucketminer1.model.GitMiner.Issue;
import aiss.bitbucketminer1.model.GitMiner.Project;
import aiss.bitbucketminer1.service.CommentService;
import aiss.bitbucketminer1.service.CommitService;
import aiss.bitbucketminer1.service.IssuesService;
import aiss.bitbucketminer1.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "BitBucketMiner", description = "BitBucket data collector API")
@RestController
public class BitBucketMinerController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CommitService commitService;

    @Autowired
    private IssuesService issuesService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BitbucketTransformer transformer;

    @Autowired
    private RestTemplate restTemplate;

    final String gitMinerApiUrl = "http://localhost:8081/bitbucketminer/v1/projects";

    @GetMapping("/bitbucket/{workspace}/{repo}")
    @Operation(
            summary = "Retrieves data from BitBucket",
            description = "Creates a project from BitBucket's data",
            tags ={"Projects","GET"})
    @ApiResponses({
            @ApiResponse(responseCode = "200",content = {@Content(schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "404",content = {@Content(schema = @Schema())})
    })

    public ResponseEntity<Project> getRepositoryData(
            @PathVariable String workspace,
            @PathVariable String repo,
            @Parameter(description = "number of commits,default 5")
            @RequestParam(defaultValue = "5") Integer nCommits,
            @Parameter(description = "number of issues,default 5")
            @RequestParam(defaultValue = "5") Integer nIssues,
            @Parameter(description = "maximum of pages,default 2")
            @RequestParam(defaultValue = "2") Integer maxPages) {
        // Get raw data from services
        ProjectJava bitbucketProject = projectService.getProject(workspace, repo);
        List<CommitJava> bitbucketCommits = commitService.getNCommits(workspace, repo, nCommits, maxPages);
        List<IssuesJava> bitbucketIssues = issuesService.nIssues(workspace, repo, nIssues, maxPages);


        List<Issue> transformedIssues = bitbucketIssues.stream()
                .map(issue -> {
                    List<CommentJava> comments = commentService.findCommentsFromIssue(
                            issue.getId(), workspace, repo, maxPages
                    );
                    return transformer.transformIssue(issue, comments);
                })
                .collect(Collectors.toList());


        Project gitMinerProject = transformer.transformProject(
                bitbucketProject,
                bitbucketCommits,
                bitbucketIssues,
                workspace,
                repo,
                maxPages
        );


        gitMinerProject.setIssues(transformedIssues);

        return ResponseEntity.ok(gitMinerProject);
    }

    @PostMapping("/bitbucket/{workspace}/{repo}")
   @Operation(
            summary = "Obtains and resends data to another endpoint or backend",
            description = "Creates a project from BitBucket's data and sends it",
            tags ={"Projects","POST"})
    @ApiResponses({
            @ApiResponse(responseCode = "200",content = {@Content(schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "404",content = {@Content(schema = @Schema())})
    })
    public ResponseEntity<Project> sendRepositoryData(
            @PathVariable String workspace,
            @PathVariable String repo,
            @Parameter(description = "number of commits,default 5")
            @RequestParam(defaultValue = "5") Integer nCommits,
            @Parameter(description = "number of issues,default 5")
            @RequestParam(defaultValue = "5") Integer nIssues,
            @Parameter(description = "maximum of pages,default 2")
            @RequestParam(defaultValue = "2") Integer maxPages) {

        ResponseEntity<Project> response = getRepositoryData(
                workspace, repo, nCommits, nIssues, maxPages
        );
        Project gitMinerProject = response.getBody();


        HttpEntity<Project> request = new HttpEntity<>(gitMinerProject);
        ResponseEntity<Project> gitMinerResponse = restTemplate.exchange(
                gitMinerApiUrl,
                HttpMethod.POST,
                request,
                Project.class
        );

        return ResponseEntity.ok(gitMinerResponse.getBody());
    }
    @Operation(
            summary = "Obtains a Project",
            tags ={"Projects","POST"})
    @ApiResponses({
            @ApiResponse(responseCode = "200",content = {@Content(schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "404",content = {@Content(schema = @Schema())})
    })
    @PostMapping("/bitbucketminer/v1/projects")
    public ResponseEntity<Project> receiveProject(@RequestBody Project project) {
        System.out.println("Project received: " + project.getName());
        return ResponseEntity.ok(project);
    }
}