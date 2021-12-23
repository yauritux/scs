package id.go.beacukai.scs.command.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class DocumentLookupEntity {

    @Id
    private String nomorAju;
    private String kodeDokumen;
    private String roleEntitas;
    private String idEntitas;
    private String idPerusahaan;
}
