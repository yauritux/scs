package id.go.beacukai.scs.command.infrastructure.adapter.input;

import id.go.beacukai.scs.command.domain.port.input.DocumentCommandService;
import id.go.beacukai.scs.command.domain.port.input.dto.CreateNewDocument;
import id.go.beacukai.scs.command.domain.port.input.dto.UpdateDocumentHeader;
import id.go.beacukai.scs.command.domain.repository.DocumentLookupEntityRepository;
import id.go.beacukai.scs.command.infrastructure.api.command.controller.dto.request.BaseDocumentRequest;
import id.go.beacukai.scs.command.infrastructure.api.command.controller.dto.request.NewDocumentRequest;
import id.go.beacukai.scs.command.infrastructure.api.command.controller.dto.request.UpdateHeaderRequest;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service("documentCommandService")
public class DocumentCommandServiceImpl implements DocumentCommandService<CreateNewDocument, UpdateDocumentHeader, BaseDocumentRequest> {

    private final CommandGateway commandGateway;
    private final DocumentLookupEntityRepository repository;

    public DocumentCommandServiceImpl(CommandGateway commandGateway, DocumentLookupEntityRepository repository) {
        this.commandGateway = commandGateway;
        this.repository = repository;
    }

    @Override
    public Mono<CreateNewDocument> createNewDocument(BaseDocumentRequest request) {
        var createRequest = (NewDocumentRequest) request;
        var newNoAju = createRequest.getNomorAju();
        if (newNoAju == null || newNoAju.trim().equals("")) {
            newNoAju = newNoAju(createRequest.getKodeDokumen(), createRequest.getIdEntitas());
        }

        var command = CreateNewDocument.builder()
                .roleEntitas(createRequest.getRoleEntitas())
                .nomorAju(newNoAju)
                .kodeDokumen(createRequest.getKodeDokumen())
                .idEntitas(createRequest.getIdEntitas())
                .idPerusahaan(createRequest.getIdPerusahaan())
                .asalData(createRequest.getAsalData())
                .userPortal(createRequest.getUserPortal())
                .seri(createRequest.getSeri())
                .build();
        try {
            commandGateway.send(command);
            return Mono.just(command);
        } catch (Exception e) {
            log.error(String.format("Failed to send register user command.Error= %s", e.getMessage()));
            return Mono.error(e);
        }
    }

    @Override
    public Mono<UpdateDocumentHeader> updateDocumentHeader(BaseDocumentRequest request) {
        var updateRequest = (UpdateHeaderRequest) request;
        var command = UpdateDocumentHeader.builder()
                .nomorAju(updateRequest.getNomorAju())
                .kodeCaraBayar(updateRequest.getKodeCaraBayar())
                .kodeJenisImpor(updateRequest.getKodeJenisImpor())
                .kodeJenisProsedur(updateRequest.getKodeJenisProsedur())
                .kodeKantor(updateRequest.getKodeKantor())
                .kodePelTujuan(updateRequest.getKodePelTujuan())
                .build();
        try {
            commandGateway.send(command);
            return Mono.just(command);
        } catch (Exception e) {
            log.error(String.format("Failed to update document header.Error= %s", e.getMessage()));
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
