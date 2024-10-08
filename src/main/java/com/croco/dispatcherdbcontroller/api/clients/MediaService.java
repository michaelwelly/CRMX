package com.croco.dispatcherdbcontroller.api.clients;

import com.croco.dispatcherdbcontroller.dto.MediaDto;

import java.io.IOException;
import java.util.List;

public interface MediaService {
    List<MediaDto> getList();
    MediaDto getOne(Long id);
    MediaDto create(MediaDto mediaDto);
    MediaDto update(Long id, MediaDto mediaDto);
    MediaDto delete(Long id);
    MediaDto patch(Long id, MediaDto mediaDto) throws IOException;
}