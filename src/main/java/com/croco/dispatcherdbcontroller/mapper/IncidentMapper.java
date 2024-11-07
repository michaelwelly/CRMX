package com.croco.dispatcherdbcontroller.mapper;

import com.croco.dispatcherdbcontroller.dto.IncidentDto;
import com.croco.dispatcherdbcontroller.entity.Incident;
import com.croco.dispatcherdbcontroller.entity.IncidentStatus;
import com.croco.dispatcherdbcontroller.entity.IncidentType;
import com.croco.dispatcherdbcontroller.entity.LocationType;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, ReporterMapper.class, FilialMapper.class, FieldServiceTeamMapper.class, TaskMapper.class})
public interface IncidentMapper {

    @Mapping(target = "locationType", source = "locationType", qualifiedByName = "mapStringToLocationType")
    @Mapping(target = "incidentType", source = "incidentType", qualifiedByName = "mapStringToIncidentType")
    @Mapping(target = "incidentStatus", source = "incidentStatus", qualifiedByName = "mapStringToIncidentStatus")
    @Mapping(target = "team", source = "team")
    @Mapping(target = "tasks", source = "tasks")
    @Mapping(target = "id", ignore = true)
    Incident toEntity(IncidentDto incidentDto);

    @Mapping(target = "locationType", source = "locationType", qualifiedByName = "mapLocationTypeToString")
    @Mapping(target = "incidentType", source = "incidentType", qualifiedByName = "mapIncidentTypeToString")
    @Mapping(target = "incidentStatus", source = "incidentStatus", qualifiedByName = "mapIncidentStatusToString")
    @Mapping(target = "team", source = "team")
    @Mapping(target = "tasks", source = "tasks")
    IncidentDto toDto(Incident incident);


    @Mapping(target = "locationType", source = "locationType", qualifiedByName = "mapLocationTypeToString")
    @Mapping(target = "incidentType", source = "incidentType", qualifiedByName = "mapIncidentTypeToString")
    @Mapping(target = "incidentStatus", source = "incidentStatus", qualifiedByName = "mapIncidentStatusToString")
    @Mapping(target = "team", source = "team")
    @Mapping(target = "tasks", source = "tasks")
    List<IncidentDto> toDto(List<Incident> incidents);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "locationType", source = "locationType", qualifiedByName = "mapStringToLocationType")
    @Mapping(target = "incidentType", source = "incidentType", qualifiedByName = "mapStringToIncidentType")
    @Mapping(target = "incidentStatus", source = "incidentStatus", qualifiedByName = "mapStringToIncidentStatus")
    @Mapping(target = "team", source = "team")
    @Mapping(target = "tasks", source = "tasks")
    Incident partialUpdate(IncidentDto incidentDto, @MappingTarget Incident incident);

    // Метод для маппинга IncidentStatus в строку
    @Named("mapIncidentStatusToString")
    default String mapIncidentStatusToString(IncidentStatus incidentStatus) {
        return incidentStatus != null ? incidentStatus.name() : null;
    }

    // Метод для маппинга IncidentType в строку
    @Named("mapIncidentTypeToString")
    default String mapIncidentTypeToString(IncidentType incidentType) {
        return incidentType != null ? incidentType.name() : null;
    }

    // Метод для маппинга LocationType в строку
    @Named("mapLocationTypeToString")
    default String mapLocationTypeToString(LocationType locationType) {
        return locationType != null ? locationType.name() : null;
    }

    // Метод для маппинга IncidentStatus из строки в перечисление
    @Named("mapStringToIncidentStatus")
    default IncidentStatus mapStringToIncidentStatus(String incidentStatus) {
        return incidentStatus != null ? IncidentStatus.valueOf(incidentStatus) : null;
    }

    // Метод для маппинга IncidentType из строки в перечисление
    @Named("mapStringToIncidentType")
    default IncidentType mapStringToIncidentType(String incidentType) {
        return incidentType != null ? IncidentType.valueOf(incidentType) : null;
    }

    // Метод для маппинга LocationType из строки в перечисление
    @Named("mapStringToLocationType")
    default LocationType mapStringToLocationType(String locationType) {
        return locationType != null ? LocationType.valueOf(locationType) : null;
    }

}