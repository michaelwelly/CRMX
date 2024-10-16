package com.croco.dispatcherdbcontroller.dto;

import com.croco.dispatcherdbcontroller.entity.Worker;
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
public class FieldServiceTeamDto implements BasicDto{
    Long id;
    Worker workers;

    @NotNull
    @Size(max = 255)
    String nameStr;

    Map<String, Object> attributesJson;
    OffsetDateTime createDateDttm;
    Boolean isActiveFlag;
    OffsetDateTime closeDateDttm;

    @JsonCreator
    public FieldServiceTeamDto(
            @JsonProperty("id") Long id,
            @JsonProperty("workers") Worker workers,
            @JsonProperty("nameStr") String nameStr,
            @JsonProperty("attributesJson") Map<String, Object> attributesJson,
            @JsonProperty("createDateDttm") OffsetDateTime createDateDttm,
            @JsonProperty("isActiveFlag") Boolean isActiveFlag,
            @JsonProperty("closeDateDttm") OffsetDateTime closeDateDttm) {
        this.id = id;
        this.workers = workers;
        this.nameStr = nameStr;
        this.attributesJson = attributesJson;
        this.createDateDttm = createDateDttm;
        this.isActiveFlag = isActiveFlag;
        this.closeDateDttm = closeDateDttm;
    }
}