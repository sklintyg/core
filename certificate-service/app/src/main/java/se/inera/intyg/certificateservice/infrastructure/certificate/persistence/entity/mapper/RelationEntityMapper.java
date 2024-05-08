package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateRelationEntity;

public class RelationEntityMapper {

  private RelationEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static Relation parentToDomain(CertificateRelationEntity certificateRelationEntity) {
    return Relation.builder()
        .certificateId(
            new CertificateId(certificateRelationEntity.getParentCertificate().getCertificateId()))
        .type(
            RelationType.valueOf(
                certificateRelationEntity.getCertificateRelationType().getType()
            )
        )
        .status(
            Status.valueOf(
                certificateRelationEntity.getParentCertificate().getStatus().getStatus()
            )
        )
        .created(certificateRelationEntity.getCreated())
        .build();
  }

  public static Relation childToDomain(CertificateRelationEntity entity) {
    return Relation.builder()
        .certificateId(new CertificateId(entity.getChildCertificate().getCertificateId()))
        .type(
            RelationType.valueOf(
                entity.getCertificateRelationType().getType()
            )
        )
        .status(
            Status.valueOf(
                entity.getChildCertificate().getStatus().getStatus()
            )
        )
        .created(entity.getCreated())
        .build();
  }
}

