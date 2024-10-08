package com.croco.dispatcherdbcontroller.api.clients.impl;

import com.croco.dispatcherdbcontroller.dto.WorkerDto;
import com.croco.dispatcherdbcontroller.entity.Worker;
import com.croco.dispatcherdbcontroller.mapper.WorkerMapper;
import com.croco.dispatcherdbcontroller.repository.WorkerRepository;
import com.croco.dispatcherdbcontroller.api.clients.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;
    private final WorkerMapper workerMapper;

    public WorkerServiceImpl(WorkerRepository workerRepository, WorkerMapper workerMapper) {
        this.workerRepository = workerRepository;
        this.workerMapper = workerMapper;
    }

    @Override
    public List<WorkerDto> getList() {
        List<Worker> workers = workerRepository.findAll();
        return workerMapper.toDto(workers);
    }

    @Override
    public WorkerDto getOne(Long id) {
        Worker worker = workerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found"));
        return workerMapper.toDto(worker);
    }

    @Override
    public WorkerDto create(WorkerDto workerDto) {
        Worker worker = workerMapper.toEntity(workerDto);
        Worker savedWorker = workerRepository.save(worker);
        return workerMapper.toDto(savedWorker);
    }

    @Override
    public WorkerDto update(Long id, WorkerDto workerDto) {
        Worker existingWorker = workerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found"));
        workerMapper.partialUpdate(workerDto, existingWorker); // Метод для частичного обновления
        return workerMapper.toDto(workerRepository.save(existingWorker));
    }

    @Override
    public WorkerDto patch(Long id, WorkerDto workerDto) {
        if (!workerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }
        Worker worker = workerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        workerMapper.partialUpdate(workerDto, worker);
        return workerMapper.toDto(workerRepository.save(worker));
    }


    @Override
    public WorkerDto delete(Long id) {
        Worker worker = workerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found"));
        workerRepository.delete(worker);
        return workerMapper.toDto(worker);
    }
}