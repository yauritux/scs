package id.go.beacukai.scs.command.infrastructure.api.command.controller.dto.request;

import id.go.beacukai.scs.command.domain.vo.Entitas;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
public class AddEntitasRequest extends BaseDocumentRequest {
//    private List<Entitas> entities;
}

// e.g. request :
/*
[
  {
    "idEntitas": null,
    "idHeader": "6819b878-e80f-4eb5-9f33-677cf186122a",
    "alamatEntitas": "JL. TURANGGA BRT DLM IV",
    "kodeEntitas": "1",
    "kodeJenisApi": "02",
    "kodeJenisIdentitas": "5",
    "kodeNegara": null,
    "kodeStatus": "LAINNYA",
    "namaEntitas": "PT DEMO PORTAL",
    "nibEntitas": "777999",
    "niperEntitas": null,
    "nomorIdentitas": "123456789012345",
    "nomorIjinEntitas": null,
    "tanggalIjinEntitas": null,
    "namaNegara": null,
    "namaJenisApi": null,
    "namaJenisIdentitas": "NPWP 15 DIGIT",
    "nomorApi": null,
    "seriEntitas": 1,
    "seriEntitasOld": null
  },
  {
    "kodeJenisIdentitas": "2",
    "nomorIdentitas": "B34111",
    "namaEntitas": "M. YAURI M. ATTAMIMI",
    "alamatEntitas": "CITRAGRAND CBD CIBUBUR CLUSTER PREMIUM FRASER PARK BLOK F05/52",
    "idHeader": "6819b878-e80f-4eb5-9f33-677cf186122a",
    "kodeEntitas": "7",
    "seriEntitas": "7"
  },
  {
    "namaEntitas": "M. IQBAL NASUTION",
    "alamatEntitas": "APT. KALIBATA CITY ",
    "kodeNegara": "ID",
    "idHeader": "6819b878-e80f-4eb5-9f33-677cf186122a",
    "kodeEntitas": "9",
    "seriEntitas": "9"
  },
  {
    "kodeJenisIdentitas": "2",
    "nomorIdentitas": "B34111",
    "namaEntitas": "M. YAURI M. ATTAMIMI",
    "alamatEntitas": "CITRAGRAND CBD CIBUBUR CLUSTER PREMIUM FRASER PARK BLOK F05/52",
    "idHeader": "6819b878-e80f-4eb5-9f33-677cf186122a",
    "kodeEntitas": "11",
    "seriEntitas": "11"
  },
  {
    "namaEntitas": "M. IQBAL NASUTION",
    "alamatEntitas": "APT. KALIBATA CITY ",
    "kodeNegara": "ID",
    "idHeader": "6819b878-e80f-4eb5-9f33-677cf186122a",
    "kodeEntitas": "10",
    "seriEntitas": "10"
  }
]
 */
