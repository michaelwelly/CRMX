package com.croco.dispatcherdbcontroller.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "media", indexes = {
        @Index(name = "idx_media_collection_id", columnList = "collection_id", unique = true)
})
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "attributes_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> attributesJson;

    @Column(name = "collection_id")
    private Integer collectionId;

    @Column(name = "creation_date_dttm")
    private OffsetDateTime creationDateDttm;

    @OneToMany(mappedBy = "medias")
    private Set<Task> tasks = new LinkedHashSet<>();

}