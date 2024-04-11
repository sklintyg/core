package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueMapperDateTest {

  private static final String ID = "id";
  private static final LocalDate NOW = LocalDate.now();
  private ElementValueMapperDate elementValueMapperDate;

  @BeforeEach
  void setUp() {
    elementValueMapperDate = new ElementValueMapperDate();
  }

  @Test
  void shallReturnTrueIfClassMappedElementValueIssuingUnit() {
    assertTrue(elementValueMapperDate.supports(MappedElementValueDate.class));
  }

  @Test
  void shallReturnTrueIfClassElementValueUnitContactInformation() {
    assertTrue(elementValueMapperDate.supports(ElementValueDate.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapperDate.supports(String.class));
  }

  @Test
  void shallMapToDomain() {
    final var expectedValue = ElementValueDate.builder()
        .dateId(new FieldId(ID))
        .date(NOW)
        .build();

    final var mappedElementValueDate = MappedElementValueDate.builder()
        .dateId(ID)
        .date(NOW)
        .build();

    final var actualValue = elementValueMapperDate.toDomain(
        mappedElementValueDate
    );

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallMapToMapped() {
    final var expectedValue = MappedElementValueDate.builder()
        .dateId(ID)
        .date(NOW)
        .build();

    final var elementValueDate = ElementValueDate.builder()
        .dateId(new FieldId(ID))
        .date(NOW)
        .build();

    final var actualValue = elementValueMapperDate.toMapped(
        elementValueDate
    );

    assertEquals(expectedValue, actualValue);
  }
}
