package aiss.bitbucketminer1.service;


import aiss.bitbucketminer1.model.BitBucket.issues.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class IssuesService {

    private final static String baseUri = "https://api.bitbucket.org/2.0/repositories";

    @Autowired
    RestTemplate restTemplate;

    @Value("${bitbucketminer.token}")
    private String token;

    @Value("${bitbucketminer.username}")
    private String username;

    public List<IssuesJava> sinceIssues(String workspace, String repo) {
        String auth = username + ":" + token;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader); // Añade la cabecera con el token
        HttpEntity<String> entity = new HttpEntity<>(headers); // Crea una entidad con las cabeceras
        String uri = baseUri + "/" + workspace + "/" + repo + "/issues";
        List<IssuesJava> issues = null;
        try {
            ResponseEntity<IssuesJavaContainer> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    IssuesJavaContainer.class
            );
            issues.addAll(Objects.requireNonNull(response.getBody()).getValues());

            while (response.getBody().getNext() != null) {
                uri = response.getBody().getNext();

                response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        entity,
                        IssuesJavaContainer.class
                );
                issues.addAll(Objects.requireNonNull(response.getBody()).getValues());
            }

            return issues;
        }catch(
                Exception e)

        {
            throw new RuntimeException(e);
        }
    }

    public List<IssuesJava> nIssues (String workspace, String repo, Integer x, Integer maxPages){
        String auth = username + ":" + token;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",  authHeader); // Añade la cabecera con el token
        HttpEntity<String> entity = new HttpEntity<>(headers); // Crea una entidad con las cabeceras
        String uri = baseUri + "/" + workspace + "/" + repo + "/issues?pagelen=" + x;
        List<IssuesJava> issues = new ArrayList<>();
        int pageCount = 0;
        try{
            while (uri != null && pageCount < maxPages) {
                ResponseEntity<IssuesJavaContainer> response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        entity,
                        IssuesJavaContainer.class
                );
                IssuesJavaContainer body = response.getBody();
                if (body != null) {
                    if (body.getValues() != null) {
                        issues.addAll(body.getValues());
                    }
                    uri = body.getNext();
                    pageCount++;
                } else {
                    break;
                }
            }

        } catch (Exception e){
            throw new RuntimeException(e);
        }

        return issues;
    }
}
