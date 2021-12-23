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
public class CreateNewDocument {
    @TargetAggregateIdentifier
    private String nomorAju;
    private String kodeDokumen;
    private String roleEntitas;
    private String idEntitas;
    private String idPerusahaan;
    private String asalData;
    private int seri;
    private String userPortal;
}
