package aiss.bitbucketminer1.etl;

import aiss.bitbucketminer1.model.BitBucket.commit.CommitJava;
import aiss.bitbucketminer1.model.BitBucket.issues.IssuesJava;
import aiss.bitbucketminer1.model.BitBucket.project.ProjectJava;
import aiss.bitbucketminer1.model.GitMiner.Project;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class BitbucketTransformerTest {

    private final BitbucketTransformer transformer = new BitbucketTransformer();

    @Test
    public void testTransformProjectReturnsNotNull() {
        ProjectJava pj = new ProjectJava();
        pj.setUuid("uuid");
        pj.setName("repo");
        Project result = transformer.transformProject(
            pj,
            Collections.emptyList(),
            Collections.emptyList(),
            "workspace",
            "repo",
            1
        );
        assertNotNull(result);
        assertEquals("uuid", result.getId());
        assertEquals("repo", result.getName());
    }

    @Test
    public void testTransformCommitReturnsNotNull() {
        CommitJava commitJava = new CommitJava();
        commitJava.setHash("h1");
        commitJava.setMessage("msg");
        commitJava.setDate("2024-01-01");
        assertNotNull(transformer.transformCommit(commitJava));
    }

    @Test
    public void testTransformIssueReturnsNotNull() {
        IssuesJava issueJava = new IssuesJava();
        issueJava.setId(1);
        issueJava.setTitle("title");
        issueJava.setState("open");
        issueJava.setKind("bug");
        assertNotNull(transformer.transformIssue(issueJava, Collections.emptyList()));
    }
}
