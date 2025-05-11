
package aiss.bitbucketminer1.model.BitBucket.comment;

import java.util.LinkedHashMap;
import java.util.Map;

import aiss.bitbucketminer1.model.BitBucket.common.Link;
import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryLinks {

    @JsonProperty("self")
    private Link self;
    @JsonProperty("html")
    private Link html;
    @JsonProperty("avatar")
    private Link avatar;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
