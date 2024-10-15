package com.croco.dispatcherdbcontroller.kafka.deserializer;

import com.croco.dispatcherdbcontroller.dto.FilialDto;
import com.croco.dispatcherdbcontroller.mapper.FilialMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@RequiredArgsConstructor
public class KafkaFilialRequestDeserializer implements Deserializer<FilialDto> {

    private final FilialMapper filialMapper;

    public KafkaFilialRequestDeserializer() {
        // Инициализация маппера
        this.filialMapper = FilialMapper.INSTANCE;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public FilialDto deserialize(String topic, byte[] data) {
        return filialMapper.toDto(filialMapper.toXml(data));

    }

    @Override
    public void close() {
    }
}