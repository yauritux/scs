package id.go.beacukai.scs.command.domain.port.input.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateDocumentHeader {
    @TargetAggregateIdentifier
    private String nomorAju;
    private String kodeCaraBayar;
    private String kodeJenisImpor;
    private String kodeJenisProsedur;
    private String kodeKantor;
    private String kodePelTujuan;
}
