package com.croco.dispatcherdbcontroller.api.clients.impl;

import com.croco.dispatcherdbcontroller.dto.FilialDto;
import com.croco.dispatcherdbcontroller.entity.Filial;
import com.croco.dispatcherdbcontroller.mapper.FilialMapper;
import com.croco.dispatcherdbcontroller.repository.FilialRepository;
import com.croco.dispatcherdbcontroller.api.clients.FilialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FilialServiceImpl implements FilialService {
    private final FilialRepository filialRepository;
    private final FilialMapper filialMapper;
    private final ObjectMapper objectMapper;

    @Override
    public List<FilialDto> getList() {
        List<Filial> filials = filialRepository.findAll();
        return filialMapper.toDto(filials);
    }

    @Override
    public FilialDto getOne(Long id) {
        Filial filial = filialRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        return filialMapper.toDto(filial);
    }

    @Override
    public List<FilialDto> getMany(List<Long> ids) {
        List<Filial> filials = filialRepository.findAllById(ids);
        return filialMapper.toDto(filials);
    }

    @Override
    public FilialDto create(FilialDto filialDto) {
        Filial filial = filialMapper.toEntity(filialDto);
        Filial savedFilial = filialRepository.save(filial);
        return filialMapper.toDto(savedFilial);
    }

    @Override
    public FilialDto patch(Long id, FilialDto filialDto) {
        if (!filialRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }
        Filial filial = filialRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        filialMapper.partialUpdate(filialDto, filial);
        return filialMapper.toDto(filialRepository.save(filial));
    }

    @Override
    public FilialDto update(Long id, FilialDto filialDto) {
        if (!filialRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }
        // Используем маппер для обновления сущности
        Filial existingFilial = filialRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        filialMapper.partialUpdate(filialDto, existingFilial);
        return filialMapper.toDto(filialRepository.save(existingFilial));
    }

    @Override
    public FilialDto delete(Long id) {
        Filial filial = filialRepository.findById(id).orElse(null);
        if (filial != null) {
            filialRepository.delete(filial);
            return filialMapper.toDto(filial);
        }
        return null;
    }

    @Override
    public void deleteMany(List<Long> ids) {
        filialRepository.deleteAllById(ids);
    }


}