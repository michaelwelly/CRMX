package com.croco.dispatcherdbcontroller.kafka.handlers;

import com.croco.dispatcherdbcontroller.api.clients.UserService;
import com.croco.dispatcherdbcontroller.dto.IncidentDto;
import com.croco.dispatcherdbcontroller.dto.UserDto;
import com.croco.dispatcherdbcontroller.entity.User;
import com.croco.dispatcherdbcontroller.kafka.DefaultProducer;
import com.croco.dispatcherdbcontroller.kafka.MessageHandler;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaMessage;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaResponse;
import com.croco.dispatcherdbcontroller.service.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Collections;
import java.util.List;

public class UserHandler implements MessageHandler {
    private final UserService kafkaUserService;
    private final DefaultProducer kafkaControllerProducer;
    private final JwtService jwtService;
    public UserHandler(UserService kafkaUserService, DefaultProducer kafkaControllerProducer, JwtService jwtService) {
        this.kafkaUserService = kafkaUserService;
        this.kafkaControllerProducer = kafkaControllerProducer;
        this.jwtService = jwtService;
    }

    @Override
    public void handle(KafkaMessage data) {
        KafkaResponse message = null;
        switch (data.action) {
            case GET:
                UserDto getUserDto = null;
                List<UserDto> getUserDtos = null;
                if (data.url != null) {
                    getUserDto = kafkaUserService.getOneByNameFromUrl(data.url);
                    var jwt = jwtService.generateToken(getUserDto);
                    message = KafkaResponse.builder()
                            .id(data.id)
                            .version(data.version)
                            .authToken(jwt)
                            .md5Signature(data.md5Signature)
                            .entityType(data.entityType)
                            .action(data.action)
                            .object(getUserDto)
                            .oldObject(data.object)
                            .build();
                } else if (data.object == null) {
                    if (data.elementId != null) {
                        getUserDto = kafkaUserService.getOne(data.elementId);
                        message = KafkaResponse.builder()
                                .id(data.id)
                                .version(data.version)
                                .authToken(data.authToken)
                                .md5Signature(data.md5Signature)
                                .entityType(data.entityType)
                                .action(data.action)
                                .object(getUserDto)
                                .oldObject(data.getOldObject())
                                .elementId(getUserDto.getId())
                                .build();
                    } else {
                        getUserDtos = kafkaUserService.getList();
                        message = KafkaResponse.builder()
                                .id(data.id)
                                .version(data.version)
                                .authToken(data.authToken)
                                .md5Signature(data.md5Signature)
                                .entityType(data.entityType)
                                .action(data.action)
                                .object(getUserDto)
                                .objectsList(Collections.singletonList(getUserDtos))
                                .oldObject(data.object)
                                .build();
                    }
                }
                sendResponse(message);
                break;
            case CREATE:
                if (data.object != null) {
                    UserDto userDto = convertToUserDto(data.object);
                    UserDto createdFlialDto = kafkaUserService.create(userDto);
                     message = KafkaResponse.builder().
                            id(data.id).
                            version(data.version).
                            authToken(data.authToken).
                            md5Signature(data.md5Signature).
                            entityType(data.entityType).
                            action(data.action).
                            object(createdFlialDto).
                            oldObject(data.object).
                            elementId(createdFlialDto.getId()).
                            build();

                    sendResponse(message);
                }
                break;
            case UPDATE:
                if (data.elementId != null && data.object != null) {
                    UserDto userDtoUpdate = convertToUserDto(data.object);
                    UserDto updatedFlialDto = kafkaUserService.update(data.elementId, userDtoUpdate);
                     message = KafkaResponse.builder().
                            id(data.id).
                            version(data.version).
                            authToken(data.authToken).
                            md5Signature(data.md5Signature).
                            entityType(data.entityType).
                            action(data.action).
                            object(updatedFlialDto).
                            oldObject(data.object).
                            elementId(updatedFlialDto.getId()).
                            build();

                    sendResponse(message);
                }
                break;
            case DELETE:
                if (data.elementId != null) {
                    UserDto deletedFlialDto = kafkaUserService.delete(data.elementId);
                     message = KafkaResponse.builder().
                            id(data.id).
                            version(data.version).
                            authToken(data.authToken).
                            md5Signature(data.md5Signature).
                            entityType(data.entityType).
                            action(data.action).
                            object(deletedFlialDto).
                            oldObject(data.object).
                            elementId(deletedFlialDto.getId()).
                            build();

                    sendResponse(message);

                }
                break;
            default:
                System.out.println("Unknown action: " + data.action);
                break;
        }
    }

    private UserDto convertToUserDto(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String result = null;
        UserDto userDto = null;
        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            // Преобразуем объект в JSON-строку, если это необходимо
            // Десериализуем JSON-строку в объект UserDto
            userDto = mapper.readValue(result, UserDto.class);

        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при преобразовании объекта в UserDto: " + e.getMessage());
        }

        return userDto;
    }

    private void sendResponse(KafkaResponse response) {
        kafkaControllerProducer.publishResponse(response);
        // Логика отправки ответа в Kafka
    }
}