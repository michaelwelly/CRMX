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
public class UserFilter {
    public String userName;
    public Boolean isActive;
    public List<IncidentStatus> statuses;
    public  String startDate;
    public  String endDate;
    public Map<String, String> attributes;

    // Конструктор по умолчанию
    public UserFilter(String userName, Boolean isActive, List<IncidentStatus> statuses, String startDate, String endDate, Map<String, String> attributes) {
        this.userName = userName;
        this.isActive = isActive;
        this.statuses = statuses;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attributes = attributes;
    }

    // Конструктор по умолчанию
    public UserFilter() {
        this.userName = null;
        this.isActive = null;
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
    public static UserFilter deserialize(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, UserFilter.class);
    }
}