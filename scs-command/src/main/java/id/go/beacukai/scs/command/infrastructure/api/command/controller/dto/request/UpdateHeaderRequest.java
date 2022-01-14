package id.go.beacukai.scs.command.infrastructure.api.command.controller.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateHeaderRequest extends BaseDocumentRequest {
    private String kodeCaraBayar; // e.g. 1 - BIASA / TUNAI
    private String kodeJenisImpor; // e.g. 1 - UNTUK DIPAKAI
    private String kodeJenisProsedur; // e.g. 1 - BIASA
    private String kodeKantor; // e.g. 160200 - KPPBC TMP A MARUNDA
    private String kodePelTujuan; // e.g. IDAJN - ARJUNA, JAYA
}
