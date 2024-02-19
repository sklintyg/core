package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_NAME;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitTypeEntity;

class UnitEntityMapperTest {

  private static final UnitEntity CARE_UNIT_ENTITY = UnitEntity.builder()
      .hsaId(ALFA_MEDICINCENTRUM_ID)
      .name(ALFA_MEDICINCENTRUM_NAME)
      .type(
          UnitTypeEntity.builder()
              .type(UnitType.CARE_UNIT.name())
              .key(UnitType.CARE_UNIT.getKey())
              .build()
      )
      .build();

  private static final UnitEntity SUB_UNIT_ENTITY = UnitEntity.builder()
      .hsaId(ALFA_ALLERGIMOTTAGNINGEN_ID)
      .name(ALFA_ALLERGIMOTTAGNINGEN_NAME)
      .type(
          UnitTypeEntity.builder()
              .type(UnitType.SUB_UNIT.name())
              .key(UnitType.SUB_UNIT.getKey())
              .build()
      )
      .build();

  private static final UnitEntity CARE_PROVIDER_ENTITY = UnitEntity.builder()
      .hsaId(ALFA_REGIONEN_ID)
      .name(ALFA_REGIONEN_NAME)
      .type(
          UnitTypeEntity.builder()
              .type(UnitType.CARE_PROVIDER.name())
              .key(UnitType.CARE_PROVIDER.getKey())
              .build()
      )
      .build();

  private static final CareUnit CARE_UNIT = CareUnit.builder()
      .hsaId(new HsaId(ALFA_MEDICINCENTRUM_ID))
      .name(new UnitName(ALFA_MEDICINCENTRUM_NAME))
      .build();

  private static final SubUnit SUB_UNIT = SubUnit.builder()
      .hsaId(new HsaId(ALFA_ALLERGIMOTTAGNINGEN_ID))
      .name(new UnitName(ALFA_ALLERGIMOTTAGNINGEN_NAME))
      .build();

  private static final CareProvider CARE_PROVIDER = CareProvider.builder()
      .hsaId(new HsaId(ALFA_REGIONEN_ID))
      .name(new UnitName(ALFA_REGIONEN_NAME))
      .build();

  @Nested
  class ToEntity {

    @Test
    void shallMapCareProvider() {
      assertEquals(CARE_PROVIDER_ENTITY,
          UnitEntityMapper.toEntity(CARE_PROVIDER)
      );
    }

    @Test
    void shallMapCareUnit() {
      assertEquals(CARE_UNIT_ENTITY,
          UnitEntityMapper.toEntity(CARE_UNIT)
      );
    }

    @Test
    void shallMapSubUnit() {
      assertEquals(SUB_UNIT_ENTITY,
          UnitEntityMapper.toEntity(SUB_UNIT)
      );
    }
  }

  @Nested
  class ToDomain {

    @Test
    void shallMapCareProvider() {
      assertEquals(CARE_PROVIDER,
          UnitEntityMapper.toCareProviderDomain(CARE_PROVIDER_ENTITY)
      );
    }

    @Test
    void shallMapCareUnit() {
      assertEquals(CARE_UNIT,
          UnitEntityMapper.toCareUnitDomain(CARE_UNIT_ENTITY)
      );
    }

    @Test
    void shallMapSubUnit() {
      assertEquals(SUB_UNIT,
          UnitEntityMapper.toSubUnitDomain(SUB_UNIT_ENTITY)
      );
    }

    @Test
    void shallMapIssuedUnitOfCareUnit() {
      assertEquals(CARE_UNIT,
          UnitEntityMapper.toIssuingUnitDomain(CARE_UNIT_ENTITY)
      );
    }

    @Test
    void shallMapIssuedUnitOfSubUnit() {
      assertEquals(SUB_UNIT,
          UnitEntityMapper.toIssuingUnitDomain(SUB_UNIT_ENTITY)
      );
    }
  }
}