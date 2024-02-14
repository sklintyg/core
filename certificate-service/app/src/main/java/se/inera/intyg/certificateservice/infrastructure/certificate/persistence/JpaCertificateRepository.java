package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.Unit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateDataEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateModelEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.utils.CertificateToObjectUtility;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.utils.RepositoryUtility;

@Profile("!" + TESTABILITY_PROFILE)
@Primary
@Repository
public class JpaCertificateRepository implements CertificateRepository {

  private final CertificateEntityRepository certificateEntityRepository;
  private final CertificateModelEntityRepository certificateModelEntityRepository;
  private final StaffEntityRepository staffEntityRepository;
  private final UnitEntityRepository unitEntityRepository;
  private final PatientEntityRepository patientEntityRepository;
  private final CertificateModelRepository certificateModelRepository;

  public JpaCertificateRepository(CertificateEntityRepository certificateEntityRepository,
      CertificateModelEntityRepository certificateModelEntityRepository,
      StaffEntityRepository staffEntityRepository, UnitEntityRepository unitEntityRepository,
      PatientEntityRepository patientEntityRepository,
      CertificateModelRepository certificateModelRepository) {
    this.certificateEntityRepository = certificateEntityRepository;
    this.certificateModelEntityRepository = certificateModelEntityRepository;
    this.staffEntityRepository = staffEntityRepository;
    this.unitEntityRepository = unitEntityRepository;
    this.patientEntityRepository = patientEntityRepository;
    this.certificateModelRepository = certificateModelRepository;
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

    final var certificateEntity = buildCertificateEntity(certificate);
    certificateEntityRepository.save(certificateEntity);

    return certificate;
  }

  @Override
  public Certificate getById(CertificateId certificateId) {
    if (certificateId == null) {
      throw new IllegalArgumentException("Cannot get certificate if certificateId is null");
    }

    final var certificate = certificateEntityRepository.findByCertificateId(certificateId.id());

    if (certificate == null) {
      throw new IllegalArgumentException(
          "CertificateId '%s' not present in repository".formatted(certificateId)
      );
    }

    final var model = CertificateModelEntityMapper.toDomain(
        certificate.getCertificateModel()
    );
    final var modelFromDB = certificateModelRepository.getById(model.id());
    return CertificateEntityMapper.toDomain(certificate, modelFromDB);
  }

  @Override
  public boolean exists(CertificateId certificateId) {
    if (certificateId == null) {
      throw new IllegalArgumentException(
          "Cannot check if certificate exists since certificateId is null");
    }

    return certificateEntityRepository.findByCertificateId(certificateId.id()) != null;
  }

  private CertificateEntity buildCertificateEntity(Certificate certificate) {
    final var certificateFromDB = certificateEntityRepository.findByCertificateId(
        certificate.id().id()
    );

    final var careUnit = findUnit(CertificateToObjectUtility.getCareUnit(certificate));
    final var issuedOn = findUnit(CertificateToObjectUtility.getIssuingUnit(certificate));
    final var careProvider = findUnit(CertificateToObjectUtility.getCareProvider(certificate));

    final var issuedBy = findStaff(CertificateToObjectUtility.getIssuer(certificate));
    final var createdBy = findStaff(CertificateToObjectUtility.getIssuer(certificate));

    final var patient = findPatient(certificate);
    final var model = findModel(certificate);

    final var certificateEntity = certificateFromDB == null
        ? CertificateEntityMapper.toEntity(certificate)
        : CertificateEntityMapper.updateEntity(certificateFromDB);

    certificateEntity.setIssuedBy(
        RepositoryUtility.getEntity(
            issuedBy,
            CertificateToObjectUtility.getIssuer(certificate),
            StaffEntityMapper::toEntity)
    );
    certificateEntity.setCreatedBy(
        RepositoryUtility.getEntity(
            createdBy,
            CertificateToObjectUtility.getIssuer(certificate),
            StaffEntityMapper::toEntity)
    );

    certificateEntity.setPatient(
        RepositoryUtility.getEntity(
            patient,
            CertificateToObjectUtility.getPatient(certificate),
            PatientEntityMapper::toEntity)
    );

    certificateEntity.setCareProvider(
        RepositoryUtility.getEntity(
            careProvider,
            CertificateToObjectUtility.getCareProvider(certificate),
            UnitEntityMapper::toCareProviderEntity)
    );

    certificateEntity.setCareUnit(
        RepositoryUtility.getEntity(
            careUnit,
            CertificateToObjectUtility.getCareUnit(certificate),
            UnitEntityMapper::toCareUnitEntity)
    );

    certificateEntity.setIssuedOnUnit(
        RepositoryUtility.getEntity(
            issuedOn,
            CertificateToObjectUtility.getIssuingUnit(certificate),
            certificate.certificateMetaData().issuingUnit() instanceof SubUnit
                ? UnitEntityMapper::toCareUnitEntity : UnitEntityMapper::toSubUnitEntity)
    );

    certificateEntity.setCertificateModel(
        RepositoryUtility.getEntity(
            model,
            CertificateToObjectUtility.getCertificateModel(certificate),
            CertificateModelEntityMapper::toEntity)
    );

    certificateEntity.setData(CertificateDataEntityMapper.toEntity(certificate.elementData()));

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
}
