package com.croco.dispatcherdbcontroller.api.controllers;

import com.croco.dispatcherdbcontroller.api.clients.UserService;
import com.croco.dispatcherdbcontroller.dto.IncidentDto;
import com.croco.dispatcherdbcontroller.entity.IncidentStatus;
import com.croco.dispatcherdbcontroller.entity.User;
import com.croco.dispatcherdbcontroller.exception.RequestValidationException;
import com.croco.dispatcherdbcontroller.mapper.IncidentMapper;
import com.croco.dispatcherdbcontroller.api.clients.IncidentService;
import com.croco.dispatcherdbcontroller.mapper.UserMapper;
import com.croco.dispatcherdbcontroller.service.RequestValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentService incidentService;
    private final RequestValidator requestValidator;
    private final UserService userService;
    private final UserMapper userMapper;
    private final IncidentMapper incidentMapper;

    public IncidentController(IncidentService incidentService, RequestValidator requestValidator, UserService userService, UserMapper userMapper, IncidentMapper incidentMapper) {
        this.incidentService = incidentService;
        this.requestValidator = requestValidator;
        this.userService = userService;
        this.userMapper = userMapper;
        this.incidentMapper = incidentMapper;
    }

    @GetMapping
    public ResponseEntity<List<IncidentDto>> getIncidents(@RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                                          @RequestHeader("Authorization") String authToken,
                                                          @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        List<IncidentDto> incidentDtos = incidentService.getList();
        if (incidentDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(incidentDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentDto> getIncident(@PathVariable Long id,
                                                   @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                                   @RequestHeader("Authorization") String authToken,
                                                   @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        IncidentDto incidentDto = incidentService.getOne(id);
        return ResponseEntity.ok(incidentDto);
    }

    @PostMapping
    public ResponseEntity<IncidentDto> create(
            @RequestBody @Validated IncidentDto incidentDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        IncidentDto createdIncident = incidentService.create(incidentDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(getHeaders(authToken, md5Signature))
                .body(createdIncident);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IncidentDto> delete(@PathVariable Long id,
                                              @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                              @RequestHeader("Authorization") String authToken,
                                              @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        return ResponseEntity.ok(incidentService.delete(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<IncidentDto> patch(
            @PathVariable Long id,
            @RequestBody @Validated IncidentDto incidentDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {

        // Проверка входных параметров запроса
        try {
            requestValidator.validate(version, authToken, md5Signature);
        } catch (RequestValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        IncidentDto updatedIncident = null;
        try {
            updatedIncident = incidentService.patch(id, incidentDto);
        } catch (IOException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(getHeaders(authToken, md5Signature))
                .body(updatedIncident);
    }


    @PutMapping("/{id}")
    public ResponseEntity<IncidentDto> update(@PathVariable Long id, @RequestBody @Validated IncidentDto incidentDto,
                                              @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                              @RequestHeader("Authorization") String authToken,
                                              @RequestHeader("MD5-Signature") String md5Signature) {
        // Проверка входных параметров запроса
        try {
            requestValidator.validate(version, authToken, md5Signature);
        } catch (RequestValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        IncidentDto updatedIncident = incidentService.update(id, incidentDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(getHeaders(authToken, md5Signature))
                .body(updatedIncident);
    }

    @GetMapping("/filtered")
    public ResponseEntity<List<IncidentDto>> getFilteredIncidents(
            @RequestParam(required = false) Boolean myRequests,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) List<IncidentStatus> statuses,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {

        requestValidator.validate(version, authToken, md5Signature);
        User user = null;
        if (userId != null) {
            try {
                user = userMapper.toEntity(userService.getOne(Long.valueOf(userId)));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

        }
        List<IncidentDto> incidentDtos = incidentService.getFilteredIncidents(statuses, startDate, endDate, user);

        if (incidentDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(incidentDtos);
    }

    private HttpHeaders getHeaders(String authToken, String md5Signature) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);
        headers.set("MD5-Signature", md5Signature);
        return headers;
    }
}