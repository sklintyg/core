package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper.toEntity;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.IssuingUnit;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitVersionEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitVersionEntityRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UnitRepository {

  private final UnitEntityRepository unitEntityRepository;
  private final UnitVersionEntityRepository unitVersionEntityRepository;

  @Transactional
  public UnitEntity careProvider(CareProvider careProvider) {
    return unitEntityRepository.findByHsaId(careProvider.hsaId().id())
        .map(unitEntity -> updateUnitVersion(unitEntity, careProvider))
        .orElseGet(
            () -> unitEntityRepository.save(
                toEntity(careProvider)
            )
        );
  }

  @Transactional
  public UnitEntity careUnit(CareUnit careUnit) {
    return unitEntityRepository.findByHsaId(careUnit.hsaId().id())
        .map(unitEntity -> updateUnitVersion(unitEntity, careUnit))
        .orElseGet(
            () -> unitEntityRepository.save(
                toEntity(careUnit)
            )
        );
  }

  @Transactional
  public UnitEntity subUnit(SubUnit subUnit) {
    return unitEntityRepository.findByHsaId(subUnit.hsaId().id())
        .map(unitEntity -> updateUnitVersion(unitEntity, subUnit))
        .orElseGet(
            () -> unitEntityRepository.save(
                toEntity(subUnit)
            )
        );
  }

  @Transactional
  public UnitEntity issuingUnit(IssuingUnit issuingUnit) {
    if (issuingUnit instanceof CareUnit careUnit) {
      return careUnit(careUnit);
    }

    if (issuingUnit instanceof SubUnit subUnit) {
      return subUnit(subUnit);
    }

    throw new IllegalStateException(
        "Could not map IssuingUnit due to unknown type: '%s'".formatted(issuingUnit)
    );
  }

  private UnitEntity updateUnitVersion(UnitEntity unitEntity, CareProvider careProvider) {
    var newUnitEntity = toEntity(careProvider);
    if (!unitEntity.equals(newUnitEntity)) {
      return saveUnit(unitEntity, newUnitEntity);
    }
    return unitEntity;
  }

  private UnitEntity updateUnitVersion(UnitEntity unitEntity, CareUnit careUnit) {
    var newUnitEntity = toEntity(careUnit);
    if (!unitEntity.equals(newUnitEntity)) {
      return saveUnit(unitEntity, newUnitEntity);
    }
    return unitEntity;
  }

  private UnitEntity updateUnitVersion(UnitEntity unitEntity, SubUnit subUnit) {
    var newUnitEntity = toEntity(subUnit);
    if (!unitEntity.equals(newUnitEntity)) {
      return saveUnit(unitEntity, newUnitEntity);
    }
    return unitEntity;
  }

  private UnitEntity saveUnit(UnitEntity unitEntity, UnitEntity newUnitEntity) {
    try {
      final var unitVersionEntity = UnitVersionEntityMapper.toEntity(unitEntity);
      unitEntity.updateWith(newUnitEntity);
      var result = unitEntityRepository.save(unitEntity);
      saveUnitVersion(unitVersionEntity);
      return result;

    } catch (OptimisticLockException e) {
      log.info("Skipped updating UnitEntity {} because it was updated concurrently",
          unitEntity.getHsaId());
      return unitEntityRepository.findByHsaId(unitEntity.getHsaId())
          .orElse(unitEntity);
    }
  }


  private void saveUnitVersion(UnitVersionEntity unitVersionEntity) {

    final var latestVersion = unitVersionEntityRepository
        .findFirstByHsaIdOrderByValidFromDesc(unitVersionEntity.getHsaId());

    latestVersion.ifPresent(
        versionEntity -> unitVersionEntity.setValidFrom(versionEntity.getValidTo()));

    unitVersionEntityRepository.save(unitVersionEntity);
  }
}
