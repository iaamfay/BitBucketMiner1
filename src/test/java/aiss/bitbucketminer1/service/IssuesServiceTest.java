package aiss.bitbucketminer1.service;

import aiss.bitbucketminer1.model.BitBucket.issues.IssuesJava;
import aiss.bitbucketminer1.model.BitBucket.issues.IssuesJavaContainer;
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

public class IssuesServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private IssuesService issuesService;

    public IssuesServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNIssuesReturnsList() {
        IssuesJavaContainer container = new IssuesJavaContainer();
        container.setValues(Collections.emptyList());
        when(restTemplate.exchange(anyString(), any(), any(), eq(IssuesJavaContainer.class)))
                .thenReturn(ResponseEntity.ok(container));

        List<IssuesJava> issues = issuesService.nIssues("w", "r", 1, 1);
        assertNotNull(issues);
    }
}
