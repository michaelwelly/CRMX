package com.croco.dispatcherdbcontroller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
public class TaskDto {
    private Long id;

    @NotNull
    private Long workerId; // ID работника

    private Map<String, Object> incidentId;
    private String titleStr;
    private Integer orderNum;
    private Map<String, Object> attributesJson;
    private Map<String, Object> subtasksJson;
    private Boolean isCompleteFlag;
    private Integer mediaId; // ID медиа
    private OffsetDateTime toBeginWorkDttm;
    private OffsetDateTime beginWorkDttm;
    private OffsetDateTime completeWorkDttm;
    private Integer collectionId;

    @JsonCreator
    public TaskDto(
            @JsonProperty("id") Long id,
            @JsonProperty("workerId") @NotNull Long workerId,
            @JsonProperty("incidentId") Map<String, Object> incidentId,
            @JsonProperty("titleStr") String titleStr,
            @JsonProperty("orderNum") Integer orderNum,
            @JsonProperty("attributesJson") Map<String, Object> attributesJson,
            @JsonProperty("subtasksJson") Map<String, Object> subtasksJson,
            @JsonProperty("isCompleteFlag") Boolean isCompleteFlag,
            @JsonProperty("mediaId") Integer mediaId,
            @JsonProperty("toBeginWorkDttm") OffsetDateTime toBeginWorkDttm,
            @JsonProperty("beginWorkDttm") OffsetDateTime beginWorkDttm,
            @JsonProperty("completeWorkDttm") OffsetDateTime completeWorkDttm,
            @JsonProperty("collectionId") Integer collectionId) {
        this.id = id;
        this.workerId = workerId;
        this.incidentId = incidentId;
        this.titleStr = titleStr;
        this.orderNum = orderNum;
        this.attributesJson = attributesJson;
        this.subtasksJson = subtasksJson;
        this.isCompleteFlag = isCompleteFlag;
        this.mediaId = mediaId;
        this.toBeginWorkDttm = toBeginWorkDttm;
        this.beginWorkDttm = beginWorkDttm;
        this.completeWorkDttm = completeWorkDttm;
        this.collectionId = collectionId;
    }
}