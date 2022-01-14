package id.go.beacukai.scs.query.infrastructure.adapter.input.eventhandler;

import com.google.gson.Gson;
import id.go.beacukai.scs.domain.event.DocumentCreatedEvent;
import id.go.beacukai.scs.query.domain.entity.PabeanDocument;
import id.go.beacukai.scs.query.domain.repository.PabeanDocumentRepository;
import id.go.beacukai.scs.query.domain.service.port.input.DocumentBaseEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

@Slf4j
public class DocumentCreatedEventHandler implements DocumentBaseEventHandler {

    private final DocumentCreatedEvent event;
    private final ApplicationContext applicationContext;

    public DocumentCreatedEventHandler(String payload, ApplicationContext applicationContext) {
        this.event = new Gson().fromJson(payload, DocumentCreatedEvent.class);
        this.applicationContext = applicationContext;
    }

    @Override
    public void handle() {
        PabeanDocumentRepository repository = (PabeanDocumentRepository) applicationContext.getBean("pabeanDocumentRepository");
        var doc = PabeanDocument.builder()
                .nomorAju(this.event.getData().getNomorAju())
                .kodeDokumen(this.event.getData().getKodeDokumen())
                .idEntitas(this.event.getData().getIdEntitas())
                .roleEntitas(this.event.getData().getRoleEntitas())
                .idPerusahaan(this.event.getData().getIdPerusahaan())
                .userPortal(this.event.getData().getUserPortal())
                .asalData(this.event.getData().getAsalData())
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(doc);
    }
}
