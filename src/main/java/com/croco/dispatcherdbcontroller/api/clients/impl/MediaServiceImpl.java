package com.croco.dispatcherdbcontroller.api.clients.impl;

import com.croco.dispatcherdbcontroller.dto.MediaDto;
import com.croco.dispatcherdbcontroller.entity.Media;
import com.croco.dispatcherdbcontroller.mapper.MediaMapper;
import com.croco.dispatcherdbcontroller.repository.MediaRepository;
import com.croco.dispatcherdbcontroller.api.clients.MediaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;

    public MediaServiceImpl(MediaRepository mediaRepository, MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
    }

    @Override
    public List<MediaDto> getList() {
        List<Media> mediaList = mediaRepository.findAll();
        return mediaMapper.toDto(mediaList);
    }

    @Override
    public MediaDto getOne(Long id) {
        Media media = mediaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found"));
        return mediaMapper.toDto(media);
    }

    @Override
    public MediaDto create(MediaDto mediaDto) {
        Media media = mediaMapper.toEntity(mediaDto);
        Media savedMedia = mediaRepository.save(media);
        return mediaMapper.toDto(savedMedia);
    }

    @Override
    public MediaDto update(Long id, MediaDto mediaDto) {
        Media existingMedia = mediaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found"));
        mediaMapper.partialUpdate(mediaDto, existingMedia); // Метод для частичного обновления
        return mediaMapper.toDto(mediaRepository.save(existingMedia));
    }


    @Override
    public MediaDto patch(Long id, MediaDto mediaDto) {
        if (!mediaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }
        Media media = mediaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        mediaMapper.partialUpdate(mediaDto, media);
        return mediaMapper.toDto(mediaRepository.save(media));
    }

    @Override
    public MediaDto delete(Long id) {
        Media media = mediaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found"));
        mediaRepository.delete(media);
        return mediaMapper.toDto(media);
    }
}