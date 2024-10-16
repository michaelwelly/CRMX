package com.croco.dispatcherdbcontroller.dto;

import com.croco.dispatcherdbcontroller.entity.WorkerType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.Map;


@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkerDto implements BasicDto{
    Long id;

    @NotNull
    @Size(max = 255)
    String nameStr;

    Map<String, Object> contactsJson;
    WorkerType workerType;
    Integer collectionId;

    @JsonCreator
    public WorkerDto(
            @JsonProperty("id") Long id,
            @JsonProperty("nameStr") @NotNull @Size(max = 255) String nameStr,
            @JsonProperty("contactsJson") Map<String, Object> contactsJson,
            @JsonProperty("workerType") WorkerType workerType,
            @JsonProperty("collectionId") Integer collectionId) {
        this.id = id;
        this.nameStr = nameStr;
        this.contactsJson = contactsJson;
        this.workerType = workerType;
        this.collectionId = collectionId;
    }
}