package com.croco.dispatcherdbcontroller.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @Column(name = "incident_id")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> incidentId;

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

    @Column(name = "is_complete_flag")
    private Boolean isCompleteFlag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medias", referencedColumnName = "collection_id")
    private Media medias;

    @Column(name = "to_begin_work_dttm")
    private OffsetDateTime toBeginWorkDttm;

    @Column(name = "begin_work_dttm")
    private OffsetDateTime beginWorkDttm;

    @Column(name = "complete_work_dttm")
    private OffsetDateTime completeWorkDttm;

    @Column(name = "collection_id")
    private Integer collectionId;

    @OneToMany(mappedBy = "tasks")
    private Set<Incident> incidents = new LinkedHashSet<>();

}