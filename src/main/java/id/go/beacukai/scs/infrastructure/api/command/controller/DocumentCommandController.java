package id.go.beacukai.scs.infrastructure.api.command.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.go.beacukai.scs.domain.service.port.input.DocumentCommandService;
import id.go.beacukai.scs.infrastructure.api.command.controller.dto.request.NewDocumentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentCommandController {

    private final DocumentCommandService documentCommandService;

    public DocumentCommandController(DocumentCommandService documentCommandService) {
        this.documentCommandService = documentCommandService;
    }

    @PostMapping
    public Mono<?> createNewDocument(@RequestBody NewDocumentRequest request) {
        return documentCommandService.createNewDocument(request)
                .map(m -> ResponseEntity.status(HttpStatus.CREATED).body(m))
                .onErrorResume(JsonProcessingException.class,
                        (e) -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(Mono.just(e.toString()), String.class));
    }
}
