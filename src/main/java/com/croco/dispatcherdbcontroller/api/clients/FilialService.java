package com.croco.dispatcherdbcontroller.api.clients;

import com.croco.dispatcherdbcontroller.dto.FilialDto;

import java.io.IOException;
import java.util.List;

public interface FilialService {
    List<FilialDto> getList();
    FilialDto getOne(Long id);
    List<FilialDto> getMany(List<Long> ids);
    FilialDto create(FilialDto filial);
    FilialDto delete(Long id);
    void deleteMany(List<Long> ids);
    FilialDto update(Long id, FilialDto filialDto);
    FilialDto patch(Long id, FilialDto filialDto) throws IOException;
}
