package id.go.beacukai.scs.command.domain.repository;

import id.go.beacukai.scs.command.domain.entity.DocumentLookupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentLookupEntityRepository extends JpaRepository<DocumentLookupEntity, String> {
}
