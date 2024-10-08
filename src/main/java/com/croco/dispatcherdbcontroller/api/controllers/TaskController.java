package com.croco.dispatcherdbcontroller.api.controllers;

import com.croco.dispatcherdbcontroller.dto.TaskDto;
import com.croco.dispatcherdbcontroller.api.clients.TaskService;
import com.croco.dispatcherdbcontroller.exception.RequestValidationException;
import com.croco.dispatcherdbcontroller.service.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final RequestValidator requestValidator;
    @Autowired
    public TaskController(TaskService taskService, RequestValidator requestValidator) {
        this.taskService = taskService;
        this.requestValidator = requestValidator;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks(@RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                                  @RequestHeader("Authorization") String authToken,
                                                  @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        List<TaskDto> taskDtos = taskService.getList();
        return ResponseEntity.ok(taskDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id,
                                           @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                           @RequestHeader("Authorization") String authToken,
                                           @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        TaskDto taskDto = taskService.getOne(id);
        return ResponseEntity.ok(taskDto);
    }

    @PostMapping
    public ResponseEntity<TaskDto> create(
            @RequestBody @Validated TaskDto taskDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        TaskDto createdTask = taskService.create(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> update(
            @PathVariable Long id,
            @RequestBody @Validated TaskDto taskDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {

        // Проверка входных параметров запроса
        try {
            requestValidator.validate(version, authToken, md5Signature);
        } catch (RequestValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        TaskDto updatedTask = taskService.update(id, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDto> delete(@PathVariable Long id,
                                          @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                          @RequestHeader("Authorization") String authToken,
                                          @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        TaskDto deletedTask = taskService.delete(id);
        return ResponseEntity.ok(deletedTask);
    }

    private HttpHeaders getHeaders(String authToken, String md5Signature) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);
        headers.set("MD5-Signature", md5Signature);
        return headers;
    }
}