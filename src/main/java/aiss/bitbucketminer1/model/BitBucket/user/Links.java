
package aiss.bitbucketminer1.model.BitBucket.user;

import java.util.LinkedHashMap;
import java.util.Map;

import aiss.bitbucketminer1.model.BitBucket.common.Link;
import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {

    @JsonProperty("self")
    private Link self;
    @JsonProperty("avatar")
    private Link avatar;
    @JsonProperty("html")
    private Link html;
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

    @JsonProperty("avatar")
    public Link getAvatar() {
        return avatar;
    }

    @JsonProperty("avatar")
    public void setAvatar(Link avatar) {
        this.avatar = avatar;
    }

    @JsonProperty("html")
    public Link getHtml() {
        return html;
    }

    @JsonProperty("html")
    public void setHtml(Link html) {
        this.html = html;
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
