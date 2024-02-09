package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.CareProvider;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.IssuingUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateDataEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityCertificateRepository;

@Profile(TESTABILITY_PROFILE)
@Repository
@Slf4j
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

    final var certificateEntity = buildCertificateEntity(certificate);
    log.debug(certificateEntityRepository.save(certificateEntity).toString());

    return certificate;
  }

  private CertificateEntity buildCertificateEntity(Certificate certificate) {
    final var certificateFromDB = certificateEntityRepository.findByCertificateId(
        "b7c07158-bb0f-4785-93fe-228b626d981a"
    );

    final var careUnit = findUnit(getCareUnit(certificate).hsaId());
    final var issuedOn = findUnit(getIssuingUnit(certificate).hsaId());
    final var careProvider = findUnit(getCareProvider(certificate).hsaId());

    final var issuedBy = findStaff(getIssuer(certificate).hsaId().id());
    final var createdBy = findStaff(getIssuer(certificate).hsaId().id());

    final var patient = findPatient(certificate);
    final var model = findModel(certificate);

    final var certificateEntity =
        certificateFromDB == null
            ? CertificateEntityMapper.toEntity(certificate)
            : CertificateEntityMapper.updateEntity(certificateFromDB);

    certificateEntity.setIssuedBy(
        getEntity(issuedBy, getIssuer(certificate), StaffEntityMapper::toEntity)
    );
    certificateEntity.setCreatedBy(
        getEntity(createdBy, getIssuer(certificate), StaffEntityMapper::toEntity)
    );
    certificateEntity.setPatient(patient);
    certificateEntity.setCareProvider(careProvider);
    certificateEntity.setCareUnit(careUnit);
    certificateEntity.setIssuedOnUnit(issuedOn);
    certificateEntity.setCertificateModel(model);
    certificateEntity.setData(
        CertificateDataEntity.builder()
            .data(new byte[0])
            .build()
    );

    return certificateEntity;
  }

  private <T, R> T getEntity(T entity, R object, Function<R, T> mapper) {
    return entity != null ? entity : mapper.apply(object);
  }

  private CertificateModelEntity findModel(Certificate certificate) {
    return certificateModelEntityRepository.findByTypeAndVersion(
        getCertificateModelId(certificate).type().type(),
        getCertificateModelId(certificate).version().version()
    );
  }

  private PatientEntity findPatient(Certificate certificate) {
    return patientEntityRepository.findById(
        getPatient(certificate).id().id()
    );
  }

  private static CertificateModelId getCertificateModelId(Certificate certificate) {
    return certificate.certificateModel().id();
  }

  private static Patient getPatient(Certificate certificate) {
    return certificate.certificateMetaData().patient();
  }

  private static Staff getIssuer(Certificate certificate) {
    return certificate.certificateMetaData().issuer();
  }

  private static CareProvider getCareProvider(Certificate certificate) {
    return certificate.certificateMetaData().careProvider();
  }

  private static IssuingUnit getIssuingUnit(Certificate certificate) {
    return certificate.certificateMetaData().issuingUnit();
  }

  private static CareUnit getCareUnit(Certificate certificate) {
    return certificate.certificateMetaData().careUnit();
  }

  private StaffEntity findStaff(String hsaId) {
    return staffEntityRepository.findByHsaId(
        hsaId
    );
  }

  private UnitEntity findUnit(HsaId certificate) {
    return unitEntityRepository.findByHsaId(
        certificate.id()
    );
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
