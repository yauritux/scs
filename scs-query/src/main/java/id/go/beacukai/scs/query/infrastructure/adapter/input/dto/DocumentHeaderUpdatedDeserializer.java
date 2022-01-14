package id.go.beacukai.scs.query.infrastructure.adapter.input.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.go.beacukai.scs.domain.event.DocumentHeaderUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class DocumentHeaderUpdatedDeserializer implements Deserializer<DocumentHeaderUpdatedEvent> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public DocumentHeaderUpdatedEvent deserialize(String s, byte[] bytes) {
        DocumentHeaderUpdatedEvent event = null;
        try {
            event = mapper.readValue(bytes, DocumentHeaderUpdatedEvent.class);
            log.info("receivedEvent={}", event);
        } catch (IOException e) {
            log.error("Failed to parsing event.Error={}", e);
        }
        return event;
    }

    @Override
    public DocumentHeaderUpdatedEvent deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
