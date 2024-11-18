package com.croco.dispatcherdbcontroller.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "incident")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = true)
    @JoinColumn(name = "reporter_id")
    private Reporter reporter;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "operator_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @NotNull
    @Column(name = "incident_type", nullable = false)
    private IncidentType incidentType;

    @Column(name = "registration_dttm")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_WITH_TIMEZONE)
    private OffsetDateTime registrationDttm;

    @NotNull
    @Column(name = "exec_dttm", nullable = false)
    @JdbcTypeCode(SqlTypes.TIMESTAMP_WITH_TIMEZONE)
    private OffsetDateTime execDttm;

    @Column(name = "synchronization_dttm")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_WITH_TIMEZONE)
    private OffsetDateTime synchronizationDttm;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "location_type", nullable = false)
    private LocationType locationType;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "incident_status", nullable = false)
    private IncidentStatus incidentStatus;

    @Column(name = "description_text", length = Integer.MAX_VALUE)
    private String descriptionText;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = true)
    @JoinColumn(name = "filial_id", nullable = false)
    private Filial filial;

    @NotNull
    @Column(name = "address_json", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> addressJson;

    @NotNull
    @Column(name = "attributes_json", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> attributesJson;

    @Column(name = "address_str", length = Integer.MAX_VALUE)
    private String addressStr;

    @Column(name = "changed_dttm")
    private OffsetDateTime changedDttm;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "team_id")
    private FieldServiceTeam team;

    @OneToMany(mappedBy = "incident", fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new LinkedHashSet<>();

}