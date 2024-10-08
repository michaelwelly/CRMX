package com.croco.dispatcherdbcontroller.mapper;

import com.croco.dispatcherdbcontroller.dto.MediaDto;
import com.croco.dispatcherdbcontroller.entity.Media;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MediaMapper {
    MediaDto toDto(Media media);
    Media toEntity(MediaDto mediaDto);
    List<MediaDto> toDto(List<Media> mediaList);
    List<Media> toEntity(List<MediaDto> mediaDtos);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Media partialUpdate(MediaDto mediaDto, @MappingTarget Media media);

}