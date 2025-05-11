
package aiss.bitbucketminer1.model.BitBucket.comment;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueInfo {

    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("repository")
    private RepositoryInfo repositoryInfo;
    @JsonProperty("links")
    private IssueLinks links;
    @JsonProperty("title")
    private String title;
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
    public RepositoryInfo getRepository() {
        return repositoryInfo;
    }

    @JsonProperty("repository")
    public void setRepository(RepositoryInfo repositoryInfo) {
        this.repositoryInfo = repositoryInfo;
    }

    @JsonProperty("links")
    public IssueLinks getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(IssueLinks links) {
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
