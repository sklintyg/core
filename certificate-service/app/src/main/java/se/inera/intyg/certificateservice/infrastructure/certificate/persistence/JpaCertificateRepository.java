package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
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
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateDataEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityCertificateRepository;

@Profile(TESTABILITY_PROFILE)
@Repository
@RequiredArgsConstructor
public class JpaCertificateRepository implements TestabilityCertificateRepository {

  private final CertificateEntityRepository certificateEntityRepository;
  private final CertificateModelEntityRepository certificateModelEntityRepository;
  private final CertificateDataEntityRepository certificateDataEntityRepository;
  private final StaffEntityRepository staffEntityRepository;
  private final UnitEntityRepository unitEntityRepository;
  private final PatientEntityRepository patientEntityRepository;

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

    final var patientEntity = patientEntityRepository.save(
        PatientEntity.builder()
            .id(certificate.certificateMetaData().patient().id().id())
            .type(
                PatientIdTypeEntity.builder()
                    .key(1)
                    .type(PersonIdType.PERSONAL_IDENTITY_NUMBER.name())
                    .build()
            )
            .build()
    );

    final var careUnitEntity = unitEntityRepository.save(
        UnitEntity.builder()
            .name(certificate.certificateMetaData().careUnit().name().name())
            .hsaId(certificate.certificateMetaData().careUnit().hsaId().id())
            .type(
                UnitTypeEntity.builder()
                    .key(2)
                    .type("CARE_UNIT")
                    .build()
            )
            .build()
    );

    final var careProviderEntity = unitEntityRepository.save(
        UnitEntity.builder()
            .name(certificate.certificateMetaData().careProvider().name().name())
            .hsaId(certificate.certificateMetaData().careProvider().hsaId().id())
            .type(
                UnitTypeEntity.builder()
                    .key(3)
                    .type("CARE_PROVIDER")
                    .build()
            )
            .build()
    );

    final var subUnitEntity = unitEntityRepository.save(
        UnitEntity.builder()
            .name(certificate.certificateMetaData().issuingUnit().name().name())
            .hsaId(certificate.certificateMetaData().issuingUnit().hsaId().id())
            .type(
                UnitTypeEntity.builder()
                    .key(1)
                    .type("SUB_UNIT")
                    .build()
            )
            .build()
    );

    final var staffEntity = staffEntityRepository.save(
        StaffEntity.builder()
            .name(certificate.certificateMetaData().issuer().name().fullName())
            .hsaId(certificate.certificateMetaData().issuer().hsaId().id())
            .build()
    );

    final var certificateModelEntity = certificateModelEntityRepository.save(
        CertificateModelEntity.builder()
            .type(certificate.certificateModel().id().type().type())
            .name(certificate.certificateModel().name())
            .version(certificate.certificateModel().id().version().version())
            .build()
    );

    final var save = certificateEntityRepository.save(
        CertificateEntity.builder()
            .certificateId(certificate.id().id())
            .modified(LocalDateTime.now())
            .version(1)
            .created(certificate.created())
            .createdBy(staffEntity.getKey())
            .patientKey(patientEntity.getKey())
            .issuedBy(staffEntity.getKey())
            .issuedOnUnit(subUnitEntity.getKey())
            .careUnit(careUnitEntity.getKey())
            .careProvider(careProviderEntity.getKey())
            .certificateModelKey(certificateModelEntity.getKey())
            .data(
                CertificateDataEntity.builder()
                    .data(new byte[0])
                    .build()
            )
            .build()
    );

    System.out.println(save);

    // certificateDataEntityRepository.save(
    //     CertificateDataEntity.builder()
    //         .key(save.getKey())
    //         .data(new byte[0])
    //         .build()
    // );
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

  @Override
  public Certificate insert(Certificate certificate) {
    return null;
  }

  @Override
  public void remove(List<CertificateId> certificateIds) {

  }
}
