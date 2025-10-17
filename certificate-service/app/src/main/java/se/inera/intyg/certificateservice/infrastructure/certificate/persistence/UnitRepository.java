package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper.toEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.IssuingUnit;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UnitRepository {

  private final UnitEntityRepository unitEntityRepository;
  private final MetadataVersionRepository metadataVersionRepository;

  public UnitEntity careProvider(CareProvider careProvider) {
    return unitEntityRepository.findByHsaId(careProvider.hsaId().id())
        .map(unitEntity -> saveUnit(unitEntity, toEntity(careProvider)))
        .orElseGet(
            () -> unitEntityRepository.save(
                toEntity(careProvider)
            )
        );
  }

  public UnitEntity careUnit(CareUnit careUnit) {
    return unitEntityRepository.findByHsaId(careUnit.hsaId().id())
        .map(unitEntity -> saveUnit(unitEntity, toEntity(careUnit)))
        .orElseGet(
            () -> unitEntityRepository.save(
                toEntity(careUnit)
            )
        );
  }

  public UnitEntity subUnit(SubUnit subUnit) {
    return unitEntityRepository.findByHsaId(subUnit.hsaId().id())
        .map(unitEntity -> saveUnit(unitEntity, toEntity(subUnit)))
        .orElseGet(
            () -> unitEntityRepository.save(
                toEntity(subUnit)
            )
        );
  }

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

  private UnitEntity saveUnit(UnitEntity unitEntity, UnitEntity newUnitEntity) {
    if (unitEntity.hasDiff(newUnitEntity)) {
      return metadataVersionRepository.saveUnitVersion(unitEntity, newUnitEntity);
    }
    return unitEntity;
  }
}
