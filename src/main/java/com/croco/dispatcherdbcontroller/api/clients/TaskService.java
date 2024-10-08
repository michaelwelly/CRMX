package com.croco.dispatcherdbcontroller.api.clients;

import com.croco.dispatcherdbcontroller.dto.TaskDto;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    List<TaskDto> getList();
    TaskDto getOne(Long id);
    TaskDto create(TaskDto taskDto);
    TaskDto update(Long id, TaskDto taskDto);
    TaskDto delete(Long id);
    TaskDto patch(Long id, TaskDto taskDto) throws IOException;
}