package aiss.bitbucketminer1.etl;

import aiss.bitbucketminer1.model.BitBucket.comment.CommentJava;
import aiss.bitbucketminer1.model.BitBucket.commit.CommitJava;
import aiss.bitbucketminer1.model.BitBucket.issues.IssuesJava;
import aiss.bitbucketminer1.model.BitBucket.project.ProjectJava;
import aiss.bitbucketminer1.model.BitBucket.user.UserJava;
import aiss.bitbucketminer1.model.GitMiner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import aiss.bitbucketminer1.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BitbucketTransformer {
    private final CommentService commentService;

    @Autowired
    public BitbucketTransformer(CommentService commentService) {
        this.commentService = commentService;
    }

    public Project transformProject(ProjectJava bitbucketProject,
                                    List<CommitJava> bitbucketCommits,
                                    List<IssuesJava> bitbucketIssues,
                                    String workspace,
                                    String repo,
                                    Integer maxPages) {
        if (bitbucketProject == null) {
            return null;
        }

        Project gitMinerProject = new Project();


        gitMinerProject.setId(bitbucketProject.getUuid());
        gitMinerProject.setName(bitbucketProject.getName());


        if (bitbucketProject.getLinks() != null &&
                bitbucketProject.getLinks().getHtml() != null) {
            gitMinerProject.setWebUrl(bitbucketProject.getLinks().getHtml().getHref());
        }


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


        if (bitbucketIssues != null) {
            List<Issue> validIssues = bitbucketIssues.stream()
                    .filter(issue ->
                            issue.getTitle() != null &&
                                    !issue.getTitle().isEmpty() &&
                                    issue.getReporter() != null
                    )
                    .map(issue -> transformIssue(issue, commentService.findCommentsFromIssue(issue.getId(), workspace, repo, maxPages))).toList();
            gitMinerProject.setIssues(validIssues);
        }

        return gitMinerProject;
    }


    public Commit transformCommit(CommitJava bitbucketCommit) {
        if (bitbucketCommit == null) {
            return null;
        }

        Commit gitMinerCommit = new Commit();


        gitMinerCommit.setId(bitbucketCommit.getHash());
        gitMinerCommit.setMessage(bitbucketCommit.getMessage());
        gitMinerCommit.setAuthoredDate(bitbucketCommit.getDate());


        if (bitbucketCommit.getSummary() != null && bitbucketCommit.getSummary().getRaw() != null) {
            gitMinerCommit.setTitle(bitbucketCommit.getSummary().getRaw());
        } else if (bitbucketCommit.getMessage() != null) {
            gitMinerCommit.setTitle(bitbucketCommit.getMessage().split("\n")[0]);
        }


        if (bitbucketCommit.getAuthor() != null) {
            gitMinerCommit.setAuthorName(bitbucketCommit.getAuthor().getRaw());

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


        if (bitbucketCommit.getLinks() != null &&
                bitbucketCommit.getLinks().getHtml() != null &&
                bitbucketCommit.getLinks().getHtml().getHref() != null) {
            gitMinerCommit.setWebUrl(bitbucketCommit.getLinks().getHtml().getHref());
        }

        return gitMinerCommit;
    }


    public Issue transformIssue(IssuesJava bitbucketIssue, List<CommentJava> comments) {
        Issue gitMinerIssue = new Issue();
        gitMinerIssue.setId(bitbucketIssue.getId().toString());
        gitMinerIssue.setTitle(bitbucketIssue.getTitle());
        gitMinerIssue.setState(bitbucketIssue.getState());
        gitMinerIssue.setCreatedAt(bitbucketIssue.getCreatedOn());
        gitMinerIssue.setUpdatedAt(bitbucketIssue.getUpdatedOn());
        gitMinerIssue.setLabels(new ArrayList<String>(List.of(bitbucketIssue.getKind())));
        gitMinerIssue.setAuthor(transformUser(bitbucketIssue.getReporter()));
        if (bitbucketIssue.getAssignee() != null) {
            gitMinerIssue.setAssignee(transformUser(bitbucketIssue.getAssignee()));
        }
        gitMinerIssue.setComments(comments.stream().map(this::transformComment).toList());
        return gitMinerIssue;
    }


    public User transformUser(UserJava bitbucketUser) {
        if (bitbucketUser == null) {
            return null;
        }

        User gitMinerUser = new User();


        gitMinerUser.setId(bitbucketUser.getUuid());
        gitMinerUser.setUsername(bitbucketUser.getNickname());
        gitMinerUser.setName(bitbucketUser.getDisplayName());


        if (bitbucketUser.getLinks() != null &&
                bitbucketUser.getLinks().getAvatar() != null &&
                bitbucketUser.getLinks().getAvatar().getHref() != null) {
            gitMinerUser.setAvatarUrl(bitbucketUser.getLinks().getAvatar().getHref());
        }


        if (bitbucketUser.getLinks() != null &&
                bitbucketUser.getLinks().getSelf() != null &&
                bitbucketUser.getLinks().getSelf().getHref() != null) {
            gitMinerUser.setWebUrl(bitbucketUser.getLinks().getSelf().getHref());
        }

        return gitMinerUser;
    }


    public Comment transformComment(CommentJava bitbucketComment) {
        if (bitbucketComment == null) {
            return null;
        }

        Comment gitMinerComment = new Comment();


        gitMinerComment.setId(bitbucketComment.getId().toString());
        gitMinerComment.setCreatedAt(bitbucketComment.getCreatedOn());
        gitMinerComment.setUpdatedAt(bitbucketComment.getUpdatedOn());


        if (bitbucketComment.getContent() != null) {
            gitMinerComment.setBody(bitbucketComment.getContent().getRaw());
        }


        if (bitbucketComment.getUser() != null) {
            gitMinerComment.setAuthor(transformUser(bitbucketComment.getUser()));
        }

        return gitMinerComment;
    }
}