package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.Unit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitTypeEntity;

public class UnitEntityMapper {

  public static UnitEntity toEntity(Unit unit) {
    return switch (unit.getType()) {
      case SUB_UNIT -> toSubUnitEntity(unit);
      case CARE_UNIT -> toCareUnitEntity(unit);
      case CARE_PROVIDER -> toCareProviderEntity(unit);
    };
  }

  public static UnitEntity toCareProviderEntity(Unit unit) {
    return UnitEntity.builder()
        .type(
            UnitTypeEntity.builder()
                .key(UnitType.CARE_PROVIDER.getKey())
                .type(UnitType.CARE_PROVIDER.name())
                .build()
        )
        .hsaId(unit.getHsaId())
        .name(unit.getName())
        .build();
  }

  public static UnitEntity toCareUnitEntity(Unit unit) {
    return UnitEntity.builder()
        .type(
            UnitTypeEntity.builder()
                .key(UnitType.CARE_UNIT.getKey())
                .type(UnitType.CARE_UNIT.name())
                .build()
        )
        .hsaId(unit.getHsaId())
        .name(unit.getName())
        .build();
  }

  public static UnitEntity toSubUnitEntity(Unit unit) {
    return UnitEntity.builder()
        .type(
            UnitTypeEntity.builder()
                .key(UnitType.SUB_UNIT.getKey())
                .type(UnitType.SUB_UNIT.name())
                .build()
        )
        .hsaId(unit.getHsaId())
        .name(unit.getName())
        .build();
  }
}
