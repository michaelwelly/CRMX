package com.croco.dispatcherdbcontroller.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "tasks_template")
public class TasksTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title_str", length = Integer.MAX_VALUE)
    private String titleStr;

    @Column(name = "order_num")
    private Integer orderNum;

    @Column(name = "attributes_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> attributesJson;
    @Column(name = "subtasks_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> subtasksJson;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "incident_type", columnDefinition = "incidenttype")
    private IncidentType incidentType;
}