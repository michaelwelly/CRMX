package com.croco.dispatcherdbcontroller.dto;

import com.croco.dispatcherdbcontroller.entity.MapType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MapDto {
    private Long id;

    @NotNull
    private String titleStr;

    private String descriptionTxt;

    private Map<String, Object> attributesJson;

    private MapType mapType;
}