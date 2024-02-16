package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper.toEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.CareProvider;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.IssuingUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;

@Repository
@RequiredArgsConstructor
public class UnitRepository {

  final UnitEntityRepository unitEntityRepository;

  public UnitEntity careProvider(CareProvider careProvider) {
    return unitEntityRepository.findByHsaId(careProvider.hsaId().id())
        .orElseGet(
            () -> unitEntityRepository.save(
                toEntity(careProvider)
            )
        );
  }
  
  public UnitEntity careUnit(CareUnit careUnit) {
    return unitEntityRepository.findByHsaId(careUnit.hsaId().id())
        .orElseGet(
            () -> unitEntityRepository.save(
                toEntity(careUnit)
            )
        );
  }

  public UnitEntity subUnit(SubUnit subUnit) {
    return unitEntityRepository.findByHsaId(subUnit.hsaId().id())
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
}
