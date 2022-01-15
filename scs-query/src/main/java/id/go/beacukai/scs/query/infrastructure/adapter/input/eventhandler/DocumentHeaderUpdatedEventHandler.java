package id.go.beacukai.scs.query.infrastructure.adapter.input.eventhandler;

import id.go.beacukai.scs.domain.event.DocumentHeaderUpdatedEvent;
import id.go.beacukai.scs.domain.event.ScsBaseEvent;
import id.go.beacukai.scs.query.ScsQueryApplication;
import id.go.beacukai.scs.query.domain.entity.PabeanDocument;
import id.go.beacukai.scs.query.domain.repository.PabeanDocumentRepository;
import id.go.beacukai.scs.query.domain.service.port.input.DocumentBaseEventHandler;
import id.go.beacukai.scs.query.domain.vo.DocumentHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DocumentHeaderUpdatedEventHandler implements DocumentBaseEventHandler {

    private final PabeanDocumentRepository repository;

    public DocumentHeaderUpdatedEventHandler(PabeanDocumentRepository repository) {
        this.repository = repository;
        ScsQueryApplication.EVENT_HANDLERS.put("DocumentHeaderUpdatedEventHandler", this);
    }

    @Override
    public void handle(ScsBaseEvent event) {
        var docHeaderUpdatedEvent = (DocumentHeaderUpdatedEvent) event;
        Optional<PabeanDocument> currentDocument = this.repository.findById(docHeaderUpdatedEvent.getData().getNomorAju());
        if (currentDocument.isEmpty()) {
            // data doesn't exist, skip
            log.info("Document with 'nomor aju' {} does not exist. Skip the event.", docHeaderUpdatedEvent.getData().getNomorAju());
            return;
        }
        PabeanDocument document = currentDocument.get();
        var docHeader = DocumentHeader.builder()
                .kodeCaraBayar(docHeaderUpdatedEvent.getData().getKodeCaraBayar())
                .kodeJenisImpor(docHeaderUpdatedEvent.getData().getKodeJenisImpor())
                .kodeKantor(docHeaderUpdatedEvent.getData().getKodeKantor())
                .kodePelTujuan(docHeaderUpdatedEvent.getData().getKodePelTujuan())
                .kodeJenisProsedur(docHeaderUpdatedEvent.getData().getKodeJenisProsedur())
                .build();
        document.setHeader(docHeader);
        this.repository.save(document);
    }
}
