package id.go.beacukai.scs.command;

import id.go.beacukai.scs.command.infrastructure.adapter.output.dto.EventSerializer;
import id.go.beacukai.scs.domain.event.ScsBaseEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
public class ScsCommandApplication {

    @Value("${kafka.bootstrap.host:localhost}")
    private String bootstrapHost;

    @Value("${kafka.bootstrap.port:9092}")
    private String bootstrapPort;

    public static void main(String[] args) {
        SpringApplication.run(ScsCommandApplication.class, args);
    }

    @Bean
    public KafkaProducer<String, ScsBaseEvent> kafkaProducer() {
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
        KafkaProducer<String, ScsBaseEvent> producer = new KafkaProducer<>(properties);

        return producer;
    }
}
