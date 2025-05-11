package aiss.bitbucketminer1.model.GitMiner;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @Test
    public void testGettersAndSetters() {
        Project project = new Project();
        project.setId("1");
        project.setName("Test Project");
        project.setWebUrl("http://web.url");
        Commit commit = new Commit();
        Issue issue = new Issue();
        project.setCommits(List.of(commit));
        project.setIssues(List.of(issue));

        assertEquals("1", project.getId());
        assertEquals("Test Project", project.getName());
        assertEquals("http://web.url", project.getWebUrl());
        assertEquals(1, project.getCommits().size());
        assertEquals(1, project.getIssues().size());
    }

    @Test
    public void testToStringNotNull() {
        Project project = new Project();
        project.setId("1");
        project.setName("Test Project");
        String str = project.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("commits"));
        assertTrue(str.contains("issues"));
    }
}
