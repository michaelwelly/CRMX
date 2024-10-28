package com.croco.dispatcherdbcontroller.kafka.handlers;

import com.croco.dispatcherdbcontroller.api.clients.IncidentService;
import com.croco.dispatcherdbcontroller.dto.IncidentDto;
import com.croco.dispatcherdbcontroller.kafka.DefaultProducer;
import com.croco.dispatcherdbcontroller.kafka.MessageHandler;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaMessage;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Collections;
import java.util.List;

public class IncidentHandler implements MessageHandler {
    private final IncidentService kafkaIncidentService;
    private final DefaultProducer kafkaControllerProducer;

    public IncidentHandler(IncidentService kafkaIncidentService, DefaultProducer kafkaControllerProducer) {
        this.kafkaIncidentService = kafkaIncidentService;
        this.kafkaControllerProducer = kafkaControllerProducer;
    }

    @Override
    public void handle(KafkaMessage data) {
        KafkaResponse message = null;
        switch (data.action) {
            case GET:
                IncidentDto getFlialDto = null;
                List<IncidentDto> getFlialsDtos = null;
                if (data.url != null) {
                    getFlialsDtos = kafkaIncidentService.getFilteredIncidentsFromUrl(data.url);
                    message = KafkaResponse.builder()
                            .id(data.id)
                            .version(data.version)
                            .authToken(data.authToken)
                            .md5Signature(data.md5Signature)
                            .entityType(data.entityType)
                            .action(data.action)
                            .object(getFlialDto)
                            .objectsList(Collections.singletonList(getFlialsDtos))
                            .oldObject(data.object)
                            .build();
                } else if (data.object == null) {
                    if (data.elementId != null) {
                        getFlialDto = kafkaIncidentService.getOne(data.elementId);
                        message = KafkaResponse.builder()
                                .id(data.id)
                                .version(data.version)
                                .authToken(data.authToken)
                                .md5Signature(data.md5Signature)
                                .entityType(data.entityType)
                                .action(data.action)
                                .object(getFlialDto)
                                .oldObject(data.getOldObject())
                                .elementId(getFlialDto.getId())
                                .build();
                    } else {
                        getFlialsDtos = kafkaIncidentService.getList();
                        message = KafkaResponse.builder()
                                .id(data.id)
                                .version(data.version)
                                .authToken(data.authToken)
                                .md5Signature(data.md5Signature)
                                .entityType(data.entityType)
                                .action(data.action)
                                .object(getFlialDto)
                                .objectsList(Collections.singletonList(getFlialsDtos))
                                .oldObject(data.object)
                                .build();
                    }
                }
                sendResponse(message);
                break;
            case CREATE:
                if (data.object != null) {
                    IncidentDto incidentDto = convertToIncidentDto(data.object);
                    IncidentDto createdFlialDto = kafkaIncidentService.create(incidentDto);
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
                    IncidentDto incidentDtoUpdate = convertToIncidentDto(data.object);
                    IncidentDto updatedFlialDto = kafkaIncidentService.update(data.elementId, incidentDtoUpdate);
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
                    IncidentDto deletedFlialDto = kafkaIncidentService.delete(data.elementId);
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

    private IncidentDto convertToIncidentDto(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String result = null;
        IncidentDto incidentDto = null;
        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            // Преобразуем объект в JSON-строку, если это необходимо
            // Десериализуем JSON-строку в объект IncidentDto
            incidentDto = mapper.readValue(result, IncidentDto.class);

        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при преобразовании объекта в IncidentDto: " + e.getMessage());
        }

        return incidentDto;
    }

    private void sendResponse(KafkaResponse response) {
        kafkaControllerProducer.publishResponse(response);
        // Логика отправки ответа в Kafka
    }
}