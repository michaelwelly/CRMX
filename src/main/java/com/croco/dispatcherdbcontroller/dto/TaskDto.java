package com.croco.dispatcherdbcontroller.dto;

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

    public TaskDto(Long id, Long workerId, Map<String, Object> incidentId, String titleStr, Integer orderNum, Map<String, Object> attributesJson, Map<String, Object> subtasksJson, Boolean isCompleteFlag, Integer mediaId, OffsetDateTime toBeginWorkDttm, OffsetDateTime beginWorkDttm, OffsetDateTime completeWorkDttm, Integer collectionId) {
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