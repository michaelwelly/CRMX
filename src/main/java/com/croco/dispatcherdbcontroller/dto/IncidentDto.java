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
}