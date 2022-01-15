package id.go.beacukai.scs.query.infrastructure.adapter.input.eventhandler;

import id.go.beacukai.scs.domain.event.DocumentCreatedEvent;
import id.go.beacukai.scs.domain.event.ScsBaseEvent;
import id.go.beacukai.scs.query.ScsQueryApplication;
import id.go.beacukai.scs.query.domain.entity.PabeanDocument;
import id.go.beacukai.scs.query.domain.repository.PabeanDocumentRepository;
import id.go.beacukai.scs.query.domain.service.port.input.DocumentBaseEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class DocumentCreatedEventHandler implements DocumentBaseEventHandler {

    private final PabeanDocumentRepository repository;

    public DocumentCreatedEventHandler(PabeanDocumentRepository repository) {
        this.repository = repository;
        ScsQueryApplication.EVENT_HANDLERS.put("DocumentCreatedEventHandler", this);
    }

    @Override
    public void handle(ScsBaseEvent event) {
        var docCreatedEvent = (DocumentCreatedEvent) event;
        Optional<PabeanDocument> currentDocument = this.repository.findById(docCreatedEvent.getData().getNomorAju());
        if (!currentDocument.isEmpty()) {
            // data already exist, skip (i.e. document update should belong to another kind of document event
            log.info("Document with 'nomor aju' {} already exist. Skip the event.",
                    docCreatedEvent.getData().getNomorAju());
            return;
        }

        var doc = PabeanDocument.builder()
                .nomorAju(docCreatedEvent.getData().getNomorAju())
                .kodeDokumen(docCreatedEvent.getData().getKodeDokumen())
                .roleEntitas(docCreatedEvent.getData().getRoleEntitas())
                .idEntitas(docCreatedEvent.getData().getIdEntitas())
                .idPerusahaan(docCreatedEvent.getData().getIdPerusahaan())
                .asalData(docCreatedEvent.getData().getAsalData())
                .userPortal(docCreatedEvent.getData().getUserPortal())
                .createdAt(LocalDateTime.now())
                .build();
        this.repository.save(doc);
    }
}
