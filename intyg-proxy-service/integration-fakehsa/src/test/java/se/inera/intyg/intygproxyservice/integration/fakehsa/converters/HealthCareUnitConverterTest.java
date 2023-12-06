package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

class HealthCareUnitConverterTest {

  private HealthCareUnitConverter healthCareUnitConverter;

  private static final String VALUE = "value";
  private static final LocalDateTime DATE_TIME = LocalDateTime.now();

  @BeforeEach
  void setUp() {
    healthCareUnitConverter = new HealthCareUnitConverter();
  }

  @Nested
  class ParsedCareHealthHealthCareUnitConverter {

    @Test
    void shouldSetIsHealthCareUnitToTrue() {
      final var unit = ParsedCareUnit.builder().build();
      final var result = healthCareUnitConverter.convert(unit);
      assertTrue(result.getUnitIsHealthCareUnit());
    }

    @Nested
    class ConvertId {

      @Test
      void shouldConvertId() {
        final var unit = ParsedCareUnit.builder().id(VALUE).build();
        final var result = healthCareUnitConverter.convert(unit);
        assertEquals(unit.getId(), result.getHealthCareUnitHsaId());
      }

      @Test
      void shouldNotConvertId() {
        final var unit = ParsedCareUnit.builder().build();
        final var result = healthCareUnitConverter.convert(unit);
        assertNull(result.getHealthCareUnitHsaId());
      }
    }

    @Nested
    class ConvertStartDate {


      @Test
      void shouldConvertStartDate() {
        final var unit = ParsedCareUnit.builder().start(DATE_TIME).build();
        final var result = healthCareUnitConverter.convert(unit);
        assertEquals(unit.getStart(), result.getHealthCareProviderStartDate());
      }

      @Test
      void shouldNotConvertStartDate() {
        final var unit = ParsedCareUnit.builder().build();
        final var result = healthCareUnitConverter.convert(unit);
        assertNull(result.getHealthCareProviderStartDate());
      }
    }

    @Nested
    class ConvertEndDate {


      @Test
      void shouldConvertEndDate() {
        final var unit = ParsedCareUnit.builder().end(DATE_TIME).build();
        final var result = healthCareUnitConverter.convert(unit);
        assertEquals(unit.getEnd(), result.getHealthCareProviderEndDate());
      }

      @Test
      void shouldNotConvertStartDate() {
        final var unit = ParsedCareUnit.builder().build();
        final var result = healthCareUnitConverter.convert(unit);
        assertNull(result.getHealthCareProviderEndDate());
      }
    }

    @Nested
    class ConvertUnitName {


      @Test
      void shouldConvertUnitName() {
        final var unit = ParsedCareUnit.builder().name(VALUE).build();
        final var result = healthCareUnitConverter.convert(unit);
        assertEquals(unit.getName(), result.getHealthCareUnitName());
      }

      @Test
      void shouldNotConvertUnitName() {
        final var unit = ParsedCareUnit.builder().build();
        final var result = healthCareUnitConverter.convert(unit);
        assertNull(result.getHealthCareUnitName());
      }
    }

    @Nested
    class ConvertCareProviderHsaId {


      @Test
      void shouldConvertCareProviderHsaId() {
        final var unit = ParsedCareUnit.builder().careProviderHsaId(VALUE).build();
        final var result = healthCareUnitConverter.convert(unit);
        assertEquals(unit.getCareProviderHsaId(), result.getHealthCareProviderHsaId());
      }

      @Test
      void shouldNotConvertCareProviderHsaId() {
        final var unit = ParsedCareUnit.builder().build();
        final var result = healthCareUnitConverter.convert(unit);
        assertNull(result.getHealthCareProviderHsaId());
      }
    }

    @Nested
    class ConvertCareProviderOrgNo {

      @Test
      void shouldConvertCareProviderOrgNo() {
        final var unit = ParsedCareUnit.builder().healthCareProviderOrgno(VALUE).build();
        final var result = healthCareUnitConverter.convert(unit);
        assertEquals(unit.getHealthCareProviderOrgno(), result.getHealthCareProviderOrgNo());
      }

      @Test
      void shouldNotConvertCareProviderOrgNo() {
        final var unit = ParsedCareUnit.builder().build();
        final var result = healthCareUnitConverter.convert(unit);
        assertNull(result.getHealthCareProviderOrgNo());
      }
    }
  }

  @Nested
  class ParsedSubHealthHealthCareUnitConverter {

    @Test
    void shouldSetIsHealthCareUnitToFalse() {
      final var unit = ParsedSubUnit.builder().build();
      final var result = healthCareUnitConverter.convert(unit);
      assertFalse(result.getUnitIsHealthCareUnit());
    }

    @Nested
    class ConvertId {

      @Test
      void shouldConvertId() {
        final var unit = ParsedSubUnit.builder().id(VALUE).build();
        final var result = healthCareUnitConverter.convert(unit);
        assertEquals(unit.getId(), result.getHealthCareUnitMemberHsaId());
      }

      @Test
      void shouldNotConvertId() {
        final var unit = ParsedSubUnit.builder().build();
        final var result = healthCareUnitConverter.convert(unit);
        assertNull(result.getHealthCareUnitMemberHsaId());
      }
    }

    @Nested
    class ConvertStartDate {


      @Test
      void shouldConvertStartDate() {
        final var unit = ParsedSubUnit.builder().start(DATE_TIME).build();
        final var result = healthCareUnitConverter.convert(unit);
        assertEquals(unit.getStart(), result.getHealthCareUnitMemberStartDate());
      }

      @Test
      void shouldNotConvertStartDate() {
        final var unit = ParsedSubUnit.builder().build();
        final var result = healthCareUnitConverter.convert(unit);
        assertNull(result.getHealthCareUnitMemberStartDate());
      }
    }

    @Nested
    class ConvertEndDate {


      @Test
      void shouldConvertEndDate() {
        final var unit = ParsedSubUnit.builder().end(DATE_TIME).build();
        final var result = healthCareUnitConverter.convert(unit);
        assertEquals(unit.getEnd(), result.getHealthCareUnitMemberEndDate());
      }

      @Test
      void shouldNotConvertStartDate() {
        final var unit = ParsedSubUnit.builder().build();
        final var result = healthCareUnitConverter.convert(unit);
        assertNull(result.getHealthCareUnitMemberEndDate());
      }
    }

    @Nested
    class ConvertUnitName {


      @Test
      void shouldConvertUnitName() {
        final var unit = ParsedSubUnit.builder().name(VALUE).build();
        final var result = healthCareUnitConverter.convert(unit);
        assertEquals(unit.getName(), result.getHealthCareUnitMemberName());
      }

      @Test
      void shouldNotConvertUnitName() {
        final var unit = ParsedSubUnit.builder().build();
        final var result = healthCareUnitConverter.convert(unit);
        assertNull(result.getHealthCareUnitMemberName());
      }
    }

    @Nested
    class ConvertCareUnitHsaId {

      @Test
      void shouldConvertCareUnitHsaId() {
        final var unit = ParsedSubUnit.builder().parentHsaId(VALUE).build();
        final var result = healthCareUnitConverter.convert(unit);
        assertEquals(unit.getParentHsaId(), result.getHealthCareUnitHsaId());
      }

      @Test
      void shouldNotConvertCareUnitHsaId() {
        final var unit = ParsedSubUnit.builder().build();
        final var result = healthCareUnitConverter.convert(unit);
        assertNull(result.getHealthCareUnitHsaId());
      }
    }
  }
}
