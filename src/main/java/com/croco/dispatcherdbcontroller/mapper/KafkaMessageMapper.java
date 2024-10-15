package com.croco.dispatcherdbcontroller.mapper;

import com.croco.dispatcherdbcontroller.kafka.model.KafkaMessage;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface KafkaMessageMapper {

    KafkaMessageMapper INSTANCE = Mappers.getMapper(KafkaMessageMapper.class);

    default byte[] toByte(KafkaMessage message) {
        if (message == null) {
            return null;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(KafkaMessage.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            marshaller.marshal(message, outputStream);
            return outputStream.toByteArray();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to serialize object to XML", e);
        }
    }

    default KafkaMessage toMessage(byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(KafkaMessage.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (KafkaMessage) unmarshaller.unmarshal(new ByteArrayInputStream(data));
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to deserialize XML", e);
        }
    }
}