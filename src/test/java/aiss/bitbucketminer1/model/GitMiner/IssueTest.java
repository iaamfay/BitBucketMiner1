package aiss.bitbucketminer1.model.GitMiner;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class IssueTest {

    @Test
    public void testGettersAndSetters() {
        Issue issue = new Issue();
        issue.setId("1");
        issue.setTitle("Test Issue");
        issue.setDescription("desc");
        issue.setState("open");
        issue.setCreatedAt("2024-01-01");
        issue.setUpdatedAt("2024-01-02");
        issue.setClosedAt("2024-01-03");
        issue.setLabels(List.of("bug"));
        User user = new User();
        issue.setAuthor(user);
        issue.setAssignee(user);
        issue.setVotes(5);
        Comment comment = new Comment();
        issue.setComments(List.of(comment));

        assertEquals("1", issue.getId());
        assertEquals("Test Issue", issue.getTitle());
        assertEquals("desc", issue.getDescription());
        assertEquals("open", issue.getState());
        assertEquals("2024-01-01", issue.getCreatedAt());
        assertEquals("2024-01-02", issue.getUpdatedAt());
        assertEquals("2024-01-03", issue.getClosedAt());
        assertEquals(1, issue.getLabels().size());
        assertEquals(user, issue.getAuthor());
        assertEquals(user, issue.getAssignee());
        assertEquals(5, issue.getVotes());
        assertEquals(1, issue.getComments().size());
    }

    @Test
    public void testToStringNotNull() {
        Issue issue = new Issue();
        issue.setId("1");
        issue.setTitle("Test Issue");
        String str = issue.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("title"));
    }
}
