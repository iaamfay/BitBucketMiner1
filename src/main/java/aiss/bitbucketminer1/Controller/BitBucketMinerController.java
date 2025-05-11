package aiss.bitbucketminer1.Controller;

import aiss.bitbucketminer1.etl.BitbucketTransformer;
import aiss.bitbucketminer1.model.BitBucket.comment.CommentJava;
import aiss.bitbucketminer1.model.BitBucket.commit.CommitJava;
import aiss.bitbucketminer1.model.BitBucket.issues.IssuesJava;
import aiss.bitbucketminer1.model.GitMiner.Issue;
import aiss.bitbucketminer1.model.GitMiner.Project;
import aiss.bitbucketminer1.model.BitBucket.project.ProjectJava;
import aiss.bitbucketminer1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
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

    @Value("${gitminer.api.url:http://localhost:8081/gitminer/v1/projects}")
    private String gitMinerApiUrl;

    @GetMapping("/bitbucket/{workspace}/{repo}")
    public ResponseEntity<Project> getRepositoryData(
            @PathVariable String workspace,
            @PathVariable String repo,
            @RequestParam(defaultValue = "5") Integer nCommits,
            @RequestParam(defaultValue = "5") Integer nIssues,
            @RequestParam(defaultValue = "2") Integer maxPages
    ) {
        // Get raw data from services
        ProjectJava bitbucketProject = projectService.getProject(workspace, repo);
        List<CommitJava> bitbucketCommits = commitService.getNCommits(workspace, repo, nCommits, maxPages);
        List<IssuesJava> bitbucketIssues = issuesService.nIssues(workspace, repo, nIssues, maxPages);

        // Transform issues with comments
        List<Issue> transformedIssues = bitbucketIssues.stream()
                .map(issue -> {
                    List<CommentJava> comments = commentService.findCommentsFromIssue(
                            issue.getId(), workspace, repo, maxPages
                    );
                    return transformer.transformIssue(issue);
                })
                .collect(Collectors.toList());

        // Transform project
        Project gitMinerProject = transformer.transformProject(
                bitbucketProject,
                bitbucketCommits,
                bitbucketIssues
        );

        // Set the transformed issues
        gitMinerProject.setIssues(transformedIssues);

        return ResponseEntity.ok(gitMinerProject);
    }

    @PostMapping("/bitbucket/{workspace}/{repo}")
    public ResponseEntity<Project> sendRepositoryData(
            @PathVariable String workspace,
            @PathVariable String repo,
            @RequestParam(defaultValue = "5") Integer nCommits,
            @RequestParam(defaultValue = "5") Integer nIssues,
            @RequestParam(defaultValue = "2") Integer maxPages
    ) {
        // Get and transform data (same as GET endpoint)
        ResponseEntity<Project> response = getRepositoryData(
                workspace, repo, nCommits, nIssues, maxPages
        );
        Project gitMinerProject = response.getBody();

        // Send to GitMiner
        HttpEntity<Project> request = new HttpEntity<>(gitMinerProject);
        ResponseEntity<Project> gitMinerResponse = restTemplate.exchange(
                gitMinerApiUrl,
                HttpMethod.POST,
                request,
                Project.class
        );

        return ResponseEntity.ok(gitMinerResponse.getBody());
    }

    @PostMapping("/gitminer/v1/projects")
    public ResponseEntity<Project> receiveProject(@RequestBody Project project) {
        System.out.println("Project received: " + project.getName());
        return ResponseEntity.ok(project);
    }
}