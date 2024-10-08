package com.croco.dispatcherdbcontroller.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "map")
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "title_str", nullable = false, length = Integer.MAX_VALUE)
    private String titleStr;

    @Column(name = "description_txt", length = Integer.MAX_VALUE)
    private String descriptionTxt;

    @Column(name = "attributes_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private java.util.Map<String, Object> attributesJson;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "map_type", columnDefinition = "maptype")
    private MapType mapType;

}