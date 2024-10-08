package com.croco.dispatcherdbcontroller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class WorkerDto {
    private Long id;

    @NotNull
    private String nameStr;

    private Map<String, Object> contactsJson;
    private Integer collectionId;
    private String workerType; // или WorkerType, если вы используете Enum
}