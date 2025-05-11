
package aiss.bitbucketminer1.model.BitBucket.issues;

import java.util.LinkedHashMap;
import java.util.Map;

import aiss.bitbucketminer1.model.BitBucket.user.UserJava;
import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssuesJava {

    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("repository")
    private Repository repository;
    @JsonProperty("links")
    private Links links;
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private Content content;
    @JsonProperty("reporter")
    private UserJava reporter;
    @JsonProperty("assignee")
    private UserJava assignee;
    @JsonProperty("created_on")
    private String createdOn;
    @JsonProperty("updated_on")
    private String updatedOn;
    @JsonProperty("state")
    private String state;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("milestone")
    private LinksMilestone milestone;
    @JsonProperty("component")
    private LinksMilestone component;
    @JsonProperty("priority")
    private String priority;
    @JsonProperty("votes")
    private Integer votes;
    @JsonProperty("watches")
    private Integer watches;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("repository")
    public Repository getRepository() {
        return repository;
    }

    @JsonProperty("repository")
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @JsonProperty("links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(Links links) {
        this.links = links;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    @JsonProperty("reporter")
    public UserJava getReporter() {
        return reporter;
    }

    @JsonProperty("reporter")
    public void setReporter(UserJava reporter) {
        this.reporter = reporter;
    }

    @JsonProperty("assignee")
    public UserJava getAssignee() {
        return assignee;
    }

    @JsonProperty("assignee")
    public void setAssignee(UserJava assignee) {
        this.assignee = assignee;
    }

    @JsonProperty("created_on")
    public String getCreatedOn() {
        return createdOn;
    }

    @JsonProperty("created_on")
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @JsonProperty("updated_on")
    public String getUpdatedOn() {
        return updatedOn;
    }

    @JsonProperty("updated_on")
    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("milestone")
    public LinksMilestone getMilestone() {
        return milestone;
    }

    @JsonProperty("milestone")
    public void setMilestone(LinksMilestone milestone) {
        this.milestone = milestone;
    }

    @JsonProperty("component")
    public LinksMilestone getComponent() {
        return component;
    }

    @JsonProperty("component")
    public void setComponent(LinksMilestone component) {
        this.component = component;
    }

    @JsonProperty("priority")
    public String getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @JsonProperty("votes")
    public Integer getVotes() {
        return votes;
    }

    @JsonProperty("votes")
    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @JsonProperty("watches")
    public Integer getWatches() {
        return watches;
    }

    @JsonProperty("watches")
    public void setWatches(Integer watches) {
        this.watches = watches;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
