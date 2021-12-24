package id.go.beacukai.scs.query.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "documents")
public class PabeanDocument {

    @Id
    private String nomorAju;
    private String kodeDokumen;
    private String roleEntitas;
    private String idEntitas;
    private String idPerusahaan;
    private String asalData;
    private String userPortal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
