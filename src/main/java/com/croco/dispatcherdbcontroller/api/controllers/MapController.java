package com.croco.dispatcherdbcontroller.api.controllers;

import com.croco.dispatcherdbcontroller.dto.MapDto;
import com.croco.dispatcherdbcontroller.api.clients.MapService;
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
@RequestMapping("/api/maps")
public class MapController {
    private final MapService mapService;
    private final RequestValidator requestValidator;
    @Autowired
    public MapController(MapService mapService, RequestValidator requestValidator) {
        this.mapService = mapService;
        this.requestValidator = requestValidator;
    }

    @GetMapping
    public ResponseEntity<List<MapDto>> getMaps(@RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                                @RequestHeader("Authorization") String authToken,
                                                @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        List<MapDto> mapDtos = mapService.getList();
        return ResponseEntity.ok(mapDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MapDto> getMap(@PathVariable Long id,
                                         @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                         @RequestHeader("Authorization") String authToken,
                                         @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        MapDto mapDto = mapService.getOne(id);
        return ResponseEntity.ok(mapDto);
    }

    @PostMapping
    public ResponseEntity<MapDto> create(
            @RequestBody @Validated MapDto mapDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        MapDto createdMap = mapService.create(mapDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMap);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MapDto> update(
            @PathVariable Long id,
            @RequestBody @Validated MapDto mapDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {

        // Проверка входных параметров запроса
        try {
            requestValidator.validate(version, authToken, md5Signature);
        } catch (RequestValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        MapDto updatedMap = mapService.update(id, mapDto);
        return ResponseEntity.ok(updatedMap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MapDto> delete(@PathVariable Long id,
                                         @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                         @RequestHeader("Authorization") String authToken,
                                         @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        MapDto deletedMap = mapService.delete(id);
        return ResponseEntity.ok(deletedMap);
    }

    private HttpHeaders getHeaders(String authToken, String md5Signature) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);
        headers.set("MD5-Signature", md5Signature);
        return headers;
    }
}