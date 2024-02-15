package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
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
        .revision(certificate.revision().value())
        .modified(LocalDateTime.now())
        .build();
  }

  public static CertificateEntity updateEntity(CertificateEntity entity, Certificate certificate) {
    entity.setRevision(certificate.revision().value());
    entity.setModified(LocalDateTime.now());
    return entity;
  }

  public static Certificate toDomain(CertificateEntity certificateEntity, CertificateModel model) {
    return Certificate.builder()
        .id(new CertificateId(certificateEntity.getCertificateId()))
        .created(certificateEntity.getCreated())
        .revision(new Revision(certificateEntity.getRevision()))
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
        .elementData(
            CertificateDataEntityMapper.toDomain(certificateEntity.getData())
        )
        .build();
  }

}
