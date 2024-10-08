package com.croco.dispatcherdbcontroller.api.clients.impl;

import com.croco.dispatcherdbcontroller.dto.MapDto;
import com.croco.dispatcherdbcontroller.entity.Map;
import com.croco.dispatcherdbcontroller.mapper.MapMapper;
import com.croco.dispatcherdbcontroller.repository.MapRepository;
import com.croco.dispatcherdbcontroller.api.clients.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MapServiceImpl implements MapService {
    private final MapRepository mapRepository;
    private final MapMapper mapMapper;

    public MapServiceImpl(MapRepository mapRepository, MapMapper mapMapper) {
        this.mapRepository = mapRepository;
        this.mapMapper = mapMapper;
    }

    @Override
    public List<MapDto> getList() {
        List<Map> maps = mapRepository.findAll();
        return mapMapper.toDto(maps);
    }

    @Override
    public MapDto getOne(Long id) {
        Map map = mapRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Map not found"));
        return mapMapper.toDto(map);
    }

    @Override
    public MapDto create(MapDto mapDto) {
        Map map = mapMapper.toEntity(mapDto);
        Map savedMap = mapRepository.save(map);
        return mapMapper.toDto(savedMap);
    }

    @Override
    public MapDto update(Long id, MapDto mapDto) {
        Map existingMap = mapRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Map not found"));
        mapMapper.partialUpdate(mapDto, existingMap); // Метод для частичного обновления
        return mapMapper.toDto(mapRepository.save(existingMap));
    }

    @Override
    public MapDto patch(Long id, MapDto mapDto) {
        if (!mapRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }
        Map map = mapRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        mapMapper.partialUpdate(mapDto, map);
        return mapMapper.toDto(mapRepository.save(map));
    }

    @Override
    public MapDto delete(Long id) {
        Map map = mapRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Map not found"));
        mapRepository.delete(map);
        return mapMapper.toDto(map);
    }
}