package com.croco.dispatcherdbcontroller.api.clients;

import com.croco.dispatcherdbcontroller.dto.FieldServiceTeamDto;

import java.io.IOException;
import java.util.List;

public interface FieldServiceTeamService {
    List<FieldServiceTeamDto> getList();
    FieldServiceTeamDto getOne(Long id);
    FieldServiceTeamDto create(FieldServiceTeamDto fieldServiceTeamDto);
    FieldServiceTeamDto update(Long id, FieldServiceTeamDto fieldServiceTeamDto);
    FieldServiceTeamDto patch(Long id, FieldServiceTeamDto fieldServiceTeamDto) throws IOException;
    FieldServiceTeamDto delete(Long id);
}