package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueMapperCodeBooleanTest {

  private static final String ID = "id";
  private ElementValueMapperBoolean elementValueMapperBoolean;

  @BeforeEach
  void setUp() {
    elementValueMapperBoolean = new ElementValueMapperBoolean();
  }

  @Test
  void shallReturnTrueIfClassMappedElementValueBoolean() {
    assertTrue(elementValueMapperBoolean.supports(MappedElementValueBoolean.class));
  }

  @Test
  void shallReturnTrueIfClassElementValueBoolean() {
    assertTrue(elementValueMapperBoolean.supports(ElementValueBoolean.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapperBoolean.supports(String.class));
  }

  @Test
  void shallMapToDomain() {
    final var expectedValue = ElementValueBoolean.builder()
        .booleanId(new FieldId(ID))
        .value(true)
        .build();

    final var elementValueCode = MappedElementValueBoolean.builder()
        .booleanId(ID)
        .value(true)
        .build();

    final var actualValue = elementValueMapperBoolean.toDomain(
        elementValueCode
    );

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallMapToMapped() {
    final var expectedValue = MappedElementValueBoolean.builder()
        .booleanId(ID)
        .value(true)
        .build();

    final var elementValueCode = ElementValueBoolean.builder()
        .booleanId(new FieldId(ID))
        .value(true)
        .build();

    final var actualValue = elementValueMapperBoolean.toMapped(
        elementValueCode
    );

    assertEquals(expectedValue, actualValue);
  }
}
