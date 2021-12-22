package id.go.beacukai.scs.infrastructure.api.command.controller.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NewDocumentRequest {
    private String nomorAju;
    private String roleEntitas;
    private String kodeDokumen;
    private String asalData;
    private String idEntitas;
    private String idPerusahaan;
    private int seri;
    private String userPortal;
}