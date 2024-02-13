package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;

public class CertificateEntityMapper {

  private CertificateEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static CertificateEntity toEntity(Certificate certificate) {
    return CertificateEntity.builder()
        .certificateId(certificate.id().id())
        .created(certificate.created())
        .version(certificate.version() + 1)
        .modified(LocalDateTime.now())
        .build();
  }

  public static CertificateEntity updateEntity(CertificateEntity entity) {
    entity.setVersion(entity.getVersion() + 1);
    entity.setModified(LocalDateTime.now());
    return entity;
  }

  public static Certificate toDomain(CertificateEntity certificateEntity, CertificateModel model) {
    return Certificate.builder()
        .id(new CertificateId(certificateEntity.getCertificateId()))
        .created(certificateEntity.getCreated())
        .version(certificateEntity.getVersion())
        .certificateModel(model)
        .certificateMetaData(
            CertificateMetaData.builder()
                .careProvider(
                    UnitEntityMapper.toCareProviderDomain(certificateEntity.getCareProvider())
                )
                .careUnit(
                    UnitEntityMapper.toCareUnitDomain(certificateEntity.getCareUnit())
                )
                .issuingUnit(certificateEntity.getIssuedOnUnit().getType().getKey()
                    == UnitType.SUB_UNIT.getKey()
                    ? UnitEntityMapper.toSubUnitDomain(certificateEntity.getIssuedOnUnit())
                    : UnitEntityMapper.toCareUnitDomain(certificateEntity.getIssuedOnUnit())
                )
                .issuer(
                    StaffEntityMapper.toDomain(
                        certificateEntity.getIssuedBy()
                    )
                )
                .patient(
                    PatientEntityMapper.toDomain(certificateEntity.getPatient())
                )
                .build()
        )
        .build();
  }

}
