package com.croco.dispatcherdbcontroller.kafka;

import com.croco.dispatcherdbcontroller.kafka.model.KafkaResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class DefaultProducer {

    @Value("${spring.kafka.controller.producer.kafka.enabled}")
    private boolean kafkaEnabled;

    @Value("${spring.kafka.data.response.topic}")
    private String kafkaTopic;

    @Autowired
    @Qualifier("stringKafkaTemplate")
    private KafkaTemplate<String, String> stringKafkaTemplate;

    public void publishResponse(KafkaResponse response)
    {

        String message = buildMessage(response);
        sendMessage(kafkaTopic, message);
    }

    private void sendMessage(String topic, String message)
    {

        if (topic == null || topic.isEmpty()) {

            IllegalArgumentException e = new IllegalArgumentException("no topics provided");
            log.error(e.toString());
            throw e;
        }

        if (shouldSend()) {

            log.debug(String.format("KafkaProducer: message %1$s sent to topic %2$s", message, topic));
            stringKafkaTemplate.send(topic, message);
        }
    }

    private boolean shouldSend()
    {

        if (!kafkaEnabled) {

            log.debug("Skipping Kafka send operation. Disabled in ${org.knowledge4retail.api.producer.kafka.enabled}");
            return false;
        }

        return true;
    }

    public String buildMessage(KafkaResponse response)
    {
        String result = null;

        KafkaResponse message = KafkaResponse.builder().
                id(response.id).
                version(response.version).
                authToken(response.authToken).
                md5Signature(response.md5Signature).
                entityType(response.entityType).
                action(response.action).
                object(response.object).
                oldObject(response.oldObject).
                elementId(response.elementId).
                objectsList(response.objectsList).
                build();

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // do not include Object if it's null
            result = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {

            log.error(String.format("Error creating KafkaMessage from payload %s", response.toString()), e);
        }

        return result;
    }
}
