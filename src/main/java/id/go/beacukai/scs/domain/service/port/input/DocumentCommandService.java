package id.go.beacukai.scs.domain.service.port.input;

import reactor.core.publisher.Mono;

public interface DocumentCommandService<T, R> {

    Mono<T> createNewDocument(R request);
}
