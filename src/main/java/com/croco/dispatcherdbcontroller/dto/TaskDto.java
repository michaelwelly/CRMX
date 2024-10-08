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
}