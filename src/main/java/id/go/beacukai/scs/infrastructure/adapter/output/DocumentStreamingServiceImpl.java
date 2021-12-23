package id.go.beacukai.scs.infrastructure.adapter.output;

import id.go.beacukai.scs.ScsApplication;
import id.go.beacukai.scs.domain.service.port.output.DocumentStreamingService;
import id.go.beacukai.scs.infrastructure.adapter.output.dto.EventSerializer;
import id.go.beacukai.scs.sharedkernel.event.BaseEvent;
import id.go.beacukai.scs.sharedkernel.event.DocumentCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service
@Configuration
public class DocumentStreamingServiceImpl implements DocumentStreamingService<BaseEvent> {

    @Value("${kafka.bootstrap.host:localhost}")
    private String bootstrapHost;

    @Value("${kafka.bootstrap.port:9092}")
    private String bootstrapPort;

    private static KafkaProducer<String, BaseEvent> producer;

    @Override
    public void publish(String topic, BaseEvent baseEvent) {
        DocumentCreatedEvent event = (DocumentCreatedEvent) baseEvent;
        if (producer == null) {
            producer = createProducer();
        }
        producer.send(new ProducerRecord<>(topic, event.getData().getIdEntitas(), event), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null) {
                    log.error("Something bad happened", e);
                }
            }
        });
    }

    private KafkaProducer<String, BaseEvent> createProducer() {
        // setup producer configs
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%s:%s", bootstrapHost, bootstrapPort));
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EventSerializer.class.getName());

        // setup safe producer
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        // high throughput producer (at the expense of a bit latency and CPU usage)
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20"); // 20ms
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024)); // 32 KB batch size

        // create a producer
        KafkaProducer<String, BaseEvent> producer = new KafkaProducer<>(properties);

        return producer;
    }
}
