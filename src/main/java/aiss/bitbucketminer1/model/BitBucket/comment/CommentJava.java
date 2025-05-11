
package aiss.bitbucketminer1.model.BitBucket.comment;

import java.util.LinkedHashMap;
import java.util.Map;

import aiss.bitbucketminer1.model.BitBucket.user.UserJava;
import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentJava {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("created_on")
    private String createdOn;
    @JsonProperty("updated_on")
    private String updatedOn;
    @JsonProperty("content")
    private Content content;
    @JsonProperty("user")
    private UserJava user;
    @JsonProperty("issue")
    private IssueInfo issueInfo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
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

    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    @JsonProperty("user")
    public UserJava getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(UserJava user) {
        this.user = user;
    }

    @JsonProperty("issue")
    public IssueInfo getIssue() {
        return issueInfo;
    }

    @JsonProperty("issue")
    public void setIssue(IssueInfo issueInfo) {
        this.issueInfo = issueInfo;
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
