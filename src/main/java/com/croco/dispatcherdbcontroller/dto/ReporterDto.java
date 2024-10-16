package com.croco.dispatcherdbcontroller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Map;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReporterDto implements BasicDto{
    Long id;
    @Size(max = 100)
    String nameStr;
    @NotNull
    @Size(max = 100)
    String phoneStr;
    String descriptionTxt;
    Map<String, Object> attributesJson;

    @JsonCreator
    public ReporterDto(
            @JsonProperty("id") Long id,
            @JsonProperty("nameStr") @Size(max = 100) String nameStr,
            @JsonProperty("phoneStr") @NotNull @Size(max = 100) String phoneStr,
            @JsonProperty("descriptionTxt") String descriptionTxt,
            @JsonProperty("attributesJson") Map<String, Object> attributesJson) {
        this.id = id;
        this.nameStr = nameStr;
        this.phoneStr = phoneStr;
        this.descriptionTxt = descriptionTxt;
        this.attributesJson = attributesJson;
    }
}