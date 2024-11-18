package com.croco.dispatcherdbcontroller.mapper;

import com.croco.dispatcherdbcontroller.dto.WorkerDto;
import com.croco.dispatcherdbcontroller.entity.Worker;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkerMapper {
    WorkerDto toDto(Worker worker);
    @Mapping(target = "id", ignore = true)
    Worker toEntity(WorkerDto workerDto);
    List<WorkerDto> toDto(List<Worker> workers);
    List<Worker> toEntity(List<WorkerDto> workerDtos);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    Worker partialUpdate(WorkerDto workerDto, @MappingTarget Worker worker);

}