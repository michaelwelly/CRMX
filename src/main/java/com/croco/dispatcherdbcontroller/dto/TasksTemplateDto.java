package com.croco.dispatcherdbcontroller.dto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class TasksTemplateDto {
    Long id;
    TasksTemplateDto incidentType;
    @NotNull
    @Size(max = 255)
    String titleStr;

    Integer orderNum;
    Map<String, Object> attributesJson;
    Map<String, Object> subtasksJson;

    @JsonCreator
    public TasksTemplateDto(
            @JsonProperty("id") Long id,
            @JsonProperty("incidentType") TasksTemplateDto incidentType,
            @JsonProperty("titleStr") @NotNull @Size(max = 255) String titleStr,
            @JsonProperty("orderNum") Integer orderNum,
            @JsonProperty("attributesJson") Map<String, Object> attributesJson,
            @JsonProperty("subtasksJson") Map<String, Object> subtasksJson) {
        this.id = id;
        this.incidentType = incidentType;
        this.titleStr = titleStr;
        this.orderNum = orderNum;
        this.attributesJson = attributesJson;
        this.subtasksJson = subtasksJson;
    }
}