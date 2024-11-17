package com.croco.dispatcherdbcontroller.mapper;

import com.croco.dispatcherdbcontroller.dto.TaskDto;
import com.croco.dispatcherdbcontroller.entity.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "worker.id", source = "workerId") // Маппим workerId на worker
    @Mapping(target = "id", ignore = true)
    Task toEntity(TaskDto taskDto);

    @Mapping(target = "workerId", source = "worker.id") // Маппим worker на workerId
    TaskDto toDto(Task task);

    Set<TaskDto> toDto(Set<Task> tasks);

    List<Task> toEntity(List<TaskDto> taskDtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    Task partialUpdate(TaskDto taskDto, @MappingTarget Task task);

    List<TaskDto> toDto(List<Task> tasks);
}