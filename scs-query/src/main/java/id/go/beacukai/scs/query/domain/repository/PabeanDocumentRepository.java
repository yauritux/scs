package id.go.beacukai.scs.query.domain.repository;

import id.go.beacukai.scs.query.domain.entity.PabeanDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PabeanDocumentRepository extends MongoRepository<PabeanDocument, String> {
}
