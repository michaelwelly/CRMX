package com.croco.dispatcherdbcontroller.dto;

import com.croco.dispatcherdbcontroller.entity.IncidentStatus;
import com.croco.dispatcherdbcontroller.entity.IncidentType;
import com.croco.dispatcherdbcontroller.entity.LocationType;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Set;

@Value
public class IncidentDto {
    Long id;
    ReporterDto reporter;
    UserDto user;
    @NotNull
    IncidentType incidentType;
    OffsetDateTime registrationDttm;
    OffsetDateTime execDttm;
    OffsetDateTime synchronizationDttm;
    LocationType locationType;
    IncidentStatus incidentStatus;
    String descriptionText;
    @NotNull
    FilialDto filial;
    @NotNull
    Map<String, Object> addressJson;
    @NotNull
    Map<String, Object> attributesJson;
    String addressStr;
    OffsetDateTime changedDttm;
    FieldServiceTeamDto team;
    Set<TaskDto> tasks;

    @JsonCreator
    public IncidentDto(
            @JsonProperty("id") Long id,
            @JsonProperty("reporter") ReporterDto reporter,
            @JsonProperty("user") UserDto user,
            @JsonProperty("incidentType") IncidentType incidentType,
            @JsonProperty("registrationDttm") OffsetDateTime registrationDttm,
            @JsonProperty("execDttm") OffsetDateTime execDttm,
            @JsonProperty("synchronizationDttm") OffsetDateTime synchronizationDttm,
            @JsonProperty("locationType") LocationType locationType,
            @JsonProperty("incidentStatus") IncidentStatus incidentStatus,
            @JsonProperty("descriptionText") String descriptionText,
            @JsonProperty("filial") FilialDto filial,
            @JsonProperty("addressJson") Map<String, Object> addressJson,
            @JsonProperty("attributesJson") Map<String, Object> attributesJson,
            @JsonProperty("addressStr") String addressStr,
            @JsonProperty("changedDttm") OffsetDateTime changedDttm,
            @JsonProperty("team") FieldServiceTeamDto team,
            @JsonProperty("tasks") Set<TaskDto>  tasks) {
        this.id = id;
        this.reporter = reporter;
        this.user = user;
        this.incidentType = incidentType;
        this.registrationDttm = registrationDttm;
        this.execDttm = execDttm;
        this.synchronizationDttm = synchronizationDttm;
        this.locationType = locationType;
        this.incidentStatus = incidentStatus;
        this.descriptionText = descriptionText;
        this.filial = filial;
        this.addressJson = addressJson;
        this.attributesJson = attributesJson;
        this.addressStr = addressStr;
        this.changedDttm = changedDttm;
        this.team = team;
        this.tasks = tasks;
    }
}