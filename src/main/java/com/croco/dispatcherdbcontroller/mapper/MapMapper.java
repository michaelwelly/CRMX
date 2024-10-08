package com.croco.dispatcherdbcontroller.mapper;

import com.croco.dispatcherdbcontroller.dto.MapDto;
import com.croco.dispatcherdbcontroller.entity.Map;
import com.croco.dispatcherdbcontroller.entity.MapType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MapMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Map partialUpdate(MapDto mapDto, @MappingTarget Map map);

    @Mapping(target = "mapType", source = "mapType", qualifiedByName = "mapStringToMapType")
    Map toEntity(MapDto mapDto);

    @Mapping(target = "mapType", source = "mapType", qualifiedByName = "mapMapTypeToString")
    MapDto toDto(Map map);

    @Mapping(target = "mapType", source = "mapType", qualifiedByName = "mapMapTypeToString")
    List<MapDto> toDto(List<Map> maps);

    // Метод для маппинга MapType в строку
    @Named("mapMapTypeToString")
    default String mapIncidentStatusToString(MapType mapType) {
        return mapType != null ? mapType.name() : null;
    }

    // Метод для маппинга MapType из строки в перечисление
    @Named("mapStringToMapType")
    default MapType mapStringToIncidentStatus(String mapType) {
        return mapType != null ? MapType.valueOf(mapType) : null;
    }
}