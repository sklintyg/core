package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateModelEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.Unit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateDataEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.utils.CertificateToObjectUtility;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.utils.RepositoryUtility;
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

    certificate.id(new CertificateId("7d9d24f3-6eba-4b5d-9eb0-81e96b475748"));

    final var certificateEntity = buildCertificateEntity(certificate);
    log.debug(certificateEntityRepository.save(certificateEntity).toString());

    return certificate;
  }

  private CertificateEntity buildCertificateEntity(Certificate certificate) {
    final var certificateFromDB = certificateEntityRepository.findByCertificateId(
        certificate.id().id());

    final var careUnit = findUnit(CertificateToObjectUtility.getCareUnit(certificate));
    final var issuedOn = findUnit(CertificateToObjectUtility.getIssuingUnit(certificate));
    final var careProvider = findUnit(CertificateToObjectUtility.getCareProvider(certificate));

    final var issuedBy = findStaff(CertificateToObjectUtility.getIssuer(certificate));
    final var createdBy = findStaff(CertificateToObjectUtility.getIssuer(certificate));

    //final var patient = findPatient(certificate);
    final var model = findModel(certificate);

    final var certificateEntity = certificateFromDB == null
        ? CertificateEntityMapper.toEntity(certificate)
        : CertificateEntityMapper.updateEntity(certificateFromDB);

    certificateEntity.setIssuedBy(
        RepositoryUtility.getEntity(issuedBy, CertificateToObjectUtility.getIssuer(certificate),
            StaffEntityMapper::toEntity)
    );
    certificateEntity.setCreatedBy(
        RepositoryUtility.getEntity(createdBy, CertificateToObjectUtility.getIssuer(certificate),
            StaffEntityMapper::toEntity)
    );

    certificateEntity.setPatient(
        PatientEntityMapper.toEntity(certificate.certificateMetaData().patient()));

    certificateEntity.setCareProvider(RepositoryUtility.getEntity(careProvider,
        CertificateToObjectUtility.getCareProvider(certificate),
        UnitEntityMapper::toCareProviderEntity));
    certificateEntity.setCareUnit(
        RepositoryUtility.getEntity(careUnit, CertificateToObjectUtility.getCareUnit(certificate),
            UnitEntityMapper::toSubUnitEntity)
    );
    certificateEntity.setIssuedOnUnit(RepositoryUtility.getEntity(issuedOn,
        CertificateToObjectUtility.getIssuingUnit(certificate),
        UnitEntityMapper::toCareUnitEntity));

    certificateEntity.setCertificateModel(
        RepositoryUtility.getEntity(model,
            CertificateToObjectUtility.getCertificateModel(certificate),
            CertificateModelEntityMapper::toEntity)
    );

    certificateEntity.setData(
        CertificateDataEntity.builder()
            .data(new byte[0])
            .build()
    );

    return certificateEntity;
  }

  private CertificateModelEntity findModel(Certificate certificate) {
    return certificateModelEntityRepository.findByTypeAndVersion(
        CertificateToObjectUtility.getCertificateModel(certificate).id().type().type(),
        CertificateToObjectUtility.getCertificateModel(certificate).id().version().version()
    );
  }

  private PatientEntity findPatient(Certificate certificate) {
    return patientEntityRepository.findById(
        CertificateToObjectUtility.getPatient(certificate).id().id()
    );
  }

  private StaffEntity findStaff(Staff staff) {
    final var staffEntity = staffEntityRepository.findByHsaId(
        staff.hsaId().id()
    );

    return RepositoryUtility.saveIfNotExists(staffEntity, staff, StaffEntityMapper::toEntity,
        staffEntityRepository);
  }

  private UnitEntity findUnit(Unit unit) {
    final var unitEntity = unitEntityRepository.findByHsaId(unit.getHsaId());

    return RepositoryUtility.saveIfNotExists(
        unitEntity,
        unit,
        UnitEntityMapper::toEntity,
        unitEntityRepository);
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
