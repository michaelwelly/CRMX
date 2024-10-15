package com.croco.dispatcherdbcontroller.kafka.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectListSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value instanceof List) {
            gen.writeObject(value);
        } else {
            List<Object> list = new ArrayList<>();
            list.add(value);
            gen.writeObject(list);
        }
    }
}