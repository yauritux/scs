package id.go.beacukai.scs.command.infrastructure.adapter.output;

import id.go.beacukai.scs.command.domain.port.output.DocumentStreamingService;
import id.go.beacukai.scs.domain.event.DocumentCreatedEvent;
import id.go.beacukai.scs.domain.event.DocumentHeaderUpdatedEvent;
import id.go.beacukai.scs.domain.event.ScsBaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Configuration
public class DocumentStreamingServiceImpl implements DocumentStreamingService<ScsBaseEvent> {

    private final KafkaProducer<String, ScsBaseEvent> kafkaProducer;

    public DocumentStreamingServiceImpl(KafkaProducer<String, ScsBaseEvent> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    // TODO:: refactor to state pattern
    @Override
    public void publish(String topic, ScsBaseEvent baseEvent) {
        if (baseEvent instanceof DocumentCreatedEvent) {
            DocumentCreatedEvent event = (DocumentCreatedEvent) baseEvent;
            publishDocumentCreatedEvent(topic, event);
        }
        if (baseEvent instanceof DocumentHeaderUpdatedEvent) {
            DocumentHeaderUpdatedEvent event = (DocumentHeaderUpdatedEvent) baseEvent;
            publishDocumentHeaderUpdatedEvent(topic, event);
        }
    }

    private void publishDocumentCreatedEvent(String topic, DocumentCreatedEvent event) {
        kafkaProducer.send(new ProducerRecord<>(topic, event.getData().getNomorAju(), event), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null) {
                    log.error("Something bad happened", e);
                }
            }
        });
    }

    private void publishDocumentHeaderUpdatedEvent(String topic, DocumentHeaderUpdatedEvent event) {
        kafkaProducer.send(new ProducerRecord<>(topic, event.getData().getNomorAju(), event), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null) {
                    log.error("Something bad happened", e);
                }
            }
        });
    }
}
