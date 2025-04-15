package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueMapperDateRangeTest {

  private ElementValueMapperDateRange elementValueMapper;

  @BeforeEach
  void setUp() {
    elementValueMapper = new ElementValueMapperDateRange();
  }

  @Test
  void shallReturnTrueIfClassMappedIsMappedDateRange() {
    assertTrue(elementValueMapper.supports(MappedElementValueDateRange.class));
  }

  @Test
  void shallReturnTrueIfClassElementValueIsDateRange() {
    assertTrue(elementValueMapper.supports(ElementValueDateRange.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapper.supports(String.class));
  }

  @Test
  void shallMapToDomain() {
    final var to = LocalDate.now();
    final var from = LocalDate.now().minusDays(1);
    final var expectedValue = ElementValueDateRange.builder()
        .id(new FieldId("RANGE_ID"))
        .toDate(to)
        .fromDate(from)
        .build();

    final var mappedElementValue = MappedElementValueDateRange.builder()
        .dateId("RANGE_ID")
        .toDate(to)
        .fromDate(from)
        .build();

    final var actualValue = elementValueMapper.toDomain(
        mappedElementValue
    );

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallMapToMapped() {
    final var to = LocalDate.now();
    final var from = LocalDate.now().minusDays(1);
    final var expectedValue = MappedElementValueDateRange.builder()
        .dateId("RANGE_ID")
        .toDate(to)
        .fromDate(from)
        .build();

    final var elementValue = ElementValueDateRange.builder()
        .id(new FieldId("RANGE_ID"))
        .toDate(to)
        .fromDate(from)
        .build();

    final var actualValue = elementValueMapper.toMapped(
        elementValue
    );

    assertEquals(expectedValue, actualValue);
  }
}
