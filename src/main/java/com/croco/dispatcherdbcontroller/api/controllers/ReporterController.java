package com.croco.dispatcherdbcontroller.api.controllers;

import com.croco.dispatcherdbcontroller.dto.ReporterDto;
import com.croco.dispatcherdbcontroller.exception.RequestValidationException;
import com.croco.dispatcherdbcontroller.mapper.ReporterMapper;
import com.croco.dispatcherdbcontroller.api.clients.ReporterService;
import com.croco.dispatcherdbcontroller.service.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reporters")
public class ReporterController {
    private final ReporterService reporterService;
    private final RequestValidator requestValidator;

    @Autowired
    private ReporterMapper reporterMapper;

    public ReporterController(ReporterService reporterService, RequestValidator requestValidator) {
        this.reporterService = reporterService;
        this.requestValidator = requestValidator;
    }

    @GetMapping
    public ResponseEntity<List<ReporterDto>> getReporters(@RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                                          @RequestHeader("Authorization") String authToken,
                                                          @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        List<ReporterDto> reporterDtos = reporterService.getList();
        if (reporterDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(reporterDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporterDto> getReporter(@PathVariable Long id,
                                                   @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                                   @RequestHeader("Authorization") String authToken,
                                                   @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        ReporterDto reporterDto = reporterService.getOne(id);
        return ResponseEntity.ok(reporterDto);
    }

    @PostMapping
    public ResponseEntity<ReporterDto> create(
            @RequestBody @Validated ReporterDto reporterDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        ReporterDto createdReporter = reporterService.create(reporterDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(getHeaders(authToken, md5Signature))
                .body(createdReporter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReporterDto> delete(@PathVariable Long id,
                                              @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                              @RequestHeader("Authorization") String authToken,
                                              @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        return ResponseEntity.ok(reporterService.delete(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReporterDto> patch(
            @PathVariable Long id,
            @RequestBody @Validated ReporterDto reporterDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {

        // Проверка входных параметров запроса
        try {
            requestValidator.validate(version, authToken, md5Signature);
        } catch (RequestValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ReporterDto createdReporter = null;
        try {
            createdReporter = reporterService.patch(id, reporterDto);
        } catch (IOException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(getHeaders(authToken, md5Signature))
                .body(createdReporter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporterDto> update(@PathVariable Long id, @RequestBody @Validated ReporterDto reporterDto,
                                            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                            @RequestHeader("Authorization") String authToken,
                                            @RequestHeader("MD5-Signature") String md5Signature) {
        // Проверка входных параметров запроса
        try {
            requestValidator.validate(version, authToken, md5Signature);
        } catch (RequestValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ReporterDto updatedReporter= reporterService.update(id, reporterDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(getHeaders(authToken, md5Signature))
                .body(updatedReporter);
    }

    private HttpHeaders getHeaders(String authToken, String md5Signature) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);
        headers.set("MD5-Signature", md5Signature);
        return headers;
    }
}