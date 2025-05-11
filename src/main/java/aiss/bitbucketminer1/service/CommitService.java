package aiss.bitbucketminer1.service;


import aiss.bitbucketminer1.model.GitMiner.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommitService {

    @Autowired
    RestTemplate restTemplate;

    final String baseUri = "https://api.bitbucket.org/2.0/repositories/";

    public List<Commit> sinceCommits(String owner, String repo, Integer days, Integer pages) {
        LocalDate date = LocalDate.now().minusDays(days);
        String uri = baseUri + owner + "/" + repo + "/commits?since=" + date;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "); // Asegúrate de rellenar el token
        HttpEntity<Void> request = new HttpEntity<>(headers);

        List<Commit> commits = new ArrayList<>();
        int page = 0;

        while (page < pages && uri != null) {
            System.out.println("Requesting: " + uri);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<>() {}
            );

            Map<String, Object> body = response.getBody();
            if (body == null || !body.containsKey("values")) break;

            List<Map<String, Object>> rawCommits = (List<Map<String, Object>>) body.get("values");

            for (Map<String, Object> rawCommit : rawCommits) {
                Commit commit = mapRawCommit(rawCommit);
                commits.add(commit);
            }

            uri = (String) body.get("next"); // Bitbucket uses "next" for pagination
            page++;
        }

        return commits;
    }

    private Commit mapRawCommit(Map<String, Object> rawCommit) {
        Commit commit = new Commit();

        commit.setId((String) rawCommit.get("hash"));

        // Procesamos el autor del commit
        Map<String, Object> author = (Map<String, Object>) rawCommit.get("author");
        if (author != null) {
            Object userObject = author.get("user");
            if (userObject instanceof Map) {
                Map<String, Object> user = (Map<String, Object>) userObject;
                commit.setAuthorName((String) user.get("display_name"));
            } else {
                commit.setAuthorName((String) author.get("raw"));
            }
            commit.setAuthorEmail((String) author.get("raw"));
        }

        commit.setMessage((String) rawCommit.get("message"));
        String message = commit.getMessage();
        commit.setTitle(message != null && message.length() > 20 ? message.substring(0, 20) : message);

        // Procesamos los enlaces (links)
        Map<String, Object> links = (Map<String, Object>) rawCommit.get("links");
        if (links != null) {
            Object htmlLink = links.get("html");
            if (htmlLink instanceof Map) {
                Map<String, Object> htmlMap = (Map<String, Object>) htmlLink;
                commit.setWebUrl((String) htmlMap.get("href"));
            }
        }

        // Procesamos las fechas
        commit.setAuthoredDate((String) rawCommit.get("date"));
        commit.setCommittedDate((String) rawCommit.get("date")); // Bitbucket doesn't separate these

        return commit;
    }

    private void mapCommitValues(Commit commit) {
        String id = commit.getId();
        String message = commit.getMessage();
        String authorName = commit.getAuthorName();
        String authorEmail = commit.getAuthorEmail();
        String authoredDate = commit.getAuthoredDate();
        String committerName = commit.getCommitterName();
        String committerEmail = commit.getCommitterEmail();
        String committedDate = commit.getCommittedDate();
        String webUrl = commit.getWebUrl();

        commit.setId(id);
        commit.setMessage(message);

        commit.setTitle(message.length() > 20 ? message.substring(0, 20) : message);

        commit.setAuthorName(authorName);
        commit.setAuthorEmail(authorEmail);
        commit.setAuthoredDate(authoredDate);
        commit.setCommitterName(committerName);
        commit.setCommitterEmail(committerEmail);
        commit.setCommittedDate(committedDate);
        commit.setWebUrl(webUrl);
    }
}
