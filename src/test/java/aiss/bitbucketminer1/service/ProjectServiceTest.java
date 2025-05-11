package aiss.bitbucketminer1.service;

import aiss.bitbucketminer1.model.BitBucket.project.ProjectJava;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProjectService projectService;

    public ProjectServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProjectReturnsProjectJava() {
        String workspace = "testWorkspace";
        String repo = "testRepo";
        ProjectJava mockProject = new ProjectJava();
        when(restTemplate.exchange(anyString(), any(), any(), eq(ProjectJava.class)))
                .thenReturn(ResponseEntity.ok(mockProject));

        ProjectJava result = projectService.getProject(workspace, repo);

        assertNotNull(result);
        verify(restTemplate, atLeastOnce()).exchange(anyString(), any(), any(), eq(ProjectJava.class));
    }

    @Test
    public void testGetProjectThrowsException() {
        when(restTemplate.exchange(anyString(), any(), any(), eq(ProjectJava.class)))
                .thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> {
            projectService.getProject("w", "r");
        });
    }
}
