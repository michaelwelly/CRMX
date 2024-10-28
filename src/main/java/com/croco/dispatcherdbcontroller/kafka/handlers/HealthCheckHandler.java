package com.croco.dispatcherdbcontroller.kafka.handlers;


import com.croco.dispatcherdbcontroller.kafka.DefaultProducer;
import com.croco.dispatcherdbcontroller.kafka.MessageHandler;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaMessage;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaResponse;

public class HealthCheckHandler implements MessageHandler {

    private final DefaultProducer kafkaControllerProducer;
    public HealthCheckHandler( DefaultProducer kafkaControllerProducer) {
        this.kafkaControllerProducer = kafkaControllerProducer;
    }

    @Override
    public void handle(KafkaMessage data) {

                if (data.object == null) {
                    KafkaResponse message = KafkaResponse.builder().
                                id(data.id).
                                version("1").
                                authToken(data.authToken).
                                md5Signature(data.md5Signature).
                                entityType(data.entityType).
                                action(data.action).
                                build();

                    sendResponse(message);

        }
    }

    private void sendResponse(KafkaResponse response) {
        kafkaControllerProducer.publishResponse(response);
        // Логика отправки ответа в Kafka
    }
}