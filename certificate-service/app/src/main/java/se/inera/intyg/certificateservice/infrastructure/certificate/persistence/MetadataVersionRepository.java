package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.repository.MetadataRepository;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientVersionEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffVersionEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitVersionEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper;
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
public class MetadataVersionRepository implements MetadataRepository {

  private final StaffEntityRepository staffEntityRepository;
  private final UnitEntityRepository unitEntityRepository;
  private final PatientEntityRepository patientEntityRepository;
  private final StaffVersionEntityRepository staffVersionEntityRepository;
  private final PatientVersionEntityRepository patientVersionEntityRepository;
  private final UnitVersionEntityRepository unitVersionEntityRepository;

  @Transactional(TxType.REQUIRES_NEW)
  public void saveStaffVersion(StaffEntity staffEntity, StaffEntity newStaffEntity) {
    final var staffVersionEntity = StaffVersionEntityMapper.toStaffVersion(staffEntity);
    staffEntity.updateWith(newStaffEntity);
    staffEntityRepository.save(staffEntity);
    updateStaffVersionHistory(staffVersionEntity);
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
  public void saveUnit(UnitEntity unitEntity, UnitEntity newUnitEntity) {
    final var unitVersionEntity = UnitVersionEntityMapper.toUnitVersion(unitEntity);
    unitEntity.updateWith(newUnitEntity);
    unitEntityRepository.save(unitEntity);
    saveUnitVersion(unitVersionEntity);
  }


  private void saveUnitVersion(UnitVersionEntity unitVersionEntity) {
    final var latestVersion = unitVersionEntityRepository
        .findFirstByHsaIdOrderByValidFromDesc(unitVersionEntity.getHsaId());
    latestVersion.ifPresent(
        versionEntity -> unitVersionEntity.setValidFrom(versionEntity.getValidTo()));
    unitVersionEntityRepository.save(unitVersionEntity);
  }


  @Transactional(TxType.REQUIRES_NEW)
  public void savePatientVersion(PatientEntity patientEntity,
      PatientEntity newPatientEntity) {
    final var patientVersionEntity = PatientVersionEntityMapper.toPatientVersion(patientEntity);
    patientEntity.updateWith(newPatientEntity);
    patientEntityRepository.save(patientEntity);
    updatePatientVersionHistory(patientVersionEntity);
  }


  private void updatePatientVersionHistory(PatientVersionEntity patientVersionEntity) {
    final var latestVersion = patientVersionEntityRepository
        .findFirstByIdOrderByValidFromDesc(patientVersionEntity.getId());
    latestVersion.ifPresent(
        versionEntity -> patientVersionEntity.setValidFrom(versionEntity.getValidTo()));
    patientVersionEntityRepository.save(patientVersionEntity);
  }


  @Override
  public CertificateMetaData getMetadataFromSignInstance(CertificateMetaData metadata,
      LocalDateTime signedAt) {

    if (signedAt == null) {
      throw new IllegalStateException(
          "SignedAt is required to fetch metadata from signed instance");
    }

    final var patientWhenSigned = getPatientVersionWhenSigned(metadata.patient(),
        signedAt);
    final var staffMap = getStaffVersionsWhenSigned(
        List.of(metadata.issuer(), metadata.creator()), signedAt);
    final var unitMap = getUnitVersionsWhenSigned(
        List.of(metadata.issuingUnit().hsaId().id(), metadata.careUnit().hsaId().id(),
            metadata.careProvider().hsaId().id()),
        signedAt);

    return CertificateMetaData.builder()
        .patient(patientWhenSigned)
        .issuer(staffMap.get(metadata.issuer().hsaId().id()))
        .creator(staffMap.get(metadata.creator().hsaId().id()))
        .issuingUnit(
            unitMap.containsKey(metadata.issuingUnit().hsaId().id())
                ? UnitEntityMapper.toIssuingUnitDomain(
                unitMap.get(metadata.issuingUnit().hsaId().id()))
                : metadata.issuingUnit()
        )
        .careProvider(
            unitMap.containsKey(metadata.careProvider().hsaId().id())
                ? UnitEntityMapper.toCareProviderDomain(
                unitMap.get(metadata.careProvider().hsaId().id()))
                : metadata.careProvider()
        )
        .careUnit(
            unitMap.containsKey(metadata.careUnit().hsaId().id())
                ? UnitEntityMapper.toCareUnitDomain(unitMap.get(metadata.careUnit().hsaId().id()))
                : metadata.careUnit()
        )
        .build();
  }

  private Patient getPatientVersionWhenSigned(Patient patient,
      LocalDateTime signedAt) {
    return patientVersionEntityRepository
        .findFirstCoveringTimestampOrderByMostRecent(patient.id().id(), signedAt)
        .map(PatientVersionEntityMapper::toPatient)
        .map(PatientEntityMapper::toDomain)
        .orElse(patient);
  }

  private Map<String, Staff> getStaffVersionsWhenSigned(List<Staff> staffs,
      LocalDateTime signedAt) {
    List<StaffVersionEntity> entities = staffVersionEntityRepository
        .findAllCoveringTimestampByHsaIdIn(
            staffs.stream().map(s -> s.hsaId().id()).collect(Collectors.toList()), signedAt);

    Map<String, Staff> versionedStaffMap = entities.stream()
        .map(StaffVersionEntityMapper::toStaff)
        .map(StaffEntityMapper::toDomain)
        .collect(Collectors.toMap(s -> s.hsaId().id(), Function.identity(), (a, b) -> a));

    return staffs.stream()
        .collect(Collectors.toMap(
            s -> s.hsaId().id(),
            s -> versionedStaffMap.getOrDefault(s.hsaId().id(), s),
            (a, b) -> a
        ));
  }


  private Map<String, UnitEntity> getUnitVersionsWhenSigned(List<String> unitHsaIds,
      LocalDateTime signedAt) {
    List<UnitVersionEntity> entities =
        unitVersionEntityRepository.findAllCoveringTimestampByHsaIdIn(unitHsaIds, signedAt);
    return entities.stream()
        .map(UnitVersionEntityMapper::toUnit)
        .collect(Collectors.toMap(UnitEntity::getHsaId, Function.identity(), (a, b) -> a));
  }

}
