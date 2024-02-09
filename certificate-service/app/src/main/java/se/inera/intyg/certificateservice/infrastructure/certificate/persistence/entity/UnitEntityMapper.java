package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.IssuingUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;

public class UnitEntityMapper {

  public static UnitEntity toEntity(IssuingUnit unit) {
    return UnitEntity.builder()
        .hsaId(unit.hsaId().id())
        .name(unit.name().name())
        .build();
  }
}
