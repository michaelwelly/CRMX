package com.croco.dispatcherdbcontroller.api.clients;

import com.croco.dispatcherdbcontroller.dto.IncidentDto;
import com.croco.dispatcherdbcontroller.entity.IncidentStatus;
import com.croco.dispatcherdbcontroller.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IncidentService {
    List<IncidentDto> getList();
    IncidentDto getOne(Long id);
    List<IncidentDto> getMany(List<Long> ids);
    IncidentDto create(IncidentDto incident);
    IncidentDto patch(Long id, IncidentDto incidentDto) throws IOException;
    IncidentDto update(Long id, IncidentDto incidentDto);
    IncidentDto delete(Long id);
    void deleteMany(List<Long> ids);
    List<IncidentDto> getFilteredIncidents(List<IncidentStatus> statuses, String startDate, String endDate, User user);
    List<IncidentDto> getFilteredIncidentsFromUrl(String url);

    List<IncidentDto> getFilteredIncidents(List<IncidentStatus> statuses, String startDate, String endDate, User user, Map<String, String> attributes);
}