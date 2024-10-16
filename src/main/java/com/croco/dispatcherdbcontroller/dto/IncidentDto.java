package com.croco.dispatcherdbcontroller.dto;

import com.croco.dispatcherdbcontroller.entity.IncidentStatus;
import com.croco.dispatcherdbcontroller.entity.IncidentType;
import com.croco.dispatcherdbcontroller.entity.LocationType;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.Map;

@Value
public class IncidentDto {
    Long id;
    ReporterDto reporter;
    UserDto user;
    @NotNull
    IncidentType incidentType;
    OffsetDateTime registrationDttm;
    @NotNull
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
    TaskDto tasks;

    public IncidentDto(Long id, ReporterDto reporter, UserDto user, IncidentType incidentType, OffsetDateTime registrationDttm, OffsetDateTime execDttm, OffsetDateTime synchronizationDttm, LocationType locationType, IncidentStatus incidentStatus, String descriptionText, FilialDto filial, Map<String, Object> addressJson, Map<String, Object> attributesJson, String addressStr, OffsetDateTime changedDttm, FieldServiceTeamDto team, TaskDto tasks) {
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
        this.team = null;
        this.tasks = tasks;
    }
}