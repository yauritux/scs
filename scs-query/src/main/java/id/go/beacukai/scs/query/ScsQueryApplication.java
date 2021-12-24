package id.go.beacukai.scs.query;

import id.go.beacukai.scs.domain.event.DocumentCreatedEvent;
import id.go.beacukai.scs.query.domain.entity.PabeanDocument;
import id.go.beacukai.scs.query.domain.repository.PabeanDocumentRepository;
import id.go.beacukai.scs.sharedkernel.constant.EventTopicConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

    public static void main(String[] args) {
        SpringApplication.run(ScsQueryApplication.class, args);
    }

    @Bean
    public ConsumerFactory<String, DocumentCreatedEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(DocumentCreatedEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DocumentCreatedEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DocumentCreatedEvent> factory =
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

        @Autowired
        private PabeanDocumentRepository documentRepository;

        @KafkaListener(topics = EventTopicConfig.DOCUMENT_EVENTS, containerFactory = "kafkaListenerContainerFactory")
        public void eventListener(DocumentCreatedEvent event) {
            // TODO:: extract interface to adhere SoC
            LOG.info("Received event = {}", event);
            Optional<PabeanDocument> currentDocument = documentRepository.findById(event.getData().getNomorAju());
            // TODO:: consider to apply state pattern
            PabeanDocument newDocument = null;
            if (currentDocument.isEmpty()) {
                newDocument = PabeanDocument.builder()
                        .nomorAju(event.getData().getNomorAju())
                        .kodeDokumen(event.getData().getKodeDokumen())
                        .roleEntitas(event.getData().getRoleEntitas())
                        .idEntitas(event.getData().getIdEntitas())
                        .idPerusahaan(event.getData().getIdPerusahaan())
                        .asalData(event.getData().getAsalData())
                        .userPortal(event.getData().getUserPortal())
                        .createdAt(LocalDateTime.now()).build();
                documentRepository.save(newDocument);
            } else {
                // data already exist, skip (i.e. document update should belong to another kind of document event
                LOG.info("Document with 'nomor aju' {} already exist. Skip the event.", event.getData().getNomorAju());
            }
            this.eventLatch.countDown();
        }
    }
}
