package com.croco.dispatcherdbcontroller.kafka.handlers;

import com.croco.dispatcherdbcontroller.api.clients.ReporterService;
import com.croco.dispatcherdbcontroller.dto.ReporterDto;
import com.croco.dispatcherdbcontroller.kafka.DefaultProducer;
import com.croco.dispatcherdbcontroller.kafka.MessageHandler;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaMessage;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Collections;
import java.util.List;

public class ReporterHandler implements MessageHandler {
    private final ReporterService kafkaReporterService;
    private final DefaultProducer kafkaControllerProducer;
    public ReporterHandler(ReporterService kafkaReporterService, DefaultProducer kafkaControllerProducer) {
        this.kafkaReporterService = kafkaReporterService;
        this.kafkaControllerProducer = kafkaControllerProducer;
    }

    @Override
    public void handle(KafkaMessage data) {
        switch (data.action) {
            case GET:
                if (data.object == null) {
                    ReporterDto getReporterDto = null;
                    List<ReporterDto> getReporterDtos = null;
                    KafkaResponse message = null;
                    if (data.elementId != null) {
                        getReporterDto = kafkaReporterService.getOne(data.elementId);
                        message = KafkaResponse.builder().
                                id(data.id).
                                version(data.version).
                                authToken(data.authToken).
                                md5Signature(data.md5Signature).
                                entityType(data.entityType).
                                action(data.action).
                                object(getReporterDto).
                                oldObject(data.getOldObject()).
                                elementId(getReporterDto.getId()).
                                build();
                    } else {
                        getReporterDtos = kafkaReporterService.getList();
                        message = KafkaResponse.builder().
                                id(data.id).
                                version(data.version).
                                authToken(data.authToken).
                                md5Signature(data.md5Signature).
                                entityType(data.entityType).
                                action(data.action).
                                object(getReporterDto).
                                objectsList(Collections.singletonList(getReporterDtos)).
                                oldObject(data.object).
                                build();
                    }
                    sendResponse(message);
                }
            case CREATE:
                if (data.object != null) {
                    ReporterDto reporterDto = convertToReporterDto(data.object);
                    ReporterDto createdFlialDto = kafkaReporterService.create(reporterDto);
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
                    ReporterDto reporterDtoUpdate = convertToReporterDto(data.object);
                    ReporterDto updatedFlialDto = kafkaReporterService.update(data.elementId, reporterDtoUpdate);
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
                    ReporterDto deletedFlialDto = kafkaReporterService.delete(data.elementId);
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

    private ReporterDto convertToReporterDto(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String result = null;
        ReporterDto reporterDto = null;
        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            // Преобразуем объект в JSON-строку, если это необходимо
            // Десериализуем JSON-строку в объект ReporterDto
            reporterDto = mapper.readValue(result, ReporterDto.class);

        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при преобразовании объекта в ReporterDto: " + e.getMessage());
        }

        return reporterDto;
    }

    private void sendResponse(KafkaResponse response) {
        kafkaControllerProducer.publishResponse(response);
        // Логика отправки ответа в Kafka
    }
}