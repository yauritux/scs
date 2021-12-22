package id.go.beacukai.scs.sharedkernel.event;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentCreatedEvent extends BaseEvent {

    private Payload data;

    public DocumentCreatedEvent(String id) {
        super(id);
    }

    @Getter
    public final class Payload {
        private String nomorAju;
        private String kodeDokumen;
        @Setter
        private String roleEntitas;
        @Setter
        private String idEntitas;
        @Setter
        private String idPerusahaan;
        @Setter
        private String asalData;
        @Setter
        private String userPortal;

        public Payload(final String nomorAju, final String kodeDokumen) {
            this.nomorAju = nomorAju;
            this.kodeDokumen = kodeDokumen;
        }
    }
}
