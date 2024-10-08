package com.croco.dispatcherdbcontroller.api.controllers;

import com.croco.dispatcherdbcontroller.dto.FilialDto;
import com.croco.dispatcherdbcontroller.exception.RequestValidationException;
import com.croco.dispatcherdbcontroller.mapper.FilialMapper;
import com.croco.dispatcherdbcontroller.api.clients.FilialService;
import com.croco.dispatcherdbcontroller.service.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/filials")
public class FilialController {

    private final FilialService filialService;
    private final RequestValidator requestValidator;

    @Autowired
    private FilialMapper filialMapper;

    public FilialController(FilialService operatorService, RequestValidator requestValidator) {
        this.filialService = operatorService;
        this.requestValidator = requestValidator;
    }

    @GetMapping
    public ResponseEntity<List<FilialDto>> getOperators(@RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                                        @RequestHeader("Authorization") String authToken,
                                                        @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        List<FilialDto> filialDtos = filialService.getList();
        // Проверяем, есть ли филиалы
        if (filialDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // Возвращаем список филиалы в формате JSON
        return ResponseEntity.ok(filialDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilialDto> getFilials(@PathVariable Long id,
                                                @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                                @RequestHeader("Authorization") String authToken,
                                                @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        FilialDto operatorDto = filialService.getOne(id);
        return ResponseEntity.ok(operatorDto);
    }

    @PostMapping
    public ResponseEntity<FilialDto> create(
            @RequestBody @Validated FilialDto operatorDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        FilialDto createdFilial = filialService.create(operatorDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(getHeaders(authToken, md5Signature))
                .body(createdFilial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FilialDto> delete(@PathVariable Long id,
                                            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                            @RequestHeader("Authorization") String authToken,
                                            @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        return ResponseEntity.ok(filialService.delete(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FilialDto> patch(
            @PathVariable Long id,
            @RequestBody @Validated FilialDto filialDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {

        // Проверка входных параметров запроса
        try {
            requestValidator.validate(version, authToken, md5Signature);
        } catch (RequestValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        FilialDto createdFilial = null;
        try {
            createdFilial = filialService.patch(id, filialDto);
        } catch (IOException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(getHeaders(authToken, md5Signature))
                .body(createdFilial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilialDto> update(@PathVariable Long id, @RequestBody @Validated FilialDto filialDto,
                                            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                            @RequestHeader("Authorization") String authToken,
                                            @RequestHeader("MD5-Signature") String md5Signature) {
        // Проверка входных параметров запроса
        try {
            requestValidator.validate(version, authToken, md5Signature);
        } catch (RequestValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        FilialDto createdFilial = filialService.update(id, filialDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(getHeaders(authToken, md5Signature))
                .body(createdFilial);
    }

    private HttpHeaders getHeaders(String authToken, String md5Signature) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);
        headers.set("MD5-Signature", md5Signature);
        return headers;
    }
}