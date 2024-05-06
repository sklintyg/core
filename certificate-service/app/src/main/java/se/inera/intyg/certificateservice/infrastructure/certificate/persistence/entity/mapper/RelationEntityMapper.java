package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

public class RelationEntityMapper {

  private RelationEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static Relation toDomain(String certificateId, RelationType relationType,
      Status status, LocalDateTime created) {
    return Relation.builder()
        .certificateId(new CertificateId(certificateId))
        .type(relationType)
        .status(status)
        .created(created)
        .build();
  }
}
