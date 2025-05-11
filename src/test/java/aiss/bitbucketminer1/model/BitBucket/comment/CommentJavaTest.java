package aiss.bitbucketminer1.model.BitBucket.comment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommentJavaTest {

    @Test
    public void testGettersAndSetters() {
        CommentJava comment = new CommentJava();
        comment.setId(1);
        comment.setCreatedOn("2024-01-01");
        comment.setUpdatedOn("2024-01-02");
        Content content = new Content();
        content.setRaw("test content");
        comment.setContent(content);

        assertEquals(1, comment.getId());
        assertEquals("2024-01-01", comment.getCreatedOn());
        assertEquals("2024-01-02", comment.getUpdatedOn());
        assertEquals("test content", comment.getContent().getRaw());
    }
}
