package com.croco.dispatcherdbcontroller.api.clients;

import com.croco.dispatcherdbcontroller.dto.ReporterDto;

import java.io.IOException;
import java.util.List;

public interface ReporterService {
    List<ReporterDto> getList();
    ReporterDto getOne(Long id);
    List<ReporterDto> getMany(List<Long> ids);
    ReporterDto create(ReporterDto reporter);
    ReporterDto delete(Long id);
    ReporterDto patch(Long id, ReporterDto reporterDto) throws IOException;
    ReporterDto update(Long id, ReporterDto reporterDto);
    void deleteMany(List<Long> ids);
}