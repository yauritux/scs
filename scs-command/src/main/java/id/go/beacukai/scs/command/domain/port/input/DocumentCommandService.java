package id.go.beacukai.scs.command.domain.port.input;

import reactor.core.publisher.Mono;

public interface DocumentCommandService<T, U, R> {

    Mono<T> createNewDocument(R request);
    Mono<U> updateDocumentHeader(R request);
//    Mono<T> addEntitas(R request);
}
