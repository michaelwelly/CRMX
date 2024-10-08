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
@Table(name = "reporter")
public class Reporter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "name_str", length = 100)
    private String nameStr;

    @Size(max = 100)
    @NotNull
    @Column(name = "phone_str", nullable = false, length = 100)
    private String phoneStr;

    @Column(name = "description_txt", length = Integer.MAX_VALUE)
    private String descriptionTxt;

    @Column(name = "attributes_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> attributesJson;

    @Column(name = "description_text", length = Integer.MAX_VALUE)
    private String descriptionText;

    @OneToMany(mappedBy = "reporter")
    private Set<Incident> incidents = new LinkedHashSet<>();

}