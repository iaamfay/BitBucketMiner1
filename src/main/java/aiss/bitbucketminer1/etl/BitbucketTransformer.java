package aiss.bitbucketminer1.etl;

import aiss.bitbucketminer1.model.BitBucket.comment.CommentJava;
import aiss.bitbucketminer1.model.BitBucket.commit.CommitJava;
import aiss.bitbucketminer1.model.BitBucket.issues.IssuesJava;
import aiss.bitbucketminer1.model.BitBucket.project.ProjectJava;
import aiss.bitbucketminer1.model.BitBucket.user.UserJava;
import aiss.bitbucketminer1.model.GitMiner.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BitbucketTransformer {

    public Project transformProject(ProjectJava bitbucketProject,
                                    List<CommitJava> bitbucketCommits,
                                    List<IssuesJava> bitbucketIssues) {
        if (bitbucketProject == null) {
            return null;
        }

        Project gitMinerProject = new Project();

        // Set basic project information
        gitMinerProject.setId(bitbucketProject.getUuid());
        gitMinerProject.setName(bitbucketProject.getName());

        // Set web URL from links if available
        if (bitbucketProject.getLinks() != null &&
                bitbucketProject.getLinks().getHtml() != null) {
            gitMinerProject.setWebUrl(bitbucketProject.getLinks().getHtml().getHref());
        }

        // Transform and set commits if available
        if (bitbucketCommits != null) {
            List<Commit> validCommits = bitbucketCommits.stream()
                    .filter(commit ->
                            commit.getHash() != null &&
                                    !commit.getHash().isEmpty() &&
                                    commit.getMessage() != null
                    )
                    .map(this::transformCommit)
                    .collect(Collectors.toList());
            gitMinerProject.setCommits(validCommits);
        }

        // Transform and set issues if available
        if (bitbucketIssues != null) {
            List<Issue> validIssues = bitbucketIssues.stream()
                    .filter(issue ->
                            issue.getTitle() != null &&
                                    !issue.getTitle().isEmpty() &&
                                    issue.getReporter() != null
                    )
                    .map(issue -> transformIssue(issue, CommentService.findCommentsFromIssue(issue.getId(), workspace, repo, maxPages))
                            .collect(Collectors.toList()));
            gitMinerProject.setIssues(validIssues);
        }

        return gitMinerProject;
    }

    // Transformación individual de un Commit
    public Commit transformCommit(CommitJava bitbucketCommit) {
        if (bitbucketCommit == null) {
            return null;
        }

        Commit gitMinerCommit = new Commit();

        // Set basic fields
        gitMinerCommit.setId(bitbucketCommit.getHash());
        gitMinerCommit.setMessage(bitbucketCommit.getMessage());
        gitMinerCommit.setAuthoredDate(bitbucketCommit.getDate());

        // Set title from summary if available, otherwise first line of message
        if (bitbucketCommit.getSummary() != null && bitbucketCommit.getSummary().getRaw() != null) {
            gitMinerCommit.setTitle(bitbucketCommit.getSummary().getRaw());
        } else if (bitbucketCommit.getMessage() != null) {
            gitMinerCommit.setTitle(bitbucketCommit.getMessage().split("\n")[0]);
        }

        // Set author information
        if (bitbucketCommit.getAuthor() != null) {
            gitMinerCommit.setAuthorName(bitbucketCommit.getAuthor().getRaw());
            // If author has email in format "Name <email@example.com>", parse it
            if (bitbucketCommit.getAuthor().getRaw() != null &&
                    bitbucketCommit.getAuthor().getRaw().contains("<") &&
                    bitbucketCommit.getAuthor().getRaw().contains(">")) {
                String rawAuthor = bitbucketCommit.getAuthor().getRaw();
                gitMinerCommit.setAuthorName(rawAuthor.substring(0, rawAuthor.indexOf("<")).trim());
                gitMinerCommit.setAuthorEmail(rawAuthor.substring(
                        rawAuthor.indexOf("<") + 1,
                        rawAuthor.indexOf(">")
                ).trim());
            }
        }

        // Set web URL from links if available
        if (bitbucketCommit.getLinks() != null &&
                bitbucketCommit.getLinks().getHtml() != null &&
                bitbucketCommit.getLinks().getHtml().getHref() != null) {
            gitMinerCommit.setWebUrl(bitbucketCommit.getLinks().getHtml().getHref());
        }

        return gitMinerCommit;
    }

    // Transformación individual de un Issue (usa el constructor con todos los campos requeridos)
    public Issue transformIssue(IssuesJava bitbucketIssue) {
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
    public User transformUser(UserJava bitbucketUser) {
        if (bitbucketUser == null) {
            return null;
        }

        User gitMinerUser = new User();

        // Set basic fields
        gitMinerUser.setId(bitbucketUser.getUuid());
        gitMinerUser.setUsername(bitbucketUser.getNickname()); // Using nickname as username
        gitMinerUser.setName(bitbucketUser.getDisplayName());

        // Set avatar URL if available
        if (bitbucketUser.getLinks() != null &&
                bitbucketUser.getLinks().getAvatar() != null &&
                bitbucketUser.getLinks().getAvatar().getHref() != null) {
            gitMinerUser.setAvatarUrl(bitbucketUser.getLinks().getAvatar().getHref());
        }

        // webUrl could be set from links.self.href if available
        if (bitbucketUser.getLinks() != null &&
                bitbucketUser.getLinks().getSelf() != null &&
                bitbucketUser.getLinks().getSelf().getHref() != null) {
            gitMinerUser.setWebUrl(bitbucketUser.getLinks().getSelf().getHref());
        }

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