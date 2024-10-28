package com.croco.dispatcherdbcontroller.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage {
    public UUID id;
    public CRUDAction action;
    public Long elementId;
    public String version;
    public String authToken;
    public String md5Signature;
    public EntityType entityType;
    public Object oldObject;
    public Object object;
    public String url;
}



