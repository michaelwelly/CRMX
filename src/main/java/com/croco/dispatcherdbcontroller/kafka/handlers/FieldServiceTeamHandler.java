package com.croco.dispatcherdbcontroller.kafka.handlers;

import com.croco.dispatcherdbcontroller.api.clients.FieldServiceTeamService;
import com.croco.dispatcherdbcontroller.dto.FieldServiceTeamDto;
import com.croco.dispatcherdbcontroller.kafka.DefaultProducer;
import com.croco.dispatcherdbcontroller.kafka.MessageHandler;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaMessage;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Collections;
import java.util.List;

public class FieldServiceTeamHandler implements MessageHandler {
    private final FieldServiceTeamService kafkaFieldServiceTeamService;
    private final DefaultProducer kafkaControllerProducer;
    public FieldServiceTeamHandler(FieldServiceTeamService kafkaFieldServiceTeamService, DefaultProducer kafkaControllerProducer) {
        this.kafkaFieldServiceTeamService = kafkaFieldServiceTeamService;
        this.kafkaControllerProducer = kafkaControllerProducer;
    }

    @Override
    public void handle(KafkaMessage data) {
        switch (data.action) {
            case GET:
                if (data.object == null) {
                    FieldServiceTeamDto getFieldServiceTeamDto = null;
                    List<FieldServiceTeamDto> getFieldServiceTeamDtos = null;
                    KafkaResponse message = null;
                    if (data.elementId != null) {
                        getFieldServiceTeamDto = kafkaFieldServiceTeamService.getOne(data.elementId);
                        message = KafkaResponse.builder().
                                id(data.id).
                                version(data.version).
                                authToken(data.authToken).
                                md5Signature(data.md5Signature).
                                entityType(data.entityType).
                                action(data.action).
                                object(getFieldServiceTeamDto).
                                oldObject(data.getOldObject()).
                                elementId(getFieldServiceTeamDto.getId()).
                                build();
                    } else {
                        getFieldServiceTeamDtos = kafkaFieldServiceTeamService.getList();
                        message = KafkaResponse.builder().
                                id(data.id).
                                version(data.version).
                                authToken(data.authToken).
                                md5Signature(data.md5Signature).
                                entityType(data.entityType).
                                action(data.action).
                                object(getFieldServiceTeamDto).
                                objectsList(Collections.singletonList(getFieldServiceTeamDtos)).
                                oldObject(data.object).
                                build();
                    }
                    sendResponse(message);
                }
            case CREATE:
                if (data.object != null) {
                    FieldServiceTeamDto taskDto = convertToFieldServiceTeamDto(data.object);
                    FieldServiceTeamDto createdFlialDto = kafkaFieldServiceTeamService.create(taskDto);
                    KafkaResponse message = KafkaResponse.builder().
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
                    FieldServiceTeamDto taskDtoUpdate = convertToFieldServiceTeamDto(data.object);
                    FieldServiceTeamDto updatedFlialDto = kafkaFieldServiceTeamService.update(data.elementId, taskDtoUpdate);
                    KafkaResponse message = KafkaResponse.builder().
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
                    FieldServiceTeamDto deletedFlialDto = kafkaFieldServiceTeamService.delete(data.elementId);
                    KafkaResponse message = KafkaResponse.builder().
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

    private FieldServiceTeamDto convertToFieldServiceTeamDto(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String result = null;
        FieldServiceTeamDto taskDto = null;
        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            // Преобразуем объект в JSON-строку, если это необходимо
            // Десериализуем JSON-строку в объект FieldServiceTeamDto
            taskDto = mapper.readValue(result, FieldServiceTeamDto.class);

        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при преобразовании объекта в FieldServiceTeamDto: " + e.getMessage());
        }

        return taskDto;
    }

    private void sendResponse(KafkaResponse response) {
        kafkaControllerProducer.publishResponse(response);
        // Логика отправки ответа в Kafka
    }
}