package com.croco.dispatcherdbcontroller.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "worker", indexes = {
        @Index(name = "idx_worker_collection_id", columnList = "collection_id", unique = true)
})
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name_str", length = Integer.MAX_VALUE)
    private String nameStr;

    @Column(name = "contacts_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> contactsJson;

    @Column(name = "collection_id")
    private Integer collectionId;

    @OneToMany(mappedBy = "worker")
    private Set<Task> tasks = new LinkedHashSet<>();
    @OneToMany(mappedBy = "workers")
    private Set<FieldServiceTeam> fieldServiceTeams = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "worker_type", columnDefinition = "workertype", nullable = false)
    private WorkerType workerType;

}