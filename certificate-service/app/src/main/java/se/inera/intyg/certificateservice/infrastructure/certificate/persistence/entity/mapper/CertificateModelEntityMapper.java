package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;

public class CertificateModelEntityMapper {

  private CertificateModelEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static CertificateModelEntity toEntity(CertificateModel model) {
    return CertificateModelEntity.builder()
        .type(model.id().type().type())
        .name(model.name())
        .version(model.id().version().version())
        .build();
  }

  public static CertificateModel toDomain(CertificateModelEntity model) {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .version(new CertificateVersion(model.getVersion()))
                .type(new CertificateType(model.getType()))
                .build()
        )
        .name(model.getName())
        .build();
  }
}
