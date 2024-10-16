package com.croco.dispatcherdbcontroller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.sql.Timestamp;

@Getter
@Setter
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSessionDto implements BasicDto{
    private int id;
    private Long userId;
    private Timestamp startSessionDttm;
    private Timestamp endSessionDttm;
    private String deviceStr;
    private String ipStr;

    @JsonCreator
    public UserSessionDto(
            @JsonProperty("id") int id,
            @JsonProperty("userId") Long userId,
            @JsonProperty("startSessionDttm") Timestamp startSessionDttm,
            @JsonProperty("endSessionDttm") Timestamp endSessionDttm,
            @JsonProperty("deviceStr") String deviceStr,
            @JsonProperty("ipStr") String ipStr) {
        this.id = id;
        this.userId = userId;
        this.startSessionDttm = startSessionDttm;
        this.endSessionDttm = endSessionDttm;
        this.deviceStr = deviceStr;
        this.ipStr = ipStr;
    }

}