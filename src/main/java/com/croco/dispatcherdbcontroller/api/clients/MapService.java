package com.croco.dispatcherdbcontroller.api.clients;

import com.croco.dispatcherdbcontroller.dto.MapDto;

import java.io.IOException;
import java.util.List;

public interface MapService {
    List<MapDto> getList();
    MapDto getOne(Long id);
    MapDto create(MapDto mapDto);
    MapDto update(Long id, MapDto mapDto);
    MapDto delete(Long id);
    MapDto patch(Long id, MapDto mapDto) throws IOException;
}