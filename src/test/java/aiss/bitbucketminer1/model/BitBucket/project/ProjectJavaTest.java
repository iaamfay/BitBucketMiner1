package aiss.bitbucketminer1.model.BitBucket.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectJavaTest {

    @Test
    public void testGettersAndSetters() {
        ProjectJava pj = new ProjectJava();
        pj.setType("repo");
        pj.setFullName("workspace/repo");
        pj.setName("repo");
        pj.setSlug("repo-slug");
        pj.setDescription("desc");
        pj.setScm("git");
        pj.setWebsite("http://web");
        pj.setIsPrivate(true);
        pj.setForkPolicy("allow_forks");
        pj.setCreatedOn("2024-01-01");
        pj.setUpdatedOn("2024-01-02");
        pj.setSize(123);
        pj.setLanguage("Java");
        pj.setUuid("uuid");
        pj.setHasIssues(true);
        pj.setHasWiki(false);

        assertEquals("repo", pj.getType());
        assertEquals("workspace/repo", pj.getFullName());
        assertEquals("repo", pj.getName());
        assertEquals("repo-slug", pj.getSlug());
        assertEquals("desc", pj.getDescription());
        assertEquals("git", pj.getScm());
        assertEquals("http://web", pj.getWebsite());
        assertTrue(pj.getIsPrivate());
        assertEquals("allow_forks", pj.getForkPolicy());
        assertEquals("2024-01-01", pj.getCreatedOn());
        assertEquals("2024-01-02", pj.getUpdatedOn());
        assertEquals(123, pj.getSize());
        assertEquals("Java", pj.getLanguage());
        assertEquals("uuid", pj.getUuid());
        assertTrue(pj.getHasIssues());
        assertFalse(pj.getHasWiki());
    }
}
