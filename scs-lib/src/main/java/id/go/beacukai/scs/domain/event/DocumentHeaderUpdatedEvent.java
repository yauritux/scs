package id.go.beacukai.scs.domain.event;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentHeaderUpdatedEvent extends ScsBaseEvent {

    private Payload data;

    public DocumentHeaderUpdatedEvent(String id) {
        super(id);
    }

    @Getter
    @NoArgsConstructor
    public final class Payload {
        private String nomorAju;
        @Setter
        private String kodeCaraBayar;
        @Setter
        private String kodeJenisImpor;
        @Setter
        private String kodeJenisProsedur;
        @Setter
        private String kodeKantor;
        @Setter
        private String kodePelTujuan;

        public Payload(final String nomorAju) {
            this.nomorAju = nomorAju;
        }
    }
}
