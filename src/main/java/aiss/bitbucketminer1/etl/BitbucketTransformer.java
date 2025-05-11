package aiss.bitbucketminer1.etl;

import aiss.bitbucketminer1.model.BitBucket.comment.CommentJava;
import aiss.bitbucketminer1.model.BitBucket.issues.IssuesJava;
import aiss.bitbucketminer1.model.GitMiner.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BitbucketTransformer {

    public Project transform(Project bitbucketProject) {
        Project gitMinerProject = new Project();

        // Mapeo básico del proyecto
        gitMinerProject.setId(bitbucketProject.getId());
        gitMinerProject.setName(bitbucketProject.getName());
        gitMinerProject.setWebUrl(bitbucketProject.getWebUrl());

        // Transformación de commits (filtrado y limpieza)
        List<Commit> validCommits = bitbucketProject.getCommits().stream()
                .filter(commit ->
                        commit.getId() != null &&
                                !commit.getId().isEmpty() &&
                                commit.getTitle() != null
                )
                .map(this::transformCommit)
                .collect(Collectors.toList());
        gitMinerProject.setCommits(validCommits);

        // Transformación de issues (filtrado y limpieza)
        List<Issue> validIssues = bitbucketProject.getIssues().stream()
                .filter(issue ->
                        issue.getTitle() != null &&
                                !issue.getTitle().isEmpty() &&
                                issue.getDescription() != null &&
                                issue.getAuthor() != null &&
                                issue.getAuthor().getUsername() != null
                )
                .map(this::transformIssue)
                .collect(Collectors.toList());
        gitMinerProject.setIssues(validIssues);

        return gitMinerProject;
    }

    // Transformación individual de un Commit
    private Commit transformCommit(Commit bitbucketCommit) {
        Commit gitMinerCommit = new Commit();
        gitMinerCommit.setId(bitbucketCommit.getId());
        gitMinerCommit.setTitle(bitbucketCommit.getTitle());
        gitMinerCommit.setMessage(bitbucketCommit.getMessage());
        gitMinerCommit.setAuthorName(bitbucketCommit.getAuthorName());
        gitMinerCommit.setAuthorEmail(bitbucketCommit.getAuthorEmail());
        gitMinerCommit.setAuthoredDate(bitbucketCommit.getAuthoredDate());
        gitMinerCommit.setCommittedDate(bitbucketCommit.getCommittedDate());
        gitMinerCommit.setWebUrl(bitbucketCommit.getWebUrl());
        return gitMinerCommit;
    }

    // Transformación individual de un Issue (usa el constructor con todos los campos requeridos)
    private Issue transformIssue(IssuesJava bitbucketIssue) {
        Issue gitMinerIssue = new Issue();
        gitMinerIssue.setId(bitbucketIssue.getId().toString());
        gitMinerIssue.setTitle(bitbucketIssue.getTitle());
        gitMinerIssue.setState(bitbucketIssue.getState());
        gitMinerIssue.setCreatedAt(bitbucketIssue.getCreatedOn());
        gitMinerIssue.setUpdatedAt(bitbucketIssue.getUpdatedOn());
        gitMinerIssue.setLabels(new ArrayList<String>(List.of(bitbucketIssue.getKind())));
        gitMinerIssue.setAuthor(transformUser(bitbucketIssue.getReporter()));
        if(gitMinerIssue.getAssignee()!=null)gitMinerIssue.setAssignee(transformUser(bitbucketIssue.getAssignee()));
        List<CommentJava> comments = CommentService.findCommentsFromIssue(bitbucketIssue.getId(), workspace,repo,maxPages);
        gitMinerIssue.setComments(comments.stream().map(this::transformComment).toList());

        return gitMinerIssue;
    }

    // Transformación de un User
    private User transformUser(User bitbucketUser) {
        if (bitbucketUser == null) return null;

        User gitMinerUser = new User();
        gitMinerUser.setId(bitbucketUser.getId());
        gitMinerUser.setUsername(
                bitbucketUser.getUsername() != null ?
                        bitbucketUser.getUsername() :
                        bitbucketUser.getLogin() // Fallback a 'login' si 'username' es null
        );
        gitMinerUser.setName(bitbucketUser.getName());
        gitMinerUser.setAvatarUrl(bitbucketUser.getAvatarUrl());
        gitMinerUser.setWebUrl(bitbucketUser.getWebUrl());
        return gitMinerUser;
    }

    // Transformación de un Comment
    private Comment transformComment(Comment bitbucketComment) {
        Comment gitMinerComment = new Comment();
        gitMinerComment.setId(bitbucketComment.getId());
        gitMinerComment.setBody(bitbucketComment.getBody());
        gitMinerComment.setCreatedAt(bitbucketComment.getCreatedAt());
        gitMinerComment.setUpdatedAt(bitbucketComment.getUpdatedAt());
        gitMinerComment.setAuthor(transformUser(bitbucketComment.getAuthor()));
        return gitMinerComment;
    }
}