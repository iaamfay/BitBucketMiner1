package aiss.bitbucketminer1.model.BitBucket.commit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommitJavaTest {

    @Test
    public void testGettersAndSetters() {
        CommitJava commit = new CommitJava();
        commit.setType("commit");
        commit.setHash("abc123");
        commit.setDate("2024-01-01");
        commit.setMessage("Initial commit");

        assertEquals("commit", commit.getType());
        assertEquals("abc123", commit.getHash());
        assertEquals("2024-01-01", commit.getDate());
        assertEquals("Initial commit", commit.getMessage());
    }
}
