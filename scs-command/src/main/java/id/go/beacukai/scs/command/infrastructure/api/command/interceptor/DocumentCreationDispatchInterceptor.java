package id.go.beacukai.scs.command.infrastructure.api.command.interceptor;

import id.go.beacukai.scs.command.domain.port.input.dto.CreateNewDocument;
import id.go.beacukai.scs.command.domain.port.input.dto.UpdateDocumentHeader;
import id.go.beacukai.scs.command.domain.repository.DocumentLookupEntityRepository;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Intercepts the document registration command message and throws IllegalStateException when the submission no
 * (a.k.a. Nomor Pengajuan / Nomor Aju) already exists.
 *
 * @author Yauri Attamimi
 */
@Component
public class DocumentCreationDispatchInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final DocumentLookupEntityRepository documentLookupEntityRepository;

    public DocumentCreationDispatchInterceptor(DocumentLookupEntityRepository documentLookupEntityRepository) {
        this.documentLookupEntityRepository = documentLookupEntityRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (i, m) -> {
            if(CreateNewDocument.class.equals(m.getPayloadType())) {
                final CreateNewDocument createNewDocumentCommand = (CreateNewDocument) m.getPayload();

                if(documentLookupEntityRepository.existsById(createNewDocumentCommand.getNomorAju())) {
                    throw new CommandExecutionException(String.format("Nomor pengajuan %s telah terdaftar!",
                            createNewDocumentCommand.getNomorAju()), null);
                }
            }
            if(UpdateDocumentHeader.class.equals(m.getPayloadType())) {
                final UpdateDocumentHeader updateDocumentHeaderCommand = (UpdateDocumentHeader) m.getPayload();

                if(!documentLookupEntityRepository.existsById(updateDocumentHeaderCommand.getNomorAju())) {
                    throw new CommandExecutionException(String.format("Nomor pengajuan %s tidak ditemukan!",
                            updateDocumentHeaderCommand.getNomorAju()), null);
                }
            }
            return m;
        };
    }
}
