package com.croco.dispatcherdbcontroller.api.clients.impl;

import com.croco.dispatcherdbcontroller.dto.IncidentDto;
import com.croco.dispatcherdbcontroller.entity.Filial;
import com.croco.dispatcherdbcontroller.entity.Incident;
import com.croco.dispatcherdbcontroller.entity.IncidentStatus;
import com.croco.dispatcherdbcontroller.entity.Reporter;
import com.croco.dispatcherdbcontroller.entity.User;
import com.croco.dispatcherdbcontroller.mapper.FilialMapper;
import com.croco.dispatcherdbcontroller.mapper.IncidentMapper;
import com.croco.dispatcherdbcontroller.mapper.ReporterMapper;
import com.croco.dispatcherdbcontroller.mapper.UserMapper;
import com.croco.dispatcherdbcontroller.repository.FilialRepository;
import com.croco.dispatcherdbcontroller.repository.IncidentRepository;
import com.croco.dispatcherdbcontroller.api.clients.IncidentService;
import com.croco.dispatcherdbcontroller.repository.ReporterRepository;
import com.croco.dispatcherdbcontroller.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IncidentServiceImpl implements IncidentService {
    private final IncidentRepository incidentRepository;
    private final ReporterRepository reporterRepository;
    private final UserRepository userRepository;
    private final FilialRepository filialRepository;
    private final IncidentMapper incidentMapper;
    private final UserMapper userMapper;
    private final ReporterMapper reporterMapper;
    private final FilialMapper filialMapper;

    private final ObjectMapper objectMapper;

    @Override
    public List<IncidentDto> getList() {
        List<Incident> incidents = incidentRepository.findAll();
        return incidentMapper.toDto(incidents);
    }

    @Override
    public IncidentDto getOne(Long id) {
        Incident incident = incidentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        return incidentMapper.toDto(incident);
    }

    @Override
    public List<IncidentDto> getMany(List<Long> ids) {
        List<Incident> incidents = incidentRepository.findAllById(ids);
        return incidentMapper.toDto(incidents);
    }

    @Override
    public IncidentDto create(IncidentDto incidentDto) {
        // Проверяем наличие пользователя
        User existingUser = userRepository.findByNameStrAndUserNameStrAndUserType(
                incidentDto.getUser().getNameStr(),
                incidentDto.getUser().getUserNameStr(),
                incidentDto.getUser().getUserType()
        ).orElseGet(() -> {
            // Если не найден, создаем нового пользователя
            Long newUserId = userRepository.findMaxId() + 1; // Получаем новый ID
            User newUser = userMapper.toEntity(incidentDto.getUser());
            newUser.setId(newUserId); // Устанавливаем новый ID
            return userRepository.save(newUser);
        });

        // Проверяем наличие репортера
        Reporter existingReporter = reporterRepository.findByNameStrAndPhoneStr(
                incidentDto.getReporter().getNameStr(),
                incidentDto.getReporter().getPhoneStr()
        ).orElseGet(() -> {
            // Если не найден, создаем нового репортера
            Long newReporterId = reporterRepository.findMaxId() + 1; // Получаем новый ID
            Reporter newReporter = reporterMapper.toEntity(incidentDto.getReporter());
            newReporter.setId(newReporterId); // Устанавливаем новый ID
            return reporterRepository.save(newReporter);
        });

        // Проверяем наличие филиала
        Filial existingFilial = filialRepository.findByTitleStrAndDescriptionTxt(
                incidentDto.getFilial().getTitleStr(),
                incidentDto.getFilial().getDescriptionTxt()
        ).orElseGet(() -> {
            // Если не найден, создаем новый филиал
            Long newFilialId = filialRepository.findMaxId() + 1; // Получаем новый ID
            Filial newFilial = filialMapper.toEntity(incidentDto.getFilial());
            newFilial.setId(newFilialId); // Устанавливаем новый ID
            return filialRepository.save(newFilial);
        });

        // Создаем инцидент с использованием найденных или созданных сущностей
        Incident incident = incidentMapper.toEntity(incidentDto);
        incident.setUser(existingUser);
        incident.setReporter(existingReporter);
        incident.setFilial(existingFilial);

        Incident savedIncident = incidentRepository.save(incident);
        return incidentMapper.toDto(savedIncident);
    }

    @Override
    public IncidentDto patch(Long id, IncidentDto incidentDto) {
        Incident incident = incidentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        // Используем маппер для частичного обновления
        incidentMapper.partialUpdate(incidentDto, incident);
        Incident updatedIncident = incidentRepository.save(incident);
        return incidentMapper.toDto(updatedIncident);
    }

    @Override
    public IncidentDto update(Long id, IncidentDto incidentDto) {
        if (!incidentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }

        // Используем маппер для обновления сущности
        Incident existingFilial = incidentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        incidentMapper.partialUpdate(incidentDto, existingFilial);
        return incidentMapper.toDto(incidentRepository.save(existingFilial));
    }

    @Override
    public IncidentDto delete(Long id) {
        Incident incident = incidentRepository.findById(id).orElse(null);
        if (incident != null) {
            incidentRepository.delete(incident);
            return incidentMapper.toDto(incident);
        }
        return null;
    }

    @Override
    public void deleteMany(List<Long> ids) {
        incidentRepository.deleteAllById(ids);
    }

    public List<IncidentDto> getFilteredIncidents(List<IncidentStatus> statuses, String startDate, String endDate, User user) {
        // Парсинг строковых дат в OffsetDateTime
        OffsetDateTime startDateTime = parseDate(startDate);
        OffsetDateTime endDateTime = parseDate(endDate);

        if(startDateTime != null && endDateTime != null ){
            return incidentMapper.toDto(incidentRepository.findByRegistrationDttmBetweenAndUserIdAndStatusIn(startDateTime, endDateTime, user, statuses));
        } else
            return incidentMapper.toDto(incidentRepository.findByUserIdAndStatusIn(user, statuses));
    }

    private OffsetDateTime parseDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        return OffsetDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}