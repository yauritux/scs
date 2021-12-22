package id.go.beacukai.scs.infrastructure.api.command.handler;

import id.go.beacukai.scs.domain.entity.DocumentLookupEntity;
import id.go.beacukai.scs.domain.repository.DocumentLookupEntityRepository;
import id.go.beacukai.scs.domain.service.port.output.DocumentStreamingService;
import id.go.beacukai.scs.sharedkernel.event.DocumentCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("documentEntity")
public class DocumentEventHandler {

    private final DocumentLookupEntityRepository documentLookupEntityRepository;
    private final DocumentStreamingService streamingService;

    public DocumentEventHandler(DocumentLookupEntityRepository documentLookupEntityRepository, DocumentStreamingService streamingService) {
        this.documentLookupEntityRepository = documentLookupEntityRepository;
        this.streamingService = streamingService;
    }

    @EventHandler
    public void on(DocumentCreatedEvent event) {
        documentLookupEntityRepository.save(
                DocumentLookupEntity.builder()
                        .nomorAju(event.getData().getNomorAju())
                        .kodeDokumen(event.getData().getKodeDokumen())
                        .roleEntitas(event.getData().getRoleEntitas())
                        .idEntitas(event.getData().getIdEntitas())
                        .idPerusahaan(event.getData().getIdPerusahaan())
                        .build());
        streamingService.publish(event);
    }
}
