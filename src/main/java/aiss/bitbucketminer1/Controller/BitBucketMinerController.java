package aiss.bitbucketminer1.Controller;


import aiss.bitbucketminer1.model.GitMiner.Project;
import aiss.bitbucketminer1.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class BitBucketMinerController {
    @Autowired
    ProjectService service;

    @Autowired
    RestTemplate restTemplate;

    // Esta URI debe apuntar a una app que realmente esté escuchando en 8081
    final String bitbucketMinerUri = "http://localhost:8081/bitbucketminer/v1/projects";

    // GET: devuelve datos construidos desde Bitbucket
    @GetMapping("/bitbucket/{workspace}/{repo}")
    public Project getData(@PathVariable String workspace,
                           @PathVariable String repo,
                           @RequestParam(defaultValue = "5") Integer nCommits,
                           @RequestParam(defaultValue = "5") Integer nIssues,
                           @RequestParam(defaultValue = "2") Integer maxPages) {
        return service.allData(workspace, repo, nCommits, nIssues, maxPages);
    }

    // POST: obtiene y reenvía los datos a otro backend o endpoint
    @PostMapping("/bitbucket/{workspace}/{repo}")
    public Project sendData(@PathVariable String workspace,
                            @PathVariable String repo,
                            @RequestParam(defaultValue = "5") Integer nCommits,
                            @RequestParam(defaultValue = "5") Integer nIssues,
                            @RequestParam(defaultValue = "2") Integer maxPages) {
        Project project = service.allData(workspace, repo, nCommits, nIssues, maxPages);

        HttpEntity<Project> request = new HttpEntity<>(project);
        ResponseEntity<Project> response = restTemplate.exchange(
                bitbucketMinerUri,
                HttpMethod.POST,
                request,
                Project.class
        );

        return response.getBody();
    }

    // Este es el endpoint receptor que evita el error 404
    @PostMapping("/bitbucketminer/v1/projects")
    public ResponseEntity<Project> receiveProject(@RequestBody Project project) {
        // Aquí puedes guardar el proyecto o procesarlo
        System.out.println("Proyecto recibido: " + project.getName());
        return ResponseEntity.ok(project);
    }
}
