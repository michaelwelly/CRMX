package com.croco.dispatcherdbcontroller.api.clients.impl;

import com.croco.dispatcherdbcontroller.dto.TaskDto;
import com.croco.dispatcherdbcontroller.entity.Incident;
import com.croco.dispatcherdbcontroller.entity.Task;
import com.croco.dispatcherdbcontroller.entity.Worker;
import com.croco.dispatcherdbcontroller.mapper.TaskMapper;
import com.croco.dispatcherdbcontroller.repository.IncidentRepository;
import com.croco.dispatcherdbcontroller.repository.TaskRepository;
import com.croco.dispatcherdbcontroller.api.clients.TaskService;
import com.croco.dispatcherdbcontroller.repository.WorkerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final IncidentRepository incidentRepository;
    private final WorkerRepository workerRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, IncidentRepository incidentRepository, WorkerRepository workerRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.incidentRepository = incidentRepository;
        this.workerRepository = workerRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDto> getList() {
        List<Task> tasks = taskRepository.findAllWithWorkers();
        return taskMapper.toDto(tasks);
    }

    @Override
    public TaskDto getOne(Long id) {
        Task task = taskRepository.findOneWithWorker(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        return taskMapper.toDto(task);
    }

    @Override
    public TaskDto create(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        if (taskDto.getIncident() != null) {
            Incident incident = incidentRepository.findById(taskDto.getIncident())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incident not found"));
            task.setIncident(incident.getId());
        }
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @Override
    public TaskDto update(Long id, TaskDto taskDto) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
         if(existingTask.getWorker()!=null && taskDto.getWorkerId() == null){
             taskDto.setWorkerId(existingTask.getWorker().getId());
         }
        // Обновление полей задачи
        taskMapper.partialUpdate(taskDto, existingTask);

        // Установка worker, если он существует
        if (taskDto.getWorkerId() != null) {
            Worker worker = workerRepository.findById(taskDto.getWorkerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found"));
            existingTask.setWorker(worker); // Устанавливаем найденного работника
        } else {
            existingTask.setWorker(null); // Или можно оставить текущее значение
        }

        // Обновление инцидента
        if (taskDto.getIncident() != null) {
            Incident incident = incidentRepository.findById(taskDto.getIncident())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incident not found"));
            existingTask.setIncident(incident.getId());
        } else {
            existingTask.setIncident(null);
        }

        return taskMapper.toDto(taskRepository.save(existingTask));
    }

    @Override
    public TaskDto patch(Long id, TaskDto taskDto) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        taskMapper.partialUpdate(taskDto, task);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public TaskDto delete(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        taskRepository.delete(task);
        return taskMapper.toDto(task);
    }
}