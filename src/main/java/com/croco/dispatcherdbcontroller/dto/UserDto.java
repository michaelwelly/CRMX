package com.croco.dispatcherdbcontroller.dto;

import com.croco.dispatcherdbcontroller.entity.UserStatus;
import com.croco.dispatcherdbcontroller.entity.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Map;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    Long id;
    @NotNull
    @Size(max = 255)
    String nameStr;
    @NotNull
    @Size(max = 255)
    String emailStr;
    @Size(max = 255)
    String userNameStr;
    byte[] passwordByte;
    byte[] ticketByte;
    @NotNull
    UserStatus userStatus;
    @NotNull
    UserType userType;
    @NotNull
    Map<String, Object> accessMask;
    @NotNull
    Map<String, Object> settings;
}