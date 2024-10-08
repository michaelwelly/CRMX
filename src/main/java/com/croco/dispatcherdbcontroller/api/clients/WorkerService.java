package com.croco.dispatcherdbcontroller.api.clients;

import com.croco.dispatcherdbcontroller.dto.WorkerDto;

import java.io.IOException;
import java.util.List;

public interface WorkerService {
    List<WorkerDto> getList();
    WorkerDto getOne(Long id);
    WorkerDto create(WorkerDto workerDto);
    WorkerDto update(Long id, WorkerDto workerDto);
    WorkerDto delete(Long id);
    WorkerDto patch(Long id, WorkerDto workerDto) throws IOException;
}