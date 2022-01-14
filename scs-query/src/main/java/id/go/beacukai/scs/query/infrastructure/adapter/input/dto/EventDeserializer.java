package id.go.beacukai.scs.query.infrastructure.adapter.input.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.go.beacukai.scs.domain.event.DocumentCreatedEvent;
import id.go.beacukai.scs.domain.event.DocumentHeaderUpdatedEvent;
import id.go.beacukai.scs.domain.event.ScsBaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
//public class EventDeserializer implements Deserializer<DocumentCreatedEvent> {
public class EventDeserializer implements Deserializer<ScsBaseEvent> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
//    public DocumentCreatedEvent deserialize(String s, byte[] bytes) {
    public ScsBaseEvent deserialize(String s, byte[] bytes) {
        String data = new String(bytes, StandardCharsets.UTF_8);
        log.info("data = {}", data);
        if (data.indexOf("DocumentCreatedEvent") > 0) {
            DocumentCreatedEvent event = null;
            try {
                event = mapper.readValue(bytes, DocumentCreatedEvent.class);
                log.info("receivedEvent={}", event);
                return event;
            } catch (IOException e) {
                log.error("Failed to parsing event.Error={}", e);
            }
        }
        if (data.indexOf("DocumentHeaderUpdatedEvent") > 0) {
            DocumentHeaderUpdatedEvent event = null;
            try {
                event = mapper.readValue(bytes, DocumentHeaderUpdatedEvent.class);
                log.info("receivedEvent={}", event);
                return event;
            } catch (IOException e) {
                log.error("Failed to parsing event.Error={}", e);
            }
        }
//        return event;
        return null;
    }

    @Override
//    public DocumentCreatedEvent deserialize(String topic, Headers headers, byte[] data) {
    public ScsBaseEvent deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
