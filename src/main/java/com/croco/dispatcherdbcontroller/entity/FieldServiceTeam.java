package com.croco.dispatcherdbcontroller.entity;

import jakarta.persistence.CascadeType;
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
@Table(name = "field_service_team")
public class FieldServiceTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "workers", referencedColumnName = "collection_id")
    private Worker workers;

    @Column(name = "name_str", length = Integer.MAX_VALUE)
    private String nameStr;

    @Column(name = "attributes_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> attributesJson;

    @Column(name = "create_date_dttm")
    private OffsetDateTime createDateDttm;

    @Column(name = "is_active_flag")
    private Boolean isActiveFlag;

    @Column(name = "close_date_dttm")
    private OffsetDateTime closeDateDttm;

    @OneToMany(mappedBy = "team")
    private Set<Incident> incidents = new LinkedHashSet<>();

}