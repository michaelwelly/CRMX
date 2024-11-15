package com.croco.dispatcherdbcontroller.mapper;

import com.croco.dispatcherdbcontroller.dto.ReporterDto;
import com.croco.dispatcherdbcontroller.entity.Reporter;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReporterMapper {
    @Mapping(target = "id", ignore = true)
    Reporter toEntity(ReporterDto reporterDto);

    ReporterDto toDto(Reporter reporter);

    List<ReporterDto> toDto(List<Reporter> reporters);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    Reporter partialUpdate(ReporterDto reporterDto, @MappingTarget Reporter reporter);
}