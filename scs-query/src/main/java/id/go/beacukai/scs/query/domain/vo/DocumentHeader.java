package id.go.beacukai.scs.query.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentHeader {
    private String kodeCaraBayar;
    private String kodeJenisImpor;
    private String kodeJenisProsedur;
    private String kodeKantor;
    private String kodePelTujuan;
}
