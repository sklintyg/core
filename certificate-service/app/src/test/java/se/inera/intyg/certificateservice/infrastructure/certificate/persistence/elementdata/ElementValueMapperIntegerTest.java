package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueMapperIntegerTest {

  private static final String ID = "id";
  private ElementValueMapperInteger elementValueMapperInteger;

  @BeforeEach
  void setUp() {
    elementValueMapperInteger = new ElementValueMapperInteger();
  }

  @Test
  void shallReturnTrueIfClassMappedElementValueInteger() {
    assertTrue(elementValueMapperInteger.supports(MappedElementValueInteger.class));
  }

  @Test
  void shallReturnTrueIfClassElementValueInteger() {
    assertTrue(elementValueMapperInteger.supports(ElementValueInteger.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapperInteger.supports(String.class));
  }

  @Test
  void shallMapToDomain() {
    final var expectedValue = ElementValueInteger.builder()
        .integerId(new FieldId(ID))
        .value(42)
        .build();

    final var mappedValue = MappedElementValueInteger.builder()
        .integerId(ID)
        .value(42)
        .build();

    final var actualValue = elementValueMapperInteger.toDomain(
        mappedValue
    );

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallMapToMapped() {
    final var expectedValue = MappedElementValueInteger.builder()
        .integerId(ID)
        .value(42)
        .build();

    final var elementValue = ElementValueInteger.builder()
        .integerId(new FieldId(ID))
        .value(42)
        .build();

    final var actualValue = elementValueMapperInteger.toMapped(
        elementValue
    );

    assertEquals(expectedValue, actualValue);
  }
}

