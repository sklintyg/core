package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.IssuingUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitName;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper;

class UnitEntityMapperTest {

  private static final UnitEntity UNIT_ENTITY = UnitEntity.builder()
      .hsaId("HSA_ID")
      .name("NAME")
      .build();

  private static final IssuingUnit UNIT = CareUnit.builder()
      .hsaId(new HsaId("HSA_ID"))
      .name(new UnitName("NAME"))
      .build();

  @Nested
  class toEntity {

    @Test
    void shouldMapHsaId() {
      final var response = UnitEntityMapper.toEntity(UNIT);

      assertEquals(UNIT.hsaId().id(), response.getHsaId());
    }

    @Test
    void shouldMapName() {
      final var response = UnitEntityMapper.toEntity(UNIT);

      assertEquals(UNIT.name().name(), response.getName());
    }

  }

}