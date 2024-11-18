package com.croco.dispatcherdbcontroller.api.clients.impl;

import com.croco.dispatcherdbcontroller.api.clients.UserService;
import com.croco.dispatcherdbcontroller.dto.Address;
import com.croco.dispatcherdbcontroller.dto.IncidentDto;
import com.croco.dispatcherdbcontroller.dto.IncidentFilter;
import com.croco.dispatcherdbcontroller.dto.TaskDto;
import com.croco.dispatcherdbcontroller.entity.FieldServiceTeam;
import com.croco.dispatcherdbcontroller.entity.Filial;
import com.croco.dispatcherdbcontroller.entity.Map;
import com.croco.dispatcherdbcontroller.entity.Incident;
import com.croco.dispatcherdbcontroller.entity.IncidentStatus;
import com.croco.dispatcherdbcontroller.entity.Reporter;
import com.croco.dispatcherdbcontroller.entity.Task;
import com.croco.dispatcherdbcontroller.entity.User;
import com.croco.dispatcherdbcontroller.mapper.FieldServiceTeamMapper;
import com.croco.dispatcherdbcontroller.mapper.FilialMapper;
import com.croco.dispatcherdbcontroller.mapper.IncidentMapper;
import com.croco.dispatcherdbcontroller.mapper.ReporterMapper;
import com.croco.dispatcherdbcontroller.mapper.TaskMapper;
import com.croco.dispatcherdbcontroller.mapper.UserMapper;
import com.croco.dispatcherdbcontroller.repository.*;
import com.croco.dispatcherdbcontroller.api.clients.IncidentService;
import com.croco.dispatcherdbcontroller.utils.DataUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class IncidentServiceImpl implements IncidentService {
    private final IncidentRepository incidentRepository;
    private final ReporterRepository reporterRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FilialRepository filialRepository;
    private final TaskRepository taskRepository;
    private final FieldServiceTeamRepository fieldServiceTeamRepository;
    private final MapRepository mapRepository;
    private final IncidentMapper incidentMapper;
    private final UserMapper userMapper;
    private final ReporterMapper reporterMapper;
    private final FilialMapper filialMapper;
    private final FieldServiceTeamMapper fieldServiceTeamMapper;
    private final TaskMapper taskMapper;
    private final ObjectMapper objectMapper;

    @Override
    public List<IncidentDto> getList() {
        List<Incident> incidents = incidentRepository.findAll();
        return incidentMapper.toDto(incidents);
    }

    @Override
    public IncidentDto getOne(Long id) {
        Incident incident = incidentRepository.findIncident(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        return incidentMapper.toDto(incident);
    }

    @Override
    public List<IncidentDto> getMany(List<Long> ids) {
        List<Incident> incidents = incidentRepository.findAllByIdWithDetails(ids);
        return incidentMapper.toDto(incidents);
    }

    @Override
    public IncidentDto create(IncidentDto incidentDto) {
        // Проверяем наличие пользователя
        User existingUser = userRepository.findByUserNameStr(
                incidentDto.getUser().getUserNameStr()
        ).orElseGet(() -> {
            Long newUserId = userRepository.findMaxId() + 1; // Получаем новый ID
            User newUser = userMapper.toEntity(incidentDto.getUser());
            newUser.setId(newUserId); // Устанавливаем новый ID
            return userRepository.save(newUser);
        });

        // Проверяем наличие репортера
        Reporter existingReporter = null;
        if (incidentDto.getReporter() != null) {
            existingReporter = reporterRepository.findByNameStrAndPhoneStr(
                    incidentDto.getReporter().getNameStr(),
                    incidentDto.getReporter().getPhoneStr()
            ).orElseGet(() -> {
                Long newReporterId = reporterRepository.findMaxId() + 1; // Получаем новый ID
                Reporter newReporter = reporterMapper.toEntity(incidentDto.getReporter());
                newReporter.setId(newReporterId); // Устанавливаем новый ID
                return reporterRepository.save(newReporter);
            });
        }

        // Проверяем наличие филиала
        Filial existingFilial = null;
        if (incidentDto.getFilial() != null) {
            existingFilial = filialRepository.findByTitleStrAndDescriptionTxt(
                    incidentDto.getFilial().getTitleStr(),
                    incidentDto.getFilial().getDescriptionTxt()
            ).orElseGet(() -> {
                Long newFilialId = filialRepository.findMaxId() + 1; // Получаем новый ID
                Filial newFilial = filialMapper.toEntity(incidentDto.getFilial());
                newFilial.setId(newFilialId); // Устанавливаем новый ID
                return filialRepository.save(newFilial);
            });
        }

        // Проверяем наличие FieldServiceTeam
        FieldServiceTeam existingFieldServiceTeam = null;
        if (incidentDto.getTeam() != null) {
            existingFieldServiceTeam = fieldServiceTeamRepository.findByNameStr(
                    incidentDto.getTeam().getNameStr()
            ).orElseGet(() -> {
                Long newFieldServiceTeamId = fieldServiceTeamRepository.findMaxId() + 1; // Получаем новый ID
                FieldServiceTeam newFieldServiceTeam = fieldServiceTeamMapper.toEntity(incidentDto.getTeam());
                newFieldServiceTeam.setId(newFieldServiceTeamId); // Устанавливаем новый ID
                return fieldServiceTeamRepository.save(newFieldServiceTeam);
            });
        }

        // Создаем инцидент с использованием найденных или созданных сущностей
        Incident incident = incidentMapper.toEntity(incidentDto);
        incident.setUser(existingUser);
        incident.setReporter(existingReporter);
        incident.setFilial(existingFilial);
        incident.setTeam(existingFieldServiceTeam);

        // Проверяем наличие адреса в базе
        if (incidentDto.getAddressJson() != null) {
            Address address = DataUtils.parseAddress(incidentDto.getAddressJson());
            Optional<Map> existingMap = mapRepository.findByTitleStrAndDescriptionTxt(address.getCity(), address.getStreet());
            existingMap.ifPresent(map -> incident.setAddressJson(map.getAttributesJson()));
        }

        // Устанавливаем текущее время, если execDttm равно null
        if (incident.getExecDttm() == null) {
            ZoneOffset zoneOffset = ZoneOffset.ofHours(3);
            incident.setExecDttm(OffsetDateTime.now(zoneOffset));
        }

        // Сохраняем инцидент
        Incident savedIncident = incidentRepository.save(incident);

        // Проверяем наличие задач
        List<Task> existingTasks = new ArrayList<>();

        if (incidentDto.getTasks() != null && !incidentDto.getTasks().isEmpty()) {
            for (TaskDto taskDto : incidentDto.getTasks()) {
                // Пытаемся найти существующую задачу
                List<Task> foundTasks = taskRepository.findByWorkerIdAndTitleStrAndIsCompleteFlag(
                        taskDto.getWorkerId(),
                        taskDto.getTitleStr(),
                        taskDto.getIsCompleteFlag()
                );

                Task existingTask = foundTasks.isEmpty() ? null : foundTasks.get(0); // Если задача не найдена, existingTask будет равен null

                // Если задача не найдена, создаем новую задачу
                if (existingTask == null) {
                    Long newTaskId = taskRepository.findMaxId() + 1; // Получаем новый ID
                    Task newTask = taskMapper.toEntity(taskDto);
                    newTask.setId(newTaskId); // Устанавливаем новый ID
                    newTask.setIncident(savedIncident.getId()); // Устанавливаем новый IDинцидента для связи с тасками
                    existingTask = taskRepository.save(newTask); // Сохраняем новую задачу
                }
                existingTask.setIncident(savedIncident.getId());
                // Добавляем существующую или новую задачу в список
                existingTasks.add(existingTask);
                taskRepository.save(existingTask); // Сохраняем обновленную задачу
            }
        }

        // Обработка задач
        if (incidentDto.getTasks() != null) {
            Set<Task> tasks = new LinkedHashSet<>();
            for (TaskDto taskDto : incidentDto.getTasks()) {
                Task task = taskMapper.toEntity(taskDto);
                task.setIncident(savedIncident.getId()); // Устанавливаем связь с инцидентом
                tasks.add(task);
            }
            savedIncident.setTasks(tasks);
        }

        return incidentMapper.toDto(savedIncident);
    }

    @Override
    public IncidentDto patch(Long id, IncidentDto incidentDto) {
        Incident incident = incidentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        // Используем маппер для частичного обновления
        incidentMapper.partialUpdate(incidentDto, incident);
        if (incident.getExecDttm() == null) {
            ZoneOffset zoneOffset = ZoneOffset.ofHours(3);
            incident.setExecDttm(OffsetDateTime.now(zoneOffset));
        }
        Incident updatedIncident = incidentRepository.save(incident);
        return incidentMapper.toDto(updatedIncident);
    }

    @Transactional
    public IncidentDto update(Long id, IncidentDto incidentDto) {
        // Получаем существующий инцидент
        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        // Используем маппер для частичного обновления
        incidentMapper.partialUpdate(incidentDto, incident);

        // Устанавливаем текущее время, если execDttm равно null
        if (incident.getExecDttm() == null) {
            ZoneOffset zoneOffset = ZoneOffset.ofHours(3);
            incident.setExecDttm(OffsetDateTime.now(zoneOffset));
        }

        // Обновляем связанные сущности, если они присутствуют
        if (incidentDto.getUser() != null) {
            User user = userRepository.findById(incidentDto.getUser().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            incident.setUser(user);
        }

        if (incidentDto.getTeam() != null) {
            FieldServiceTeam team = fieldServiceTeamRepository.findById(incidentDto.getTeam().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
            incident.setTeam(team);
        }

        // Сохраняем обновленный инцидент
        Incident updatedIncident = incidentRepository.save(incident);
        return incidentMapper.toDto(updatedIncident);
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

    @Override
    public List<IncidentDto> getFilteredIncidents(List<IncidentStatus> statuses, String startDate, String
            endDate, User user) {
        // Парсинг строковых дат в OffsetDateTime
        OffsetDateTime startDateTime = parseDate(startDate);
        OffsetDateTime endDateTime = parseDate(endDate);

        if (startDateTime != null && endDateTime != null) {
            return incidentMapper.toDto(incidentRepository.findByRegistrationDttmBetweenAndUserIdAndStatusIn(startDateTime, endDateTime, user, statuses));
        } else
            return incidentMapper.toDto(incidentRepository.findByUserIdAndStatusIn(user, statuses));
    }

    @Override
    public List<IncidentDto> getFilteredIncidentsFromUrl(String url) {
        User user = null;

        IncidentFilter filter = null;
        filter = parseIncidentFilter(url);
        if (filter.getUser() != null) {
            try {
                user = userMapper.toEntity(userService.getOne(filter.getUser().getId()));
            } catch (Exception e) {
                return null;
            }
        }
        try {
            filter = objectMapper.readValue(url, IncidentFilter.class);
        } catch (JsonProcessingException e) {
            log.error("Error when deserialize IncidentFilter");
            throw new RuntimeException(e);
        }
        List<IncidentDto> incidentDtos = getFilteredIncidents(
                filter.getStatuses(),
                filter.getStartDate(),
                filter.getEndDate(),
                user,
                filter.getAttributes()
        );

        if (incidentDtos.isEmpty()) {
            return null;
        }
        return incidentDtos;
    }


    private IncidentFilter parseIncidentFilter(String url) {
        IncidentFilter incidentFilter = null;
        try {
            incidentFilter = IncidentFilter.deserialize(url);
        } catch (Exception e) {
            log.error("Error when parsing IncidentFilter url");
            return null;
        }
        return incidentFilter;
    }

    @Override
    public List<IncidentDto> getFilteredIncidents(List<IncidentStatus> statuses, String startDate, String endDate, User user, java.util.Map<String, String> attributes) {
        // Парсинг строковых дат в OffsetDateTime
        OffsetDateTime startDateTime = parseDate(startDate);
        OffsetDateTime endDateTime = parseDate(endDate);

        if (startDateTime != null && endDateTime != null) {
            return incidentMapper.toDto(incidentRepository.findByRegistrationDttmBetweenAndUserIdAndStatusIn(startDateTime, endDateTime, user, statuses));
        } else if (attributes != null && !attributes.isEmpty()) {
            return incidentMapper.toDto(incidentRepository.findByAttributes(
                    new ArrayList<>(attributes.keySet()),
                    new ArrayList<>(attributes.values()),
                    attributes.size()));
        } else
            return incidentMapper.toDto(incidentRepository.findByUserIdAndStatusIn(user, statuses));
    }

    private OffsetDateTime parseDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        return OffsetDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public IncidentDto getIncidentDto(Long id) {
        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Incident not found"));

        Reporter reporter = incident.getReporter();
        if (reporter != null) {
            Hibernate.initialize(reporter);
        }

        User user = incident.getUser();
        if (user != null) {
            Hibernate.initialize(user);
        }

        Filial filial = incident.getFilial();
        if (filial != null) {
            Hibernate.initialize(filial);
        }

        Set<Task> tasks = incident.getTasks();
        if (tasks != null) {
            Hibernate.initialize(tasks);
        }

        FieldServiceTeam team = incident.getTeam();
        if (team != null) {
            Hibernate.initialize(team);
        }

        return new IncidentDto(
                incident.getId(),
                reporterMapper.toDto(reporter),
                userMapper.toDto(user),
                incident.getIncidentType(),
                incident.getRegistrationDttm(),
                incident.getExecDttm(),
                incident.getSynchronizationDttm(),
                incident.getLocationType(),
                incident.getIncidentStatus(),
                incident.getDescriptionText(),
                filialMapper.toDto(filial),
                incident.getAddressJson(),
                incident.getAttributesJson(),
                incident.getAddressStr(),
                incident.getChangedDttm(),
                fieldServiceTeamMapper.toDto(team),
                taskMapper.toDto(tasks)
        );
    }
}