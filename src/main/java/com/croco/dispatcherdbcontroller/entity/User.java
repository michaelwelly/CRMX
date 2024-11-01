package com.croco.dispatcherdbcontroller.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name_str", nullable = false)
    private String nameStr;

    @Size(max = 255)
    @NotNull
    @Column(name = "email_str", nullable = false)
    private String emailStr;

    @Size(max = 255)
    @Column(name = "user_name_str")
    private String userNameStr;

    @Column(name = "password_byte")
    private byte[] passwordByte;

    @Column(name = "ticket_byte")
    private byte[] ticketByte;

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Basic(optional = false)
    private UserStatus userStatus;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Basic(optional = false)
    private UserType userType;

    @NotNull
    @Column(name = "access_mask", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> accessMask;

    @NotNull
    @Column(name = "settings", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> settings;

    @OneToMany(mappedBy = "user")
    private Set<Incident> incidents = new LinkedHashSet<>();

}