package aiss.bitbucketminer1.service;

import aiss.bitbucketminer1.model.BitBucket.comment.CommentJava;
import aiss.bitbucketminer1.model.BitBucket.comment.CommentsJavaContainer;
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

@Service
public class CommentService {

    private final static String baseUri = "https://api.bitbucket.org/2.0/repositories";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bitbucketminer.token}")
    private String token;

    @Value("${bitbucketminer.username}")
    private String username;

    public List<CommentJava> findCommentsFromIssue(Integer issueId, String workspace, String repo, Integer maxPages) {
        String uri = baseUri + "/" + workspace + "/" + repo + "/issues/" + issueId + "/comments";
        List<CommentJava> comments = new ArrayList<>();
        int pageCount = 0;
        try {
            while (uri != null && (maxPages == null || pageCount < maxPages)) {
                ResponseEntity<CommentsJavaContainer> response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        createAuthEntity(),
                        CommentsJavaContainer.class
                );
                CommentsJavaContainer body = response.getBody();
                if (body != null) {
                    if (body.getValues() != null) {
                        comments.addAll(body.getValues());
                    }
                    uri = body.getNext();
                    pageCount++;
                } else {
                    break;
                }
            }
            return comments;
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