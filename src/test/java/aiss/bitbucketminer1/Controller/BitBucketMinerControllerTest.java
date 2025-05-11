package aiss.bitbucketminer1.Controller;

import aiss.bitbucketminer1.model.GitMiner.Project;
import aiss.bitbucketminer1.service.ProjectService;
import aiss.bitbucketminer1.service.CommitService;
import aiss.bitbucketminer1.service.IssuesService;
import aiss.bitbucketminer1.service.CommentService;
import aiss.bitbucketminer1.etl.BitbucketTransformer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BitBucketMinerController.class)
public class BitBucketMinerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;
    @MockBean
    private CommitService commitService;
    @MockBean
    private IssuesService issuesService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private BitbucketTransformer transformer;

    @Test
    public void testGetRepositoryDataReturnsOk() throws Exception {
        when(transformer.transformProject(any(), any(), any(), anyString(), anyString(), anyInt()))
                .thenReturn(new Project());
        mockMvc.perform(get("/bitbucket/test/testrepo")
                .param("nCommits", "1")
                .param("nIssues", "1")
                .param("maxPages", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
