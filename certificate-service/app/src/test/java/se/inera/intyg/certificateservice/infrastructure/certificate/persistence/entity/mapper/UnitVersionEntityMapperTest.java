package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_WORKPLACE_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ZIP_CODE;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitVersionEntity;

class UnitVersionEntityMapperTest {

  private static final UnitTypeEntity UNIT_TYPE_CARE_UNIT = UnitTypeEntity.builder()
      .type(UnitType.CARE_UNIT.name())
      .key(UnitType.CARE_UNIT.getKey())
      .build();

  private static final UnitEntity UNIT_ENTITY = getUnitEntity();
  private static final UnitVersionEntity UNIT_VERSION_ENTITY = getUnitVersionEntity();

  @Nested
  class ToUnitVersion {

    @Test
    void shouldMapHsaId() {
      final var response = UnitVersionEntityMapper.toUnitVersion(UNIT_ENTITY);
      assertEquals(UNIT_ENTITY.getHsaId(), response.getHsaId());
    }

    @Test
    void shouldMapName() {
      final var response = UnitVersionEntityMapper.toUnitVersion(UNIT_ENTITY);
      assertEquals(UNIT_ENTITY.getName(), response.getName());
    }

    @Test
    void shouldMapAddress() {
      final var response = UnitVersionEntityMapper.toUnitVersion(UNIT_ENTITY);
      assertEquals(UNIT_ENTITY.getAddress(), response.getAddress());
    }

    @Test
    void shouldMapZipCode() {
      final var response = UnitVersionEntityMapper.toUnitVersion(UNIT_ENTITY);
      assertEquals(UNIT_ENTITY.getZipCode(), response.getZipCode());
    }

    @Test
    void shouldMapCity() {
      final var response = UnitVersionEntityMapper.toUnitVersion(UNIT_ENTITY);
      assertEquals(UNIT_ENTITY.getCity(), response.getCity());
    }

    @Test
    void shouldMapPhoneNumber() {
      final var response = UnitVersionEntityMapper.toUnitVersion(UNIT_ENTITY);
      assertEquals(UNIT_ENTITY.getPhoneNumber(), response.getPhoneNumber());
    }

    @Test
    void shouldMapEmail() {
      final var response = UnitVersionEntityMapper.toUnitVersion(UNIT_ENTITY);
      assertEquals(UNIT_ENTITY.getEmail(), response.getEmail());
    }

    @Test
    void shouldMapWorkplaceCode() {
      final var response = UnitVersionEntityMapper.toUnitVersion(UNIT_ENTITY);
      assertEquals(UNIT_ENTITY.getWorkplaceCode(), response.getWorkplaceCode());
    }

    @Test
    void shouldMapType() {
      final var response = UnitVersionEntityMapper.toUnitVersion(UNIT_ENTITY);
      assertEquals(UNIT_ENTITY.getType(), response.getType());
    }

    @Test
    void shouldMapUnit() {
      final var response = UnitVersionEntityMapper.toUnitVersion(UNIT_ENTITY);
      assertEquals(UNIT_ENTITY, response.getUnit());
    }

    @Test
    void shouldSetValidFromToNull() {
      final var response = UnitVersionEntityMapper.toUnitVersion(UNIT_ENTITY);
      assertNull(response.getValidFrom());
    }

    @Test
    void shouldSetValidToToCurrentTime() {
      final var before = LocalDateTime.now();
      final var response = UnitVersionEntityMapper.toUnitVersion(UNIT_ENTITY);
      final var after = LocalDateTime.now();

      assertNotNull(response.getValidTo());
      assertTrue(response.getValidTo().isAfter(before.minusSeconds(1)));
      assertTrue(response.getValidTo().isBefore(after.plusSeconds(1)));
    }
  }

  @Nested
  class ToUnit {

    @Test
    void shouldMapHsaId() {
      final var response = UnitVersionEntityMapper.toUnit(UNIT_VERSION_ENTITY);
      assertEquals(UNIT_VERSION_ENTITY.getHsaId(), response.getHsaId());
    }

