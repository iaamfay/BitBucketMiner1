
package aiss.bitbucketminer1.model.BitBucket.commit;

import java.util.LinkedHashMap;
import java.util.Map;

import aiss.bitbucketminer1.model.BitBucket.user.UserJava;
import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {

    @JsonProperty("type")
    private String type;
    @JsonProperty("raw")
    private String raw; //Alejandro
    @JsonProperty("user")
    private UserJava user;
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

    @JsonProperty("raw")
    public String getRaw() {
        return raw;
    }

    @JsonProperty("raw")
    public void setRaw(String raw) {
        this.raw = raw;
    }

    @JsonProperty("user")
    public UserJava getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(UserJava user) {
        this.user = user;
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
