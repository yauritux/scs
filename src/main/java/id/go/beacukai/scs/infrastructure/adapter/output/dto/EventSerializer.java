package id.go.beacukai.scs.infrastructure.adapter.output.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.go.beacukai.scs.sharedkernel.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class EventSerializer implements Serializer<BaseEvent> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map configs, boolean isKey) {}

    @Override
    public byte[] serialize(String s, BaseEvent data) {
        try {
            if (data == null) {
                log.info("Null received at serializing");
                return null;
            }
            return mapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing event to byte[]");
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, BaseEvent data) {
        return serialize(null, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
