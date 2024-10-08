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

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "filial")
public class Filial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "locations", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> locations;

    @Size(max = 255)
    @NotNull
    @Column(name = "title_str", nullable = false)
    private String titleStr;

    @Column(name = "description_text", length = Integer.MAX_VALUE)
    private String descriptionTxt;

    @Column(name = "attributes_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> attributesJson;

    @OneToMany(mappedBy = "filial")
    private Set<Incident> incidents = new LinkedHashSet<>();

}