package com.croco.dispatcherdbcontroller.api.controllers;

import com.croco.dispatcherdbcontroller.dto.WorkerDto;
import com.croco.dispatcherdbcontroller.api.clients.WorkerService;
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
@RequestMapping("/api/workers")
public class WorkerController {
    private final WorkerService workerService;
    private final RequestValidator requestValidator;
    @Autowired
    public WorkerController(WorkerService workerService, RequestValidator requestValidator) {
        this.workerService = workerService;
        this.requestValidator = requestValidator;
    }

    @GetMapping
    public ResponseEntity<List<WorkerDto>> getWorkers(@RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                                      @RequestHeader("Authorization") String authToken,
                                                      @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        List<WorkerDto> workerDtos = workerService.getList();
        return ResponseEntity.ok(workerDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerDto> getWorker(@PathVariable Long id,
                                               @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                               @RequestHeader("Authorization") String authToken,
                                               @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        WorkerDto workerDto = workerService.getOne(id);
        return ResponseEntity.ok(workerDto);
    }

    @PostMapping
    public ResponseEntity<WorkerDto> create(
            @RequestBody @Validated WorkerDto workerDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        WorkerDto createdWorker = workerService.create(workerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWorker);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkerDto> update(
            @PathVariable Long id,
            @RequestBody @Validated WorkerDto workerDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {

        // Проверка входных параметров запроса
        try {
            requestValidator.validate(version, authToken, md5Signature);
        } catch (RequestValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        WorkerDto updatedWorker = workerService.update(id, workerDto);
        return ResponseEntity.ok(updatedWorker);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WorkerDto> delete(@PathVariable Long id,
                                            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                            @RequestHeader("Authorization") String authToken,
                                            @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        WorkerDto deletedWorker = workerService.delete(id);
        return ResponseEntity.ok(deletedWorker);
    }

    private HttpHeaders getHeaders(String authToken, String md5Signature) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);
        headers.set("MD5-Signature", md5Signature);
        return headers;
    }
}