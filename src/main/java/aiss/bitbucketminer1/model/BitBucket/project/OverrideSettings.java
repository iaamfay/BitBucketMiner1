
package aiss.bitbucketminer1.model.BitBucket.project;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "default_merge_strategy",
    "branching_model"
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class OverrideSettings {

    @JsonProperty("default_merge_strategy")
    private Boolean defaultMergeStrategy;
    @JsonProperty("branching_model")
    private Boolean branchingModel;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("default_merge_strategy")
    public Boolean getDefaultMergeStrategy() {
        return defaultMergeStrategy;
    }

    @JsonProperty("default_merge_strategy")
    public void setDefaultMergeStrategy(Boolean defaultMergeStrategy) {
        this.defaultMergeStrategy = defaultMergeStrategy;
    }

    @JsonProperty("branching_model")
    public Boolean getBranchingModel() {
        return branchingModel;
    }

    @JsonProperty("branching_model")
    public void setBranchingModel(Boolean branchingModel) {
        this.branchingModel = branchingModel;
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
