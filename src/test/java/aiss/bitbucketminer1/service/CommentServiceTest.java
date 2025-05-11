package aiss.bitbucketminer1.service;

import aiss.bitbucketminer1.model.BitBucket.comment.CommentJava;
import aiss.bitbucketminer1.model.BitBucket.comment.CommentsJavaContainer;
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

public class CommentServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CommentService commentService;

    public CommentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindCommentsFromIssueReturnsList() {
        CommentsJavaContainer container = new CommentsJavaContainer();
        container.setValues(Collections.emptyList());
        when(restTemplate.exchange(anyString(), any(), any(), eq(CommentsJavaContainer.class)))
                .thenReturn(ResponseEntity.ok(container));

        List<CommentJava> comments = commentService.findCommentsFromIssue(1, "w", "r", 1);
        assertNotNull(comments);
    }
}
