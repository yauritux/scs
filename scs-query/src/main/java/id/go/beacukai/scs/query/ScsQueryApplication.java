package id.go.beacukai.scs.query;

import id.go.beacukai.scs.domain.event.ScsBaseEvent;
import id.go.beacukai.scs.query.domain.repository.PabeanDocumentRepository;
import id.go.beacukai.scs.query.domain.service.port.input.DocumentBaseEventHandler;
import id.go.beacukai.scs.query.infrastructure.adapter.input.dto.EventDeserializer;
import id.go.beacukai.scs.sharedkernel.constant.EventTopicConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = PabeanDocumentRepository.class)
@EnableKafka
public class ScsQueryApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ScsQueryApplication.class);

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:scs-query-service}")
    private String groupId;

    public static final Map<String, DocumentBaseEventHandler> EVENT_HANDLERS = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(ScsQueryApplication.class, args);
    }

    @Bean
    public ConsumerFactory<String, ScsBaseEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                  new EventDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ScsBaseEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ScsBaseEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public MessageListener messageListener() {
        return new MessageListener();
    }

    // TODO:: move to an infrastructure layer
    public static class MessageListener {

        private CountDownLatch eventLatch = new CountDownLatch(1);

        @KafkaListener(topics = EventTopicConfig.DOCUMENT_EVENTS, containerFactory = "kafkaListenerContainerFactory")
        public void eventListener(ScsBaseEvent event) {
            ScsQueryApplication.EVENT_HANDLERS.get(event.getEventHandler()).handle(event);
            this.eventLatch.countDown();
        }
    }
}
