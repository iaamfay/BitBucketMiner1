package aiss.bitbucketminer1.model.GitMiner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {

    @Test
    public void testGettersAndSetters() {
        Comment comment = new Comment();
        comment.setId("1");
        comment.setBody("body");
        User user = new User();
        comment.setAuthor(user);
        comment.setCreatedAt("2024-01-01");
        comment.setUpdatedAt("2024-01-02");

        assertEquals("1", comment.getId());
        assertEquals("body", comment.getBody());
        assertEquals(user, comment.getAuthor());
        assertEquals("2024-01-01", comment.getCreatedAt());
        assertEquals("2024-01-02", comment.getUpdatedAt());
    }

    @Test
    public void testToStringNotNull() {
        Comment comment = new Comment();
        comment.setId("1");
        comment.setBody("body");
        String str = comment.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("body"));
    }
}
