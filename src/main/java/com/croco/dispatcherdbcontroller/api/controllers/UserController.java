package com.croco.dispatcherdbcontroller.api.controllers;

import com.croco.dispatcherdbcontroller.dto.UserDto;
import com.croco.dispatcherdbcontroller.exception.RequestValidationException;
import com.croco.dispatcherdbcontroller.mapper.UserMapper;
import com.croco.dispatcherdbcontroller.api.clients.UserService;
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
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final RequestValidator requestValidator;

    @Autowired
    private UserMapper userMapper;

    public UserController(UserService userService, RequestValidator requestValidator) {
        this.userService = userService;
        this.requestValidator = requestValidator;
    }

    @GetMapping
    public ResponseEntity<?> getUsers(
            @RequestParam(required = false) String name,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {

        if (name == null) {
            requestValidator.validate(version, authToken, md5Signature);
        }

        if (name != null && !name.isEmpty()) {
            UserDto userDto = userService.getOneByName(name);
            return ResponseEntity.ok(userDto);
        } else {
            List<UserDto> userDtos = userService.getList();
            if (userDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(userDtos);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id,
                                           @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                           @RequestHeader("Authorization") String authToken,
                                           @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        UserDto userDto = userService.getOne(id);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(
            @RequestBody @Validated UserDto userDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        UserDto createdUser = userService.create(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(getHeaders(authToken, md5Signature))
                .body(createdUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable Long id,
                                          @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                          @RequestHeader("Authorization") String authToken,
                                          @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);
        return ResponseEntity.ok(userService.delete(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> patch(
            @PathVariable Long id,
            @RequestBody @Validated UserDto userDto,
            @RequestHeader(value = "Version", defaultValue = "1.0") String version,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("MD5-Signature") String md5Signature) {

        // Проверка входных параметров запроса
        try {
            requestValidator.validate(version, authToken, md5Signature);
        } catch (RequestValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        UserDto createdUser = null;
        try {
            createdUser = userService.patch(id, userDto);
        } catch (IOException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(getHeaders(authToken, md5Signature))
                .body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Validated UserDto userDto,
                                          @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                          @RequestHeader("Authorization") String authToken,
                                          @RequestHeader("MD5-Signature") String md5Signature) {
        // Проверка входных параметров запроса
        try {
            requestValidator.validate(version, authToken, md5Signature);
        } catch (RequestValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        UserDto updatedUser = userService.update(id, userDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(getHeaders(authToken, md5Signature))
                .body(updatedUser);
    }

    private HttpHeaders getHeaders(String authToken, String md5Signature) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);
        headers.set("MD5-Signature", md5Signature);
        return headers;
    }
}