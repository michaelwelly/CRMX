package com.croco.dispatcherdbcontroller.api.clients.impl;

import com.croco.dispatcherdbcontroller.dto.ReporterDto;
import com.croco.dispatcherdbcontroller.entity.Reporter;
import com.croco.dispatcherdbcontroller.mapper.ReporterMapper;
import com.croco.dispatcherdbcontroller.repository.ReporterRepository;
import com.croco.dispatcherdbcontroller.api.clients.ReporterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReporterServiceImpl implements ReporterService {
    private final ReporterRepository reporterRepository;
    private final ReporterMapper reporterMapper;
    private final ObjectMapper objectMapper;

    @Override
    public List<ReporterDto> getList() {
        List<Reporter> reporters = reporterRepository.findAll();
        return reporterMapper.toDto(reporters);
    }

    @Override
    public ReporterDto getOne(Long id) {
        Reporter reporter = reporterRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        return reporterMapper.toDto(reporter);
    }

    @Override
    public List<ReporterDto> getMany(List<Long> ids) {
        List<Reporter> reporters = reporterRepository.findAllById(ids);
        return reporterMapper.toDto(reporters);
    }

    @Override
    public ReporterDto create(ReporterDto reporterDto) {
        Reporter reporter = reporterMapper.toEntity(reporterDto);
        Reporter savedReporter = reporterRepository.save(reporter);
        return reporterMapper.toDto(savedReporter);
    }

    @Override
    public ReporterDto patch(Long id, ReporterDto reporterDto) {
        if (!reporterRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }
        Reporter reporter = reporterRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        reporterMapper.partialUpdate(reporterDto, reporter);
        return reporterMapper.toDto(reporterRepository.save(reporter));
    }

    @Override
    public ReporterDto update(Long id, ReporterDto reporterDto) {
        if (!reporterRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }
        // Используем маппер для обновления сущности
        Reporter existingReporter= reporterRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        reporterMapper.partialUpdate(reporterDto, existingReporter);
        return reporterMapper.toDto(reporterRepository.save(existingReporter));
    }

    @Override
    public ReporterDto delete(Long id) {
        Reporter reporter = reporterRepository.findById(id).orElse(null);
        if (reporter != null) {
            reporterRepository.delete(reporter);
            return reporterMapper.toDto(reporter);
        }
        return null;
    }

    @Override
    public void deleteMany(List<Long> ids) {
        reporterRepository.deleteAllById(ids);
    }
}