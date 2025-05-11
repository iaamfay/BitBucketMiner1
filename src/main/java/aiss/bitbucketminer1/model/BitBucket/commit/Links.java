
package aiss.bitbucketminer1.model.BitBucket.commit;

import java.util.LinkedHashMap;
import java.util.Map;

import aiss.bitbucketminer1.model.BitBucket.common.Link;
import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {

    @JsonProperty("self")
    private Link self;
    @JsonProperty("html")
    private Link html;
    @JsonProperty("diff")
    private Link diff;
    @JsonProperty("approve")
    private Link approve;
    @JsonProperty("comments")
    private Link comments;
    @JsonProperty("statuses")
    private Link statuses;
    @JsonProperty("patch")
    private Link patch;
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

    @JsonProperty("diff")
    public Link getDiff() {
        return diff;
    }

    @JsonProperty("diff")
    public void setDiff(Link diff) {
        this.diff = diff;
    }

    @JsonProperty("approve")
    public Link getApprove() {
        return approve;
    }

    @JsonProperty("approve")
    public void setApprove(Link approve) {
        this.approve = approve;
    }

    @JsonProperty("comments")
    public Link getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(Link comments) {
        this.comments = comments;
    }

    @JsonProperty("statuses")
    public Link getStatuses() {
        return statuses;
    }

    @JsonProperty("statuses")
    public void setStatuses(Link statuses) {
        this.statuses = statuses;
    }

    @JsonProperty("patch")
    public Link getPatch() {
        return patch;
    }

    @JsonProperty("patch")
    public void setPatch(Link patch) {
        this.patch = patch;
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
