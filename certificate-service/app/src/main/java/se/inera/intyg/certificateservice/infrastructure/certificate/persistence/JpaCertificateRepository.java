package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;

@Repository
@RequiredArgsConstructor
public class JpaCertificateRepository implements CertificateRepository {

  private final CertificateEntityRepository certificateEntityRepository;

  @Override
  public Certificate create(CertificateModel certificateModel) {
    if (certificateModel == null) {
      throw new IllegalArgumentException("Unable to create, certificateModel was null.");
    }

    return Certificate.builder()
        .id(new CertificateId(UUID.randomUUID().toString()))
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(certificateModel)
        .build();
  }

  @Override
  public Certificate save(Certificate certificate) {
    if (certificate == null) {
      throw new IllegalArgumentException(
          "Unable to save, certificate was null"
      );
    }

    certificateEntityRepository.save(
        CertificateEntity.builder()
            .certificateId(certificate.id().id())
            .modified(LocalDateTime.now())
            .version(1)
            .created(certificate.created())
            .patient(
                PatientEntity.builder()
                    .id(certificate.certificateMetaData().patient().id().id())
                    .type(
                        PatientIdTypeEntity.builder()
                            .type(PersonIdType.PERSONAL_IDENTITY_NUMBER.name())
                            .build()
                    )
                    .build()
            )
            .careUnit(
                UnitEntity.builder()
                    .name(certificate.certificateMetaData().careUnit().name().name())
                    .hsaId(certificate.certificateMetaData().careUnit().hsaId().id())
                    .type(
                        UnitTypeEntity.builder()
                            .type("CARE_UNIT")
                            .build()
                    )
                    .build()
            )
            .careProvider(
                UnitEntity.builder()
                    .name(certificate.certificateMetaData().careProvider().name().name())
                    .hsaId(certificate.certificateMetaData().careProvider().hsaId().id())
                    .type(
                        UnitTypeEntity.builder()
                            .type("CARE_PROVIDER")
                            .build()
                    )
                    .build()
            )
            .issuedOnUnit(
                UnitEntity.builder()
                    .name(certificate.certificateMetaData().issuingUnit().name().name())
                    .hsaId(certificate.certificateMetaData().issuingUnit().hsaId().id())
                    .type(
                        UnitTypeEntity.builder()
                            .type("SUB_UNIT")
                            .build()
                    )
                    .build()
            )
            .issuedBy(
                StaffEntity.builder()
                    .name(certificate.certificateMetaData().issuer().name().fullName())
                    .hsaId(certificate.certificateMetaData().issuer().hsaId().id())
                    .build()
            )
            .certificateModel(
                CertificateModelEntity.builder()
                    .type(certificate.certificateModel().id().type().type())
                    .name(certificate.certificateModel().name())
                    .version(certificate.certificateModel().id().version().version())
                    .build()
            )
            .data(
                CertificateDataEntity.builder()
                    .data(new byte[0])
                    .build()
            )
            .build()
    );

    return certificate;
  }

  @Override
  public Certificate getById(CertificateId certificateId) {
    return null;
  }

  @Override
  public boolean exists(CertificateId certificateId) {
    return false;
  }
}
