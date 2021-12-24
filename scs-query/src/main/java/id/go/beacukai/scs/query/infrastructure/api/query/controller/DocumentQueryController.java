package id.go.beacukai.scs.query.infrastructure.api.query.controller;

import id.go.beacukai.scs.query.domain.entity.PabeanDocument;
import id.go.beacukai.scs.query.domain.repository.PabeanDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentQueryController {

    @Autowired
    private PabeanDocumentRepository repository;

    @GetMapping
    public Flux<List<PabeanDocument>> findAll() {
        return Flux.just(repository.findAll());
    }
}
