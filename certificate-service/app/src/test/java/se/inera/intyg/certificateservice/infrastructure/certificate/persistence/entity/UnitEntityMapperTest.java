package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper;

class UnitEntityMapperTest {

  private static final UnitEntity CARE_UNIT_ENTITY = UnitEntity.builder()
      .hsaId("HSA_ID")
      .name("NAME")
      .type(
          UnitTypeEntity.builder()
              .type(UnitType.CARE_UNIT.name())
              .key(UnitType.CARE_UNIT.getKey())
              .build()
      )
      .build();

  private static final UnitEntity SUB_UNIT_ENTITY = UnitEntity.builder()
      .hsaId("HSA_ID")
      .name("NAME")
      .type(
          UnitTypeEntity.builder()
              .type(UnitType.SUB_UNIT.name())
              .key(UnitType.SUB_UNIT.getKey())
              .build()
      )
      .build();

  private static final UnitEntity CARE_PROVIDER_ENTITY = UnitEntity.builder()
      .hsaId("HSA_ID")
      .name("NAME")
      .type(
          UnitTypeEntity.builder()
              .type(UnitType.CARE_PROVIDER.name())
              .key(UnitType.CARE_PROVIDER.getKey())
              .build()
      )
      .build();

  private static final Unit CARE_UNIT = Unit.builder()
      .hsaId("HSA_ID")
      .name("NAME")
      .type(UnitType.CARE_UNIT)
      .build();

  private static final Unit SUB_UNIT = Unit.builder()
      .hsaId("HSA_ID")
      .name("NAME")
      .type(UnitType.SUB_UNIT)
      .build();

  private static final Unit CARE_PROVIDER = Unit.builder()
      .hsaId("HSA_ID")
      .name("NAME")
      .type(UnitType.CARE_PROVIDER)
      .build();

  @Nested
  class ToEntity {

    @Nested
    class SubUnit {

      @Test
      void shouldMapHsaId() {
        final var response = UnitEntityMapper.toEntity(SUB_UNIT);

        assertEquals(SUB_UNIT.getHsaId(), response.getHsaId());
      }

      @Test
      void shouldMapName() {
        final var response = UnitEntityMapper.toEntity(SUB_UNIT);

        assertEquals(SUB_UNIT.getName(), response.getName());
      }

      @Test
      void shouldMapTypeKey() {
        final var response = UnitEntityMapper.toEntity(SUB_UNIT);

        assertEquals(SUB_UNIT.getType().getKey(), response.getType().getKey());
      }

      @Test
      void shouldMapType() {
        final var response = UnitEntityMapper.toEntity(SUB_UNIT);

        assertEquals(SUB_UNIT.getType().name(), response.getType().getType());
      }
    }

    @Nested
    class CareUnit {

      @Test
      void shouldMapHsaId() {
        final var response = UnitEntityMapper.toEntity(CARE_UNIT);

        assertEquals(CARE_UNIT.getHsaId(), response.getHsaId());
      }

      @Test
      void shouldMapName() {
        final var response = UnitEntityMapper.toEntity(CARE_UNIT);

        assertEquals(CARE_UNIT.getName(), response.getName());
      }

      @Test
      void shouldMapTypeKey() {
        final var response = UnitEntityMapper.toEntity(CARE_UNIT);

        assertEquals(CARE_UNIT.getType().getKey(), response.getType().getKey());
      }

      @Test
      void shouldMapType() {
        final var response = UnitEntityMapper.toEntity(CARE_UNIT);

        assertEquals(CARE_UNIT.getType().name(), response.getType().getType());
      }
    }

    @Nested
    class CareProvider {

      @Test
      void shouldMapHsaId() {
        final var response = UnitEntityMapper.toEntity(CARE_PROVIDER);

        assertEquals(CARE_PROVIDER.getHsaId(), response.getHsaId());
      }

      @Test
      void shouldMapName() {
        final var response = UnitEntityMapper.toEntity(CARE_PROVIDER);

        assertEquals(CARE_PROVIDER.getName(), response.getName());
      }

      @Test
      void shouldMapTypeKey() {
        final var response = UnitEntityMapper.toEntity(CARE_PROVIDER);

        assertEquals(CARE_PROVIDER.getType().getKey(), response.getType().getKey());
      }

      @Test
      void shouldMapType() {
        final var response = UnitEntityMapper.toEntity(CARE_PROVIDER);

        assertEquals(CARE_PROVIDER.getType().name(), response.getType().getType());
      }
    }
  }

  @Nested
  class ToDomain {

    @Nested
    class SubUnit {

      @Test
      void shouldMapHsaId() {
        final var response = UnitEntityMapper.toSubUnitDomain(SUB_UNIT_ENTITY);

        assertEquals(SUB_UNIT_ENTITY.getHsaId(), response.hsaId().id());
      }

      @Test
      void shouldMapName() {
        final var response = UnitEntityMapper.toSubUnitDomain(SUB_UNIT_ENTITY);

        assertEquals(SUB_UNIT_ENTITY.getName(), response.name());
      }
    }

    @Nested
    class CareUnit {

      @Test
      void shouldMapHsaId() {
        final var response = UnitEntityMapper.toCareUnitDomain(CARE_UNIT_ENTITY);

        assertEquals(CARE_UNIT_ENTITY.getHsaId(), response.hsaId().id());
      }

      @Test
      void shouldMapName() {
        final var response = UnitEntityMapper.toCareUnitDomain(CARE_UNIT_ENTITY);

        assertEquals(CARE_UNIT_ENTITY.getName(), response.name().name());
      }
    }

    @Nested
    class CareProvider {

      @Test
      void shouldMapHsaId() {
        final var response = UnitEntityMapper.toCareProviderDomain(CARE_PROVIDER_ENTITY);

        assertEquals(CARE_PROVIDER_ENTITY.getHsaId(), response.hsaId().id());
      }

      @Test
      void shouldMapName() {
        final var response = UnitEntityMapper.toCareProviderDomain(CARE_PROVIDER_ENTITY);

        assertEquals(CARE_PROVIDER_ENTITY.getName(), response.name().name());
      }
    }
  }


}