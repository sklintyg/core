package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.Inactive;
import se.inera.intyg.certificateservice.domain.unit.model.IssuingUnit;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.domain.unit.model.UnitAddress;
import se.inera.intyg.certificateservice.domain.unit.model.UnitContactInfo;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitTypeEntity;

public class UnitEntityMapper {

  private UnitEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static UnitEntity toEntity(CareUnit careUnit) {
    return UnitEntity.builder()
        .type(
            UnitTypeEntity.builder()
                .key(UnitType.CARE_UNIT.getKey())
                .type(UnitType.CARE_UNIT.name())
                .build()
        )
        .hsaId(careUnit.hsaId().id())
        .name(careUnit.name().name())
        .address(careUnit.address().address())
        .zipCode(careUnit.address().zipCode())
        .city(careUnit.address().city())
        .phoneNumber(careUnit.contactInfo().phoneNumber())
        .email(careUnit.contactInfo().email())
        .workplaceCode(careUnit.workplaceCode() == null ? null : careUnit.workplaceCode().code())
        .build();
  }

  public static UnitEntity toEntity(SubUnit subUnit) {
    return UnitEntity.builder()
        .type(
            UnitTypeEntity.builder()
                .key(UnitType.SUB_UNIT.getKey())
                .type(UnitType.SUB_UNIT.name())
                .build()
        )
        .hsaId(subUnit.hsaId().id())
        .name(subUnit.name().name())
        .address(subUnit.address().address())
        .zipCode(subUnit.address().zipCode())
        .city(subUnit.address().city())
        .phoneNumber(subUnit.contactInfo().phoneNumber())
        .email(subUnit.contactInfo().email())
        .workplaceCode(subUnit.workplaceCode() == null ? null : subUnit.workplaceCode().code())
        .build();
  }

  public static UnitEntity toEntity(CareProvider careProvider) {
    return UnitEntity.builder()
        .type(
            UnitTypeEntity.builder()
                .key(UnitType.CARE_PROVIDER.getKey())
                .type(UnitType.CARE_PROVIDER.name())
                .build()
        )
        .hsaId(careProvider.hsaId().id())
        .name(careProvider.name().name())
        .build();
  }

  public static CareProvider toCareProviderDomain(UnitEntity unit) {
    return CareProvider.builder()
        .name(new UnitName(unit.getName()))
        .hsaId(new HsaId(unit.getHsaId()))
        .build();
  }

  public static CareUnit toCareUnitDomain(UnitEntity unit) {
    return CareUnit.builder()
        .name(new UnitName(unit.getName()))
        .hsaId(new HsaId(unit.getHsaId()))
        .address(
            UnitAddress.builder()
                .address(unit.getAddress())
                .zipCode(unit.getZipCode())
                .city(unit.getCity())
                .build()
        )
        .contactInfo(
            UnitContactInfo.builder()
                .phoneNumber(unit.getPhoneNumber())
                .email(unit.getEmail())
                .build()
        )
        .workplaceCode(new WorkplaceCode(unit.getWorkplaceCode()))
        .inactive(new Inactive(false))
        .build();
  }

  public static SubUnit toSubUnitDomain(UnitEntity unit) {
    return SubUnit.builder()
        .name(new UnitName(unit.getName()))
        .hsaId(new HsaId(unit.getHsaId()))
        .address(
            UnitAddress.builder()
                .address(unit.getAddress())
                .zipCode(unit.getZipCode())
                .city(unit.getCity())
                .build()
        )
        .contactInfo(
            UnitContactInfo.builder()
                .phoneNumber(unit.getPhoneNumber())
                .email(unit.getEmail())
                .build()
        )
        .workplaceCode(new WorkplaceCode(unit.getWorkplaceCode()))
        .inactive(new Inactive(false))
        .build();
  }

  public static IssuingUnit toIssuingUnitDomain(UnitEntity unit) {
    return unit.getType().getKey() == UnitType.SUB_UNIT.getKey()
        ? toSubUnitDomain(unit)
        : toCareUnitDomain(unit);
  }
}
