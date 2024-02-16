package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;

@Profile("!" + TESTABILITY_PROFILE)
@Primary
@Repository
public class JpaCertificateRepository implements CertificateRepository {

  private final CertificateEntityRepository certificateEntityRepository;
  private final PatientRepository patientRepository;
  private final UnitRepository unitRepository;
  private final CertificateEntityMapper certificateEntityMapper;

  public JpaCertificateRepository(
      CertificateEntityRepository certificateEntityRepository,
      PatientRepository patientRepository,
      UnitRepository unitRepository,
      CertificateEntityMapper certificateEntityMapper) {
    this.certificateEntityRepository = certificateEntityRepository;
    this.patientRepository = patientRepository;
    this.unitRepository = unitRepository;
    this.certificateEntityMapper = certificateEntityMapper;
  }

  @Override
  public Certificate create(CertificateModel certificateModel) {
    if (certificateModel == null) {
      throw new IllegalArgumentException("Unable to create, certificateModel was null");
    }

    return Certificate.builder()
        .id(new CertificateId(UUID.randomUUID().toString()))
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(certificateModel)
        .revision(new Revision(0))
        .build();
  }

  @Override
  public Certificate save(Certificate certificate) {
    if (certificate == null) {
      throw new IllegalArgumentException(
          "Unable to save, certificate was null"
      );
    }

    if (Status.DELETED_DRAFT.equals(certificate.status())) {
      certificateEntityRepository.findByCertificateId(certificate.id().id())
          .ifPresent(certificateEntityRepository::delete);
      return certificate;
    }

    final var savedEntity = certificateEntityRepository.save(
        certificateEntityMapper.toEntity(certificate)
    );

    return certificateEntityMapper.toDomain(savedEntity);
  }

  @Override
  public Certificate getById(CertificateId certificateId) {
    if (certificateId == null) {
      throw new IllegalArgumentException("Cannot get certificate if certificateId is null");
    }

    final var certificateEntity = certificateEntityRepository.findByCertificateId(
            certificateId.id())
        .orElseThrow(() ->
            new IllegalArgumentException(
                "CertificateId '%s' not present in repository".formatted(certificateId)
            )
        );

    return certificateEntityMapper.toDomain(certificateEntity);
  }

  @Override
  public boolean exists(CertificateId certificateId) {
    if (certificateId == null) {
      throw new IllegalArgumentException(
          "Cannot check if certificate exists since certificateId is null");
    }

    return certificateEntityRepository.findByCertificateId(certificateId.id()).isPresent();
  }

  @Override
  public List<Certificate> findByPatientByCareUnit(Patient patient, CareUnit careUnit) {
    return certificateEntityRepository.findCertificateEntitiesByPatientAndCareUnit(
            patientRepository.patient(patient),
            unitRepository.careUnit(careUnit)
        )
        .stream()
        .map(certificateEntityMapper::toDomain)
        .toList();
  }

  @Override
  public List<Certificate> findByPatientBySubUnit(Patient patient, SubUnit subUnit) {
    return certificateEntityRepository.findCertificateEntitiesByPatientAndIssuedOnUnit(
            patientRepository.patient(patient),
            unitRepository.subUnit(subUnit)
        )
        .stream()
        .map(certificateEntityMapper::toDomain)
        .toList();
  }
}
