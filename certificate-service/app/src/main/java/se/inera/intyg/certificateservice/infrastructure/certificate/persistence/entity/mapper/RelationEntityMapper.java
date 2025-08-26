package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateRelationEntity;

public class RelationEntityMapper {

  private RelationEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static Relation parentToDomain(CertificateRelationEntity entity, Certificate certificate) {
    return Relation.builder()
        .certificate(certificate)
        .type(
            RelationType.valueOf(
                entity.getCertificateRelationType().getType()
            )
        )
        .created(entity.getCreated())
        .build();
  }

  public static Relation childToDomain(CertificateRelationEntity entity, Certificate certificate) {
    return Relation.builder()
        .certificate(certificate)
        .type(
            RelationType.valueOf(
                entity.getCertificateRelationType().getType()
            )
        )
        .created(entity.getCreated())
        .build();
  }
}