package aiss.bitbucketminer1.model.BitBucket.issues;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IssuesJavaTest {

    @Test
    public void testGettersAndSetters() {
        IssuesJava issue = new IssuesJava();
        issue.setType("issue");
        issue.setId(1);
        issue.setTitle("Test Issue");
        issue.setState("open");
        issue.setKind("bug");
        issue.setPriority("high");
        issue.setVotes(10);
        issue.setWatches(5);
        issue.setCreatedOn("2024-01-01");
        issue.setUpdatedOn("2024-01-02");

        assertEquals("issue", issue.getType());
        assertEquals(1, issue.getId());
        assertEquals("Test Issue", issue.getTitle());
        assertEquals("open", issue.getState());
        assertEquals("bug", issue.getKind());
        assertEquals("high", issue.getPriority());
        assertEquals(10, issue.getVotes());
        assertEquals(5, issue.getWatches());
        assertEquals("2024-01-01", issue.getCreatedOn());
        assertEquals("2024-01-02", issue.getUpdatedOn());
    }
}
