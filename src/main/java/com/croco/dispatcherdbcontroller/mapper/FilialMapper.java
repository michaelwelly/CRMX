package com.croco.dispatcherdbcontroller.mapper;

import com.croco.dispatcherdbcontroller.dto.FilialDto;
import com.croco.dispatcherdbcontroller.dto.xml.FilialXmlDto;
import com.croco.dispatcherdbcontroller.entity.Filial;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilialMapper {
    @Mapping(target = "id", ignore = true)
    Filial toEntity(FilialDto filialDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    Filial partialUpdate(FilialDto filialDto, @MappingTarget Filial filial);

    List<FilialDto> toDto(List<Filial> filial);

    FilialMapper INSTANCE = Mappers.getMapper(FilialMapper.class);

    FilialXmlDto toXml(FilialDto authRequestDTO);

    FilialDto toDto(FilialXmlDto authRequestXmlDTO);

    default FilialDto toDto(Filial filial) {
        if (filial == null) {
            return null;
        }

        Map<String, Object> locations = null;
        Map<String, Object> attributesJson = null;
        Long id = null;
        String titleStr = null;
        String descriptionTxt = null;

        Map<String, Object> map = filial.getLocations();
        if (map != null) {
            locations = new LinkedHashMap<String, Object>(map);
        }
        Map<String, Object> map1 = filial.getAttributesJson();
        if (map1 != null) {
            attributesJson = new LinkedHashMap<String, Object>(map1);
        }
        id = filial.getId();
        titleStr = filial.getTitleStr();
        descriptionTxt = filial.getDescriptionTxt();

        FilialDto filialDto = new FilialDto(id, locations, titleStr, descriptionTxt, attributesJson);

        return filialDto;
    }

    default FilialXmlDto toXml(byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(FilialXmlDto.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            String logdata = new String(data);
            FilialXmlDto filialXmlDto = (FilialXmlDto) unmarshaller.unmarshal(new ByteArrayInputStream(data));
            return filialXmlDto;
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to deserialize XML", e);
        }
    }

    default byte[] toByte(FilialXmlDto xml) {
        if (xml == null) {
            return null;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(FilialXmlDto.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            marshaller.marshal(xml, outputStream);
            return outputStream.toByteArray();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to serialize object to XML", e);
        }
    }
}