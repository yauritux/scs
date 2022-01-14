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
public class EntitasLookupEntity {
    @Id
    private String idEntitas;
    private String nomorAju; // TODO:: considering to replace with IDHeader
    private String alamatEntitas; // e.g. JL. TURANGGA BRT DLM V
    private String kodeEntitas; // e.g. 1 (IMPORTIR) / 7 (PEMILIK BARANG) / 9 (PENGIRIM) / 10 (PENJUAL) / 11 (NPWP PEMUSATAN)
    private String kodeJenisApi; // e.g. 02
    private String kodeJenisIdentitas; // e.g. 5 / 2
    private String kodeNegara; // e.g. ID - Indonesia
    private String kodeStatus; // e.g. LAINNYA
    private String namaEntitas; // e.g. PT DEMO PORTAL
    private String namaJenisApi; // e.g. NULL
    private String namaJenisIdentitas; // e.g. NPWP 15 DIGIT
    private String namaNegara; // e.g. INDONESIA
    private String nibEntitas; // e.g. 777999
    private String niperEntitas; // e.g. NULL
    private String nomorApi; // e.g. NULL
    private String nomorIdentitas; // e.g. 123456789012345
    private String nomorIjinEntitas; // e.g. NULL
    private int seriEntitas; // e.g. 1
    private String seriEntitasOld; // e.g. NULL
    private String tanggalIjinEntitas; // e.g. NULL
}
