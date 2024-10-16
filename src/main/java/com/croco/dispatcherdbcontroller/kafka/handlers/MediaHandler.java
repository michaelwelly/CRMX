package com.croco.dispatcherdbcontroller.kafka.handlers;

import com.croco.dispatcherdbcontroller.api.clients.MediaService;
import com.croco.dispatcherdbcontroller.dto.MediaDto;
import com.croco.dispatcherdbcontroller.kafka.DefaultProducer;
import com.croco.dispatcherdbcontroller.kafka.MessageHandler;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaMessage;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Collections;
import java.util.List;

public class MediaHandler implements MessageHandler {
    private final MediaService kafkaMediaService;
    private final DefaultProducer kafkaControllerProducer;
    public MediaHandler(MediaService kafkaMediaService, DefaultProducer kafkaControllerProducer) {
        this.kafkaMediaService = kafkaMediaService;
        this.kafkaControllerProducer = kafkaControllerProducer;
    }

    @Override
    public void handle(KafkaMessage data) {
        switch (data.action) {
            case GET:
                if (data.object == null) {
                    MediaDto getFlialDto = null;
                    List<MediaDto> getFlialsDtos = null;
                    KafkaResponse message = null;
                    if (data.elementId != null) {
                        getFlialDto = kafkaMediaService.getOne(data.elementId);
                        message = KafkaResponse.builder().
                                id(data.id).
                                version(data.version).
                                authToken(data.authToken).
                                md5Signature(data.md5Signature).
                                entityType(data.entityType).
                                action(data.action).
                                object(getFlialDto).
                                oldObject(data.getOldObject()).
                                elementId(getFlialDto.getId()).
                                build();
                    } else {
                        getFlialsDtos = kafkaMediaService.getList();
                        message = KafkaResponse.builder().
                                id(data.id).
                                version(data.version).
                                authToken(data.authToken).
                                md5Signature(data.md5Signature).
                                entityType(data.entityType).
                                action(data.action).
                                object(getFlialDto).
                                objectsList(Collections.singletonList(getFlialsDtos)).
                                oldObject(data.object).
                                build();
                    }
                    sendResponse(message);
                }
            case CREATE:
                if (data.object != null) {
                    MediaDto mediaDto = convertToMediaDto(data.object);
                    MediaDto createdFlialDto = kafkaMediaService.create(mediaDto);
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
                    MediaDto mediaDtoUpdate = convertToMediaDto(data.object);
                    MediaDto updatedFlialDto = kafkaMediaService.update(data.elementId, mediaDtoUpdate);
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
                    MediaDto deletedFlialDto = kafkaMediaService.delete(data.elementId);
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

    private MediaDto convertToMediaDto(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String result = null;
        MediaDto mediaDto = null;
        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            // Преобразуем объект в JSON-строку, если это необходимо
            // Десериализуем JSON-строку в объект MediaDto
            mediaDto = mapper.readValue(result, MediaDto.class);

        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при преобразовании объекта в MediaDto: " + e.getMessage());
        }

        return mediaDto;
    }

    private void sendResponse(KafkaResponse response) {
        kafkaControllerProducer.publishResponse(response);
        // Логика отправки ответа в Kafka
    }
}