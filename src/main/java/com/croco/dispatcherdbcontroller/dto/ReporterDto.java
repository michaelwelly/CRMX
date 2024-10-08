package com.croco.dispatcherdbcontroller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Map;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReporterDto {
    Long id;
    @Size(max = 100)
    String nameStr;
    @NotNull
    @Size(max = 100)
    String phoneStr;
    String descriptionTxt;
    Map<String, Object> attributesJson;
}