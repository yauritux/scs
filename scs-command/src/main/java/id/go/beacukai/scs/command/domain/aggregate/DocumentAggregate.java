package id.go.beacukai.scs.command.domain.aggregate;

import id.go.beacukai.scs.command.domain.port.input.dto.CreateNewDocument;
import id.go.beacukai.scs.command.domain.port.input.dto.UpdateDocumentHeader;
import id.go.beacukai.scs.domain.event.DocumentCreatedEvent;
import id.go.beacukai.scs.domain.event.DocumentHeaderUpdatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@NoArgsConstructor
public class DocumentAggregate {
    @AggregateIdentifier
    private String nomorAju;
    private String kodeDokumen;
    private String roleEntitas;
    private String idEntitas;
    private String asalData;
    private String idPerusahaan;

    @CommandHandler
    public DocumentAggregate(CreateNewDocument command) {
        var event = new DocumentCreatedEvent(UUID.randomUUID().toString());
        DocumentCreatedEvent.Payload data = event.new Payload(command.getNomorAju(), command.getKodeDokumen());
        data.setRoleEntitas(command.getRoleEntitas());
        data.setIdEntitas(command.getIdEntitas());
        data.setIdPerusahaan(command.getIdPerusahaan());
        data.setAsalData(command.getAsalData());
        data.setUserPortal(command.getUserPortal());
        event.setData(data);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(UpdateDocumentHeader command) {
        var event = new DocumentHeaderUpdatedEvent(UUID.randomUUID().toString());
        DocumentHeaderUpdatedEvent.Payload data = event.new Payload(command.getNomorAju());
        data.setKodeCaraBayar(command.getKodeCaraBayar());
        data.setKodeJenisImpor(command.getKodeJenisImpor());
        data.setKodeJenisProsedur(command.getKodeJenisProsedur());
        data.setKodeKantor(command.getKodeKantor());
        data.setKodePelTujuan(command.getKodePelTujuan());
        event.setData(data);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(DocumentCreatedEvent event) {
        this.nomorAju = event.getData().getNomorAju();
        this.kodeDokumen = event.getData().getKodeDokumen();
        this.roleEntitas = event.getData().getRoleEntitas();
        this.idEntitas = event.getData().getIdEntitas();
        this.idPerusahaan = event.getData().getIdPerusahaan();
        this.asalData = event.getData().getAsalData();
    }

    @EventSourcingHandler
    public void on(DocumentHeaderUpdatedEvent event) {
        this.nomorAju = event.getData().getNomorAju();
    }
}
