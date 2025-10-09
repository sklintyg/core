package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientVersionEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffVersionEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitVersionEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientVersionEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffVersionEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitVersionEntityRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MetadataVersionRepository {

  private final StaffEntityRepository staffEntityRepository;
  private final UnitEntityRepository unitEntityRepository;
  private final PatientEntityRepository patientEntityRepository;
  private final StaffVersionEntityRepository staffVersionEntityRepository;
  private final PatientVersionEntityRepository patientVersionEntityRepository;
  private final UnitVersionEntityRepository unitVersionEntityRepository;
  private final EntityManager entityManager;

  @Transactional(TxType.REQUIRES_NEW)
  public StaffEntity saveStaffVersion(StaffEntity staffEntity, StaffEntity newStaffEntity) {
    final var staffVersionEntity = StaffVersionEntityMapper.toStaffVersion(staffEntity);
    staffEntity.updateWith(newStaffEntity);
    var result = staffEntityRepository.save(staffEntity);
    entityManager.flush();
    updateStaffVersionHistory(staffVersionEntity);
    return result;
  }

  private void updateStaffVersionHistory(StaffVersionEntity staffVersionEntity) {
    final var latestVersion =
        staffVersionEntityRepository.findFirstByHsaIdOrderByValidFromDesc(
            staffVersionEntity.getHsaId());

    latestVersion.ifPresent(
        versionEntity -> staffVersionEntity.setValidFrom(versionEntity.getValidTo()));

    staffVersionEntityRepository.save(staffVersionEntity);
  }

  @Transactional(TxType.REQUIRES_NEW)
  public UnitEntity saveUnit(UnitEntity unitEntity, UnitEntity newUnitEntity) {
    final var unitVersionEntity = UnitVersionEntityMapper.toUnitVersion(unitEntity);
    unitEntity.updateWith(newUnitEntity);
    var result = unitEntityRepository.save(unitEntity);
    entityManager.flush();
    saveUnitVersion(unitVersionEntity);
    return result;
  }


  private void saveUnitVersion(UnitVersionEntity unitVersionEntity) {
    final var latestVersion = unitVersionEntityRepository
        .findFirstByHsaIdOrderByValidFromDesc(unitVersionEntity.getHsaId());

    latestVersion.ifPresent(
        versionEntity -> unitVersionEntity.setValidFrom(versionEntity.getValidTo()));

    unitVersionEntityRepository.save(unitVersionEntity);
  }


  @Transactional(TxType.REQUIRES_NEW)
  public PatientEntity savePatientVersion(PatientEntity patientEntity,
      PatientEntity newPatientEntity) {

    final var patientVersionEntity = PatientVersionEntityMapper.toPatientVersion(patientEntity);

    patientEntity.updateWith(newPatientEntity);
    var result = patientEntityRepository.save(patientEntity);
    entityManager.flush();
    updatePatientVersionHistory(patientVersionEntity);
    return result;


  }


  private void updatePatientVersionHistory(PatientVersionEntity patientVersionEntity) {
    final var latestVersion = patientVersionEntityRepository
        .findFirstByIdOrderByValidFromDesc(patientVersionEntity.getId());

    latestVersion.ifPresent(
        versionEntity -> patientVersionEntity.setValidFrom(versionEntity.getValidTo()));

    patientVersionEntityRepository.save(patientVersionEntity);
  }

  @Transactional(TxType.REQUIRES_NEW)
  public StaffEntity getFreshStaffEntity(String hsaId) {
    return staffEntityRepository.findByHsaId(hsaId)
        .orElseThrow(() -> new IllegalStateException(
            String.format("StaffEntity for %s could not be found", hsaId)));
  }

  @Transactional(TxType.REQUIRES_NEW)
  public UnitEntity getFreshUnitEntity(String hsaId) {
    return unitEntityRepository.findByHsaId(hsaId)
        .orElseThrow(() -> new IllegalStateException(
            String.format("StaffEntity for %s could not be found", hsaId)));
  }

  @Transactional(TxType.REQUIRES_NEW)
  public PatientEntity getFreshPatientEntity(String id) {
    return patientEntityRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("PersonEntity ould not be found"));
  }
}
