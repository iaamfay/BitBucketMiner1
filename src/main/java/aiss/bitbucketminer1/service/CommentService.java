package aiss.bitbucketminer1.service;


import aiss.bitbucketminer1.model.GitMiner.Comment;
import aiss.bitbucketminer1.model.GitMiner.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    RestTemplate restTemplate;
    final String baseUri = "https://api.bitbucket.org/2.0/repositories/";

    public List<Comment> getNotes(String owner, String repo, String
            iid) {
        String uri = baseUri + owner + "/" + repo + "/issues/" + iid + "/comments";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer ");
        HttpEntity<String[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Comment[]> response = restTemplate.exchange(uri,
                HttpMethod.GET, request, Comment[].class);
        List<Comment> comments =
                Arrays.stream(response.getBody()).toList();
        mapAuthorValues(comments);
        return comments;
    }

    public void mapAuthorValues(List<Comment> comments) {
        for (Comment comment : comments) {
            if (comment != null) {
                User commentAuthor = comment.getUser();
                String commentAuthorUserName = commentAuthor.getLogin();
                String commentAuthorName = commentAuthor.getLogin();
                String commentAuthorWebUrl = commentAuthor.getHtmlUrl();
                comment.setAuthor(commentAuthor);
                commentAuthor.setUsername(commentAuthorUserName);
                commentAuthor.setName(commentAuthorName);
                commentAuthor.setWebUrl(commentAuthorWebUrl);
            }
        }
    }
}