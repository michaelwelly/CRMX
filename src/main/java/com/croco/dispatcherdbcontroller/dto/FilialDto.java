package com.croco.dispatcherdbcontroller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilialDto implements BasicDto {
    Long id;

    @NotNull
    Map<String, Object> locations;

    @NotNull
    @Size(max = 255)
    String titleStr;

    String descriptionTxt;

    Map<String, Object> attributesJson;

    // Конструктор с аннотацией @JsonCreator
    @JsonCreator
    public FilialDto(
            @JsonProperty("id") Long id,
            @JsonProperty("locations") Map<String, Object> locations,
            @JsonProperty("titleStr") String titleStr,
            @JsonProperty("descriptionTxt") String descriptionTxt,
            @JsonProperty("attributesJson") Map<String, Object> attributesJson) {
        this.id = id;
        this.locations = locations;
        this.titleStr = titleStr;
        this.descriptionTxt = descriptionTxt;
        this.attributesJson = attributesJson;
    }
}