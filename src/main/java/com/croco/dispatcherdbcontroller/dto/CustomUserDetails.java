package com.croco.dispatcherdbcontroller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    private Long userId;
    private String loginName;
    private String sessionToken;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long userId, String loginName, String sessionToken,
                             Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.loginName = loginName;
        this.sessionToken = sessionToken;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // Пароль не нужен после авторизации
    }

    @Override
    public String getUsername() {
        return loginName;
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

    public Long getUserId() {
        return userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}