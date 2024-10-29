package com.croco.dispatcherdbcontroller.kafka.handlers;

import com.croco.dispatcherdbcontroller.api.clients.FilialService;
import com.croco.dispatcherdbcontroller.dto.FilialDto;
import com.croco.dispatcherdbcontroller.kafka.DefaultProducer;
import com.croco.dispatcherdbcontroller.kafka.MessageHandler;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaMessage;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Collections;
import java.util.List;

public class FilialHandler implements MessageHandler {
    private final FilialService kafkaFilialService;
    private final DefaultProducer kafkaControllerProducer;
    public FilialHandler(FilialService kafkaFilialService, DefaultProducer kafkaControllerProducer) {
        this.kafkaFilialService = kafkaFilialService;
        this.kafkaControllerProducer = kafkaControllerProducer;
    }

    @Override
    public void handle(KafkaMessage data) {
        switch (data.action) {
            case GET:
                if (data.object == null) {
                    FilialDto getFilialDto = null;
                    List<FilialDto> getFilialsDtos = null;
                    KafkaResponse message = null;
                    if (data.elementId != null) {
                        getFilialDto = kafkaFilialService.getOne(data.elementId);
                        message = KafkaResponse.builder().
                                id(data.id).
                                version(data.version).
                                authToken(data.authToken).
                                md5Signature(data.md5Signature).
                                entityType(data.entityType).
                                action(data.action).
                                object(getFilialDto).
                                oldObject(data.getOldObject()).
                                elementId(getFilialDto.getId()).
                                build();
                    } else {
                        getFilialsDtos = kafkaFilialService.getList();
                        message = KafkaResponse.builder().
                                id(data.id).
                                version(data.version).
                                authToken(data.authToken).
                                md5Signature(data.md5Signature).
                                entityType(data.entityType).
                                action(data.action).
                                object(getFilialDto).
                                objectsList(Collections.singletonList(getFilialsDtos)).
                                oldObject(data.object).
                                build();
                    }
                    sendResponse(message);
                }
            case CREATE:
                if (data.object != null) {
                    FilialDto filialDto = convertToFilialDto(data.object);
                    FilialDto createdFlialDto = kafkaFilialService.create(filialDto);
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
                    FilialDto filialDtoUpdate = convertToFilialDto(data.object);
                    FilialDto updatedFlialDto = kafkaFilialService.update(data.elementId, filialDtoUpdate);
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
                    FilialDto deletedFlialDto = kafkaFilialService.delete(data.elementId);
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

    private FilialDto convertToFilialDto(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String result = null;
        FilialDto filialDto = null;
        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            // Преобразуем объект в JSON-строку, если это необходимо
            // Десериализуем JSON-строку в объект FilialDto
            filialDto = mapper.readValue(result, FilialDto.class);

        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при преобразовании объекта в FilialDto: " + e.getMessage());
        }

        return filialDto;
    }

    private void sendResponse(KafkaResponse response) {
        kafkaControllerProducer.publishResponse(response);
        // Логика отправки ответа в Kafka
    }
}