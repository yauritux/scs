package id.go.beacukai.scs.infrastructure.adapter.input;

import id.go.beacukai.scs.domain.repository.DocumentLookupEntityRepository;
import id.go.beacukai.scs.domain.service.port.input.DocumentCommandService;
import id.go.beacukai.scs.domain.service.port.input.dto.CreateNewDocument;
import id.go.beacukai.scs.infrastructure.api.command.controller.dto.request.NewDocumentRequest;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service("documentCommandService")
public class DocumentCommandServiceImpl implements DocumentCommandService<CreateNewDocument, NewDocumentRequest> {

    private final CommandGateway commandGateway;
    private final DocumentLookupEntityRepository repository;

    public DocumentCommandServiceImpl(CommandGateway commandGateway, DocumentLookupEntityRepository repository) {
        this.commandGateway = commandGateway;
        this.repository = repository;
    }

    @Override
    public Mono<CreateNewDocument> createNewDocument(NewDocumentRequest request) {
        var newNoAju = request.getNomorAju();
        if (newNoAju == null || newNoAju.trim().equals("")) {
            newNoAju = newNoAju(request.getKodeDokumen(), request.getIdEntitas());
        }

        var command = CreateNewDocument.builder()
                .roleEntitas(request.getRoleEntitas())
                .nomorAju(newNoAju)
                .kodeDokumen(request.getKodeDokumen())
                .idEntitas(request.getIdEntitas())
                .idPerusahaan(request.getIdPerusahaan())
                .asalData(request.getAsalData())
                .userPortal(request.getUserPortal())
                .seri(request.getSeri())
                .build();
        try {
            commandGateway.send(command);
            return Mono.just(command);
        } catch (Exception e) {
            log.error(String.format("Failed to send register user command.Error= %s", e.getMessage()));
            return Mono.error(e);
        }
    }

    public final synchronized String newNoAju(String kodeDokumen, String idEntitas) {
        var str = new StringBuilder();
        str.append("0".repeat(6 - kodeDokumen.length()));
        str.append(kodeDokumen);
        str.append(idEntitas.substring(0, 6));
        var df = DateTimeFormatter.ofPattern("yyyyMMdd");
        str.append(LocalDate.now().format(df));
        long nextNo = repository.count() + 1;
        str.append("0".repeat(6 - Long.toString(nextNo).length()));
        str.append(nextNo);
        return str.toString();
    }
}
