package com.croco.dispatcherdbcontroller.kafka.model;

import com.croco.dispatcherdbcontroller.kafka.serializer.ObjectListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KafkaResponse {
    public UUID id;
    public CRUDAction action;
    public Long elementId;
    public String version;
    public String authToken;
    public String md5Signature;
    public EntityType entityType;
    public Object oldObject;
    public Object object;
    public List<Object> objectsList;
}



