package com.croco.dispatcherdbcontroller.api.controllers;


import com.croco.dispatcherdbcontroller.dto.MediaDto;
import com.croco.dispatcherdbcontroller.api.clients.MediaService;
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
@RequestMapping("/api/media")
public class MediaController {
    private final MediaService mediaService;
    private final RequestValidator requestValidator;
    @Autowired
    public MediaController(MediaService mediaService, RequestValidator requestValidator) {
        this.mediaService = mediaService;
        this.requestValidator = requestValidator;
    }

    @GetMapping
    public ResponseEntity<List<MediaDto>> getMedia(@RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                                   @RequestHeader("Authorization") String authToken,
                                                   @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        List<MediaDto> mediaDtos = mediaService.getList();
        return ResponseEntity.ok(mediaDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaDto> getMedia(@PathVariable Long id,
                                             @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                             @RequestHeader("Authorization") String authToken,
                                             @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        MediaDto mediaDto = mediaService.getOne(id);
        return ResponseEntity.ok(mediaDto);
    }

    @PostMapping
    public ResponseEntity<MediaDto> create(
            @RequestBody @Validated MediaDto mediaDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        MediaDto createdMedia = mediaService.create(mediaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMedia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaDto> update(
            @PathVariable Long id,
            @RequestBody @Validated MediaDto mediaDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {

        // Проверка входных параметров запроса
        try {
            requestValidator.validate(version, authToken, md5Signature);
        } catch (RequestValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        MediaDto updatedMedia = mediaService.update(id, mediaDto);
        return ResponseEntity.ok(updatedMedia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MediaDto> delete(@PathVariable Long id,
                                           @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                           @RequestHeader("Authorization") String authToken,
                                           @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        MediaDto deletedMedia = mediaService.delete(id);
        return ResponseEntity.ok(deletedMedia);
    }

    private HttpHeaders getHeaders(String authToken, String md5Signature) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);
        headers.set("MD5-Signature", md5Signature);
        return headers;
    }
}