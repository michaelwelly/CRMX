package com.croco.dispatcherdbcontroller.mapper;

import com.croco.dispatcherdbcontroller.dto.FieldServiceTeamDto;
import com.croco.dispatcherdbcontroller.entity.FieldServiceTeam;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FieldServiceTeamMapper {
    FieldServiceTeamDto toDto(FieldServiceTeam fieldServiceTeam);

    FieldServiceTeam toEntity(FieldServiceTeamDto fieldServiceTeamDto);

    List<FieldServiceTeamDto> toDto(List<FieldServiceTeam> fieldServiceTeams);

    List<FieldServiceTeam> toEntity(List<FieldServiceTeamDto> fieldServiceTeamDtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FieldServiceTeam partialUpdate(FieldServiceTeamDto fieldServiceTeamDto, @MappingTarget FieldServiceTeam fieldServiceTeam);

}