    @Test
    void shouldMapName() {
      final var response = UnitVersionEntityMapper.toUnit(UNIT_VERSION_ENTITY);
      assertEquals(UNIT_VERSION_ENTITY.getName(), response.getName());
    }

    @Test
    void shouldMapAddress() {
      final var response = UnitVersionEntityMapper.toUnit(UNIT_VERSION_ENTITY);
      assertEquals(UNIT_VERSION_ENTITY.getAddress(), response.getAddress());
    }

    @Test
    void shouldMapZipCode() {
      final var response = UnitVersionEntityMapper.toUnit(UNIT_VERSION_ENTITY);
      assertEquals(UNIT_VERSION_ENTITY.getZipCode(), response.getZipCode());
    }

    @Test
    void shouldMapCity() {
      final var response = UnitVersionEntityMapper.toUnit(UNIT_VERSION_ENTITY);
      assertEquals(UNIT_VERSION_ENTITY.getCity(), response.getCity());
    }

    @Test
    void shouldMapPhoneNumber() {
      final var response = UnitVersionEntityMapper.toUnit(UNIT_VERSION_ENTITY);
      assertEquals(UNIT_VERSION_ENTITY.getPhoneNumber(), response.getPhoneNumber());
    }

    @Test
    void shouldMapEmail() {
      final var response = UnitVersionEntityMapper.toUnit(UNIT_VERSION_ENTITY);
      assertEquals(UNIT_VERSION_ENTITY.getEmail(), response.getEmail());
    }

    @Test
    void shouldMapWorkplaceCode() {
      final var response = UnitVersionEntityMapper.toUnit(UNIT_VERSION_ENTITY);
      assertEquals(UNIT_VERSION_ENTITY.getWorkplaceCode(), response.getWorkplaceCode());
    }

    @Test
    void shouldMapType() {
      final var response = UnitVersionEntityMapper.toUnit(UNIT_VERSION_ENTITY);
      assertEquals(UNIT_VERSION_ENTITY.getType(), response.getType());
    }
  }

  private static UnitEntity getUnitEntity() {
    return UnitEntity.builder()
        .key(1)
        .hsaId(ALFA_MEDICINCENTRUM_ID)
        .name(ALFA_MEDICINCENTRUM_NAME)
        .address(ALFA_MEDICINCENTRUM_ADDRESS)
        .zipCode(ALFA_MEDICINCENTRUM_ZIP_CODE)
        .city(ALFA_MEDICINCENTRUM_CITY)
        .phoneNumber(ALFA_MEDICINCENTRUM_PHONENUMBER)
        .email(ALFA_MEDICINCENTRUM_EMAIL)
        .workplaceCode(ALFA_MEDICINCENTRUM_WORKPLACE_CODE)
        .type(UNIT_TYPE_CARE_UNIT)
        .build();
  }

  private static UnitVersionEntity getUnitVersionEntity() {
    return UnitVersionEntity.builder()
        .key(1)
        .hsaId(ALFA_MEDICINCENTRUM_ID)
        .name(ALFA_MEDICINCENTRUM_NAME)
        .address(ALFA_MEDICINCENTRUM_ADDRESS)
        .zipCode(ALFA_MEDICINCENTRUM_ZIP_CODE)
        .city(ALFA_MEDICINCENTRUM_CITY)
        .phoneNumber(ALFA_MEDICINCENTRUM_PHONENUMBER)
        .email(ALFA_MEDICINCENTRUM_EMAIL)
        .workplaceCode(ALFA_MEDICINCENTRUM_WORKPLACE_CODE)
        .type(UNIT_TYPE_CARE_UNIT)
        .validFrom(LocalDateTime.of(2024, 1, 1, 0, 0))
        .validTo(LocalDateTime.of(2024, 12, 31, 23, 59))
        .unit(getUnitEntity())
        .build();
  }
}

