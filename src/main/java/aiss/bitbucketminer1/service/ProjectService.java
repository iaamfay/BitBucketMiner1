package aiss.bitbucketminer1.service;

import aiss.bitbucketminer1.model.BitBucket.project.ProjectJava;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ProjectService {

    private final static String baseUri = "https://api.bitbucket.org/2.0/repositories";
    @Autowired
    RestTemplate restTemplate;

    @Value("${bitbucketminer.token}")
    private String token;

    @Value("${bitbucketminer.username}")
    private String username;

    public ProjectJava getProject(String workspace, String repo) {
        String uri = baseUri + "/" + workspace + "/" + repo;
        try {
            ResponseEntity<ProjectJava> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    createAuthEntity(),
                    ProjectJava.class
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HttpEntity<String> createAuthEntity() {
        String auth = username + ":" + token;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        return new HttpEntity<>(headers);
    }
}