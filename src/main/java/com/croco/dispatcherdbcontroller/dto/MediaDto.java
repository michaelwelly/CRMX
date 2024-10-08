package com.croco.dispatcherdbcontroller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
public class MediaDto {
    private Long id;
    private Map<String, Object> attributesJson;
    private Integer collectionId;
    private OffsetDateTime creationDateDttm;
}