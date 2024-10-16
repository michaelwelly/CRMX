package com.croco.dispatcherdbcontroller.api.clients.impl;

import com.croco.dispatcherdbcontroller.dto.FieldServiceTeamDto;
import com.croco.dispatcherdbcontroller.entity.FieldServiceTeam;
import com.croco.dispatcherdbcontroller.mapper.FieldServiceTeamMapper;
import com.croco.dispatcherdbcontroller.repository.FieldServiceTeamRepository;
import com.croco.dispatcherdbcontroller.api.clients.FieldServiceTeamService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FieldServiceTeamServiceImpl implements FieldServiceTeamService {
    private final FieldServiceTeamRepository fieldServiceTeamRepository;
    private final FieldServiceTeamMapper fieldServiceTeamMapper;

    public FieldServiceTeamServiceImpl(FieldServiceTeamRepository fieldServiceTeamRepository, FieldServiceTeamMapper fieldServiceTeamMapper) {
        this.fieldServiceTeamRepository = fieldServiceTeamRepository;
        this.fieldServiceTeamMapper = fieldServiceTeamMapper;
    }

    @Override
    public List<FieldServiceTeamDto> getList() {
        List<FieldServiceTeam> fieldServiceTeams = fieldServiceTeamRepository.findAllWithWorkers();
        return fieldServiceTeamMapper.toDto(fieldServiceTeams);
    }

    @Override
    public FieldServiceTeamDto getOne(Long id) {
        FieldServiceTeam fieldServiceTeam = fieldServiceTeamRepository.getOneWithWorkers(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Field Service Team not found"));
        return fieldServiceTeamMapper.toDto(fieldServiceTeam);
    }

    @Override
    public FieldServiceTeamDto create(FieldServiceTeamDto fieldServiceTeamDto) {
        FieldServiceTeam fieldServiceTeam = fieldServiceTeamMapper.toEntity(fieldServiceTeamDto);
        FieldServiceTeam savedFieldServiceTeam = fieldServiceTeamRepository.save(fieldServiceTeam);
        return fieldServiceTeamMapper.toDto(savedFieldServiceTeam);
    }

    @Override
    public FieldServiceTeamDto update(Long id, FieldServiceTeamDto fieldServiceTeamDto) {
        FieldServiceTeam existingFieldServiceTeam = fieldServiceTeamRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Field Service Team not found"));
        fieldServiceTeamMapper.partialUpdate(fieldServiceTeamDto, existingFieldServiceTeam); // Метод для частичного обновления
        return fieldServiceTeamMapper.toDto(fieldServiceTeamRepository.save(existingFieldServiceTeam));
    }

    @Override
    public FieldServiceTeamDto patch(Long id, FieldServiceTeamDto fieldServiceTeamDto) {
        if (!fieldServiceTeamRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }
        FieldServiceTeam fieldServiceTeam = fieldServiceTeamRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        fieldServiceTeamMapper.partialUpdate(fieldServiceTeamDto, fieldServiceTeam);
        return fieldServiceTeamMapper.toDto(fieldServiceTeamRepository.save(fieldServiceTeam));
    }

    @Override
    public FieldServiceTeamDto delete(Long id) {
        FieldServiceTeam fieldServiceTeam = fieldServiceTeamRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Field Service Team not found"));
        fieldServiceTeamRepository.delete(fieldServiceTeam);
        return fieldServiceTeamMapper.toDto(fieldServiceTeam);
    }
}