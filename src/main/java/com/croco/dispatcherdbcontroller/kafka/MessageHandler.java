package com.croco.dispatcherdbcontroller.kafka;

import com.croco.dispatcherdbcontroller.kafka.model.KafkaMessage;

public interface MessageHandler {
    void handle(KafkaMessage data);
}