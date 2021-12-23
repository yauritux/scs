package id.go.beacukai.scs.command.infrastructure.adapter.output;

import id.go.beacukai.scs.command.domain.port.output.DocumentStreamingService;
import id.go.beacukai.scs.domain.event.DocumentCreatedEvent;
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

    @Override
    public void publish(String topic, ScsBaseEvent baseEvent) {
        DocumentCreatedEvent event = (DocumentCreatedEvent) baseEvent;
        kafkaProducer.send(new ProducerRecord<>(topic, event.getData().getIdEntitas(), event), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null) {
                    log.error("Something bad happened", e);
                }
            }
        });
    }
}
