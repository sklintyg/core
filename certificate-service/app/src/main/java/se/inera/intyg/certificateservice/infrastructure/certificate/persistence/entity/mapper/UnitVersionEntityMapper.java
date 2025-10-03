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
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitVersionEntity;

public class UnitVersionEntityMapper {

  private UnitVersionEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static UnitVersionEntity toEntity(UnitEntity unitEntity) {
		return UnitVersionEntity.builder()
				.hsaId(unitEntity.getHsaId())
				.name(unitEntity.getName())
				.address(unitEntity.getAddress())
				.zipCode(unitEntity.getZipCode())
				.city(unitEntity.getCity())
				.phoneNumber(unitEntity.getPhoneNumber())
				.email(unitEntity.getEmail())
				.workplaceCode(unitEntity.getWorkplaceCode())
				.type(unitEntity.getType())
				.build();
	}
}
