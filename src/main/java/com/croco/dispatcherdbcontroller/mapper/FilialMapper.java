package com.croco.dispatcherdbcontroller.mapper;

import com.croco.dispatcherdbcontroller.dto.FilialDto;
import com.croco.dispatcherdbcontroller.entity.Filial;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilialMapper {
    Filial toEntity(FilialDto filialDto);

    FilialDto toDto(Filial filial);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Filial partialUpdate(FilialDto filialDto, @MappingTarget Filial filial);

    List<FilialDto> toDto(List<Filial> filial);
}