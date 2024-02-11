package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;

public class CertificateModelEntityMapper {

  public static CertificateModelEntity toEntity(CertificateModel model) {
    return CertificateModelEntity.builder()
        .type(model.id().type().type())
        .name(model.name())
        .version(model.id().version().version())
        .build();
  }
}
