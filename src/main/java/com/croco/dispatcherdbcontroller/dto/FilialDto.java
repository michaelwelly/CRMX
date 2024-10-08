package com.croco.dispatcherdbcontroller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Map;


@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilialDto {
    Long id;
    @NotNull
    Map<String, Object> locations;
    @NotNull
    @Size(max = 255)
    String titleStr;
    String descriptionTxt;
    Map<String, Object> attributesJson;
}