package aiss.bitbucketminer1.service;

import aiss.bitbucketminer1.model.BitBucket.commit.CommitJava;
import aiss.bitbucketminer1.model.BitBucket.commit.CommitJavaContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class CommitService {

    private final static String baseUri = "https://api.bitbucket.org/2.0/repositories";
    @Autowired
    RestTemplate restTemplate;

    public List<CommitJava> getAllCommits(String workspace, String repo) {
        String uri = baseUri + "/" + workspace + "/" + repo + "/commits";
        List<CommitJava> commits = new ArrayList<>();
        try {
            ResponseEntity<CommitJavaContainer> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    createAuthEntity(),
                    CommitJavaContainer.class
            );
            commits.addAll(Objects.requireNonNull(response.getBody()).getValues());

            while (response.getBody().getNext() != null) {
                uri = response.getBody().getNext();
                response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        createAuthEntity(),
                        CommitJavaContainer.class
                );
                commits.addAll(Objects.requireNonNull(response.getBody()).getValues());
            }

            return commits;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CommitJava> getNCommits(String workspace, String repo, Integer perPage, Integer maxPages) {
        String uri = baseUri + "/" + workspace + "/" + repo + "/commits?pagelen=" + perPage;
        List<CommitJava> commits = new ArrayList<>();
        int pageCount = 0;
        try {
            while (uri != null && pageCount < maxPages) {
                ResponseEntity<CommitJavaContainer> response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        createAuthEntity(),
                        CommitJavaContainer.class
                );
                CommitJavaContainer body = response.getBody();
                if (body != null) {
                    if (body.getValues() != null) {
                        commits.addAll(body.getValues());
                    }
                    uri = body.getNext();
                    pageCount++;
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return commits;
    }

    private HttpEntity<String> createAuthEntity() {
        String username = "secreto";
        String appPassword = "secreto";
        String auth = username + ":" + appPassword;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        return new HttpEntity<>(headers);
    }
}