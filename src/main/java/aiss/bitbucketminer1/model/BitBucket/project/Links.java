
package aiss.bitbucketminer1.model.BitBucket.project;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import aiss.bitbucketminer1.model.BitBucket.common.Link;
import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "self",
    "html",
    "avatar",
    "pullrequests",
    "commits",
    "forks",
    "watchers",
    "branches",
    "tags",
    "downloads",
    "source",
    "clone",
    "issues",
    "hooks"
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {

    @JsonProperty("self")
    private Link self;
    @JsonProperty("html")
    private Link html;
    @JsonProperty("avatar")
    private Link avatar;
    @JsonProperty("pullrequests")
    private Link pullrequests;
    @JsonProperty("commits")
    private Link commits;
    @JsonProperty("forks")
    private Link forks;
    @JsonProperty("watchers")
    private Link watchers;
    @JsonProperty("branches")
    private Link branches;
    @JsonProperty("tags")
    private Link tags;
    @JsonProperty("downloads")
    private Link downloads;
    @JsonProperty("source")
    private Link source;
    @JsonProperty("clone")
    private List<Clone> clone;
    @JsonProperty("issues")
    private Link issues;
    @JsonProperty("hooks")
    private Link hooks;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("self")
    public Link getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(Link self) {
        this.self = self;
    }

    @JsonProperty("html")
    public Link getHtml() {
        return html;
    }

    @JsonProperty("html")
    public void setHtml(Link html) {
        this.html = html;
    }

    @JsonProperty("avatar")
    public Link getAvatar() {
        return avatar;
    }

    @JsonProperty("avatar")
    public void setAvatar(Link avatar) {
        this.avatar = avatar;
    }

    @JsonProperty("pullrequests")
    public Link getPullrequests() {
        return pullrequests;
    }

    @JsonProperty("pullrequests")
    public void setPullrequests(Link pullrequests) {
        this.pullrequests = pullrequests;
    }

    @JsonProperty("commits")
    public Link getCommits() {
        return commits;
    }

    @JsonProperty("commits")
    public void setCommits(Link commits) {
        this.commits = commits;
    }

    @JsonProperty("forks")
    public Link getForks() {
        return forks;
    }

    @JsonProperty("forks")
    public void setForks(Link forks) {
        this.forks = forks;
    }

    @JsonProperty("watchers")
    public Link getWatchers() {
        return watchers;
    }

    @JsonProperty("watchers")
    public void setWatchers(Link watchers) {
        this.watchers = watchers;
    }

    @JsonProperty("branches")
    public Link getBranches() {
        return branches;
    }

    @JsonProperty("branches")
    public void setBranches(Link branches) {
        this.branches = branches;
    }

    @JsonProperty("tags")
    public Link getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(Link tags) {
        this.tags = tags;
    }

    @JsonProperty("downloads")
    public Link getDownloads() {
        return downloads;
    }

    @JsonProperty("downloads")
    public void setDownloads(Link downloads) {
        this.downloads = downloads;
    }

    @JsonProperty("source")
    public Link getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(Link source) {
        this.source = source;
    }

    @JsonProperty("clone")
    public List<Clone> getClone() {
        return clone;
    }

    @JsonProperty("clone")
    public void setClone(List<Clone> clone) {
        this.clone = clone;
    }

    @JsonProperty("issues")
    public Link getIssues() {
        return issues;
    }

    @JsonProperty("issues")
    public void setIssues(Link issues) {
        this.issues = issues;
    }

    @JsonProperty("hooks")
    public Link getHooks() {
        return hooks;
    }

    @JsonProperty("hooks")
    public void setHooks(Link hooks) {
        this.hooks = hooks;
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
