package com.croco.dispatcherdbcontroller.kafka.handlers;

import com.croco.dispatcherdbcontroller.api.clients.WorkerService;
import com.croco.dispatcherdbcontroller.dto.WorkerDto;
import com.croco.dispatcherdbcontroller.kafka.DefaultProducer;
import com.croco.dispatcherdbcontroller.kafka.MessageHandler;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaMessage;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Collections;
import java.util.List;

public class WorkerHandler implements MessageHandler {
    private final WorkerService kafkaWorkerService;
    private final DefaultProducer kafkaControllerProducer;
    public WorkerHandler(WorkerService kafkaWorkerService, DefaultProducer kafkaControllerProducer) {
        this.kafkaWorkerService = kafkaWorkerService;
        this.kafkaControllerProducer = kafkaControllerProducer;
    }

    @Override
    public void handle(KafkaMessage data) {
        switch (data.action) {
            case GET:
                if (data.object == null) {
                    WorkerDto getWorkerDto = null;
                    List<WorkerDto> getWorkerDtos = null;
                    KafkaResponse message = null;
                    if (data.elementId != null) {
                        getWorkerDto = kafkaWorkerService.getOne(data.elementId);
                        message = KafkaResponse.builder().
                                id(data.id).
                                version(data.version).
                                authToken(data.authToken).
                                md5Signature(data.md5Signature).
                                entityType(data.entityType).
                                action(data.action).
                                object(getWorkerDto).
                                oldObject(data.getOldObject()).
                                elementId(getWorkerDto.getId()).
                                build();
                    } else {
                        getWorkerDtos = kafkaWorkerService.getList();
                        message = KafkaResponse.builder().
                                id(data.id).
                                version(data.version).
                                authToken(data.authToken).
                                md5Signature(data.md5Signature).
                                entityType(data.entityType).
                                action(data.action).
                                object(getWorkerDto).
                                objectsList(Collections.singletonList(getWorkerDtos)).
                                oldObject(data.object).
                                build();
                    }
                    sendResponse(message);
                }
            case CREATE:
                if (data.object != null) {
                    WorkerDto workerDto = convertToWorkerDto(data.object);
                    WorkerDto createdFlialDto = kafkaWorkerService.create(workerDto);
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
                    WorkerDto workerDtoUpdate = convertToWorkerDto(data.object);
                    WorkerDto updatedFlialDto = kafkaWorkerService.update(data.elementId, workerDtoUpdate);
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
                    WorkerDto deletedFlialDto = kafkaWorkerService.delete(data.elementId);
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

    private WorkerDto convertToWorkerDto(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String result = null;
        WorkerDto workerDto = null;
        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            // Преобразуем объект в JSON-строку, если это необходимо
            // Десериализуем JSON-строку в объект WorkerDto
            workerDto = mapper.readValue(result, WorkerDto.class);

        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при преобразовании объекта в WorkerDto: " + e.getMessage());
        }

        return workerDto;
    }

    private void sendResponse(KafkaResponse response) {
        kafkaControllerProducer.publishResponse(response);
        // Логика отправки ответа в Kafka
    }
}