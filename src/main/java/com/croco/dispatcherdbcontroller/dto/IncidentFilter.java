package com.croco.dispatcherdbcontroller.dto;

import com.croco.dispatcherdbcontroller.entity.IncidentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class IncidentFilter {
    public UserDto user;
    public Boolean myRequests;
    public List<IncidentStatus> statuses;
    public  String startDate;
    public  String endDate;
    public Map<String, String> attributes;

    // Конструктор по умолчанию
    public IncidentFilter(UserDto user, Boolean myRequests, List<IncidentStatus> statuses, String startDate, String endDate, Map<String, String> attributes) {
        this.user = user;
        this.myRequests = myRequests;
        this.statuses = statuses;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attributes = attributes;
    }

    // Конструктор по умолчанию
    public IncidentFilter() {
        this.user = null;
        this.myRequests = null;
        this.statuses = null;
        this.startDate = null;
        this.endDate = null;
        this.attributes = null;
    }
    // Метод для сериализации в строку
    public String serialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    // Статический метод для десериализации из строки
    public static IncidentFilter deserialize(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, IncidentFilter.class);
    }
}