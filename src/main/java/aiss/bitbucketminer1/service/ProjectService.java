package aiss.bitbucketminer1.service;


import aiss.bitbucketminer1.model.GitMiner.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProjectService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CommitService commitService;
    @Autowired
    IssuesService issueService;

    @Value("${bitbucketminer.baseuri}")
    private String baseUri;

    @Value("${bitbucketminer.token}")
    private String token;

    @Value("${bitbucketminer.username}")
    private String username;

    public Project getProjectByOwnerAndName(String owner, String repo) {
        String uri = baseUri + owner + "/" + repo;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, token);
        HttpEntity<Project> request = new HttpEntity<>(null, headers);

        try {
            ResponseEntity<Project> response = restTemplate.exchange(uri, HttpMethod.GET, request, Project.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el proyecto de Bitbucket: " + e.getMessage(), e);
        }
    }

    public Project allData(String owner, String repo, Integer sinceCommits, Integer sinceIssues, Integer maxPages) {
        Project data = getProjectByOwnerAndName(owner, repo);
        if (data == null) {
            throw new RuntimeException("No se pudo obtener datos del proyecto");
        }

        String webUrl = data.getHtmlUrl();
        data.setCommits(commitService.sinceCommits(owner, repo, sinceCommits, maxPages));
        data.setIssues(issueService.findAllIssues(owner, repo));
        data.setWebUrl(webUrl);
        return data;
    }
}