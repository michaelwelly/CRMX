package com.croco.dispatcherdbcontroller.kafka.deserializer;

import com.croco.dispatcherdbcontroller.kafka.model.KafkaMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class KafkaMessageDeserializer implements Deserializer<KafkaMessage> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Настройка десериализатора, если необходимо
    }

    @Override
    public KafkaMessage deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, KafkaMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON to KafkaMessage", e);
        }
    }

    @Override
    public void close() {
        // Освобождение ресурсов, если необходимо
    }
}