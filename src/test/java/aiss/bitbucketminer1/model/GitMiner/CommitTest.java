package aiss.bitbucketminer1.model.GitMiner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommitTest {

    @Test
    public void testGettersAndSetters() {
        Commit commit = new Commit();
        commit.setId("1");
        commit.setTitle("title");
        commit.setMessage("message");
        commit.setAuthorName("author");
        commit.setAuthorEmail("author@email.com");
        commit.setAuthoredDate("2024-01-01");
        commit.setWebUrl("http://web.url");

        assertEquals("1", commit.getId());
        assertEquals("title", commit.getTitle());
        assertEquals("message", commit.getMessage());
        assertEquals("author", commit.getAuthorName());
        assertEquals("author@email.com", commit.getAuthorEmail());
        assertEquals("2024-01-01", commit.getAuthoredDate());
        assertEquals("http://web.url", commit.getWebUrl());
    }

    @Test
    public void testToStringNotNull() {
        Commit commit = new Commit();
        commit.setId("1");
        commit.setTitle("title");
        String str = commit.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("title"));
    }
}
