package com.croco.dispatcherdbcontroller.dto;

import com.croco.dispatcherdbcontroller.entity.UserStatus;
import com.croco.dispatcherdbcontroller.entity.UserType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements BasicDto, UserDetails {

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

    @JsonCreator
    public UserDto(
            @JsonProperty("id") Long id,
            @JsonProperty("nameStr") @NotNull @Size(max = 255) String nameStr,
            @JsonProperty("emailStr") @NotNull @Size(max = 255) String emailStr,
            @JsonProperty("userNameStr") @Size(max = 255) String userNameStr,
            @JsonProperty("passwordByte") byte[] passwordByte,
            @JsonProperty("ticketByte") byte[] ticketByte,
            @JsonProperty("userStatus") @NotNull UserStatus userStatus,
            @JsonProperty("userType") @NotNull UserType userType,
            @JsonProperty("accessMask") @NotNull Map<String, Object> accessMask,
            @JsonProperty("settings") @NotNull Map<String, Object> settings) {
        this.id = id;
        this.nameStr = nameStr;
        this.emailStr = emailStr;
        this.userNameStr = userNameStr;
        this.passwordByte = passwordByte;
        this.ticketByte = ticketByte;
        this.userStatus = userStatus;
        this.userType = userType;
        this.accessMask = accessMask;
        this.settings = settings;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return new String(this.passwordByte); // Преобразуем массив байтов в строку
    }

    @Override
    public String getUsername() {
        return this.userNameStr;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}