package com.croco.dispatcherdbcontroller.dto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.Map;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaDto implements BasicDto{
    Long id;
    Map<String, Object> attributesJson;
    Integer collectionId;
    OffsetDateTime creationDateDttm;

    @JsonCreator
    public MediaDto(
            @JsonProperty("id") Long id,
            @JsonProperty("attributesJson") Map<String, Object> attributesJson,
            @JsonProperty("collectionId") Integer collectionId,
            @JsonProperty("creationDateDttm") OffsetDateTime creationDateDttm) {
        this.id = id;
        this.attributesJson = attributesJson;
        this.collectionId = collectionId;
        this.creationDateDttm = creationDateDttm;
    }
}