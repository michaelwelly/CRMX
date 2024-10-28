package com.croco.dispatcherdbcontroller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;


@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthCheckDto implements BasicDto {

    String responce;

    // Конструктор с аннотацией @JsonCreator
    @JsonCreator
    public HealthCheckDto(
            @JsonProperty("responce") String responce) {
        this.responce = responce;
    }
}