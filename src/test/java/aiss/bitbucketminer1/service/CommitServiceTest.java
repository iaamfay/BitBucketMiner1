package aiss.bitbucketminer1.service;

import aiss.bitbucketminer1.model.BitBucket.commit.CommitJava;
import aiss.bitbucketminer1.model.BitBucket.commit.CommitJavaContainer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommitServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CommitService commitService;

    public CommitServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCommitsReturnsList() {
        CommitJavaContainer container = new CommitJavaContainer();
        container.setValues(Collections.emptyList());
        when(restTemplate.exchange(anyString(), any(), any(), eq(CommitJavaContainer.class)))
                .thenReturn(ResponseEntity.ok(container));

        List<CommitJava> commits = commitService.getAllCommits("w", "r");
        assertNotNull(commits);
    }

    @Test
    public void testGetNCommitsReturnsList() {
        CommitJavaContainer container = new CommitJavaContainer();
        container.setValues(Collections.emptyList());
        when(restTemplate.exchange(anyString(), any(), any(), eq(CommitJavaContainer.class)))
                .thenReturn(ResponseEntity.ok(container));

        List<CommitJava> commits = commitService.getNCommits("w", "r", 1, 1);
        assertNotNull(commits);
    }
}
