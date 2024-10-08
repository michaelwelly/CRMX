package com.croco.dispatcherdbcontroller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
public class FieldServiceTeamDto {
    private Long id;

    @NotNull
    private Long workerId; // ID работника

    private String nameStr;
    private Map<String, Object> attributesJson;
    private OffsetDateTime createDateDttm;
    private Boolean isActiveFlag;
    private OffsetDateTime closeDateDttm;
}