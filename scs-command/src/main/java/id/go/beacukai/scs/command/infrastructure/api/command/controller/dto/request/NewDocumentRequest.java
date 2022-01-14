package id.go.beacukai.scs.command.infrastructure.api.command.controller.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NewDocumentRequest extends BaseDocumentRequest {
    private String roleEntitas;
    private String kodeDokumen;
    private String asalData;
    private String idEntitas;
    private String idPerusahaan;
    private int seri;
    private String userPortal;
}
