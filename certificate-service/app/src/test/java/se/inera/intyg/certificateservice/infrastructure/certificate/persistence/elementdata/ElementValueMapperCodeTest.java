package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueMapperCodeTest {

  private static final String ID = "id";
  private static final String CODE = "code";
  private ElementValueMapperCode elementValueMapperCode;

  @BeforeEach
  void setUp() {
    elementValueMapperCode = new ElementValueMapperCode();
  }

  @Test
  void shallReturnTrueIfClassMappedElementValueCode() {
    assertTrue(elementValueMapperCode.supports(MappedElementValueCode.class));
  }

  @Test
  void shallReturnTrueIfClassElementValueCode() {
    assertTrue(elementValueMapperCode.supports(ElementValueCode.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapperCode.supports(String.class));
  }

  @Test
  void shallMapToDomain() {
    final var expectedValue = ElementValueCode.builder()
        .codeId(new FieldId(ID))
        .code(CODE)
        .build();

    final var elementValueCode = MappedElementValueCode.builder()
        .codeId(ID)
        .code(CODE)
        .build();

    final var actualValue = elementValueMapperCode.toDomain(
        elementValueCode
    );

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallMapToMapped() {
    final var expectedValue = MappedElementValueCode.builder()
        .codeId(ID)
        .code(CODE)
        .build();

    final var elementValueCode = ElementValueCode.builder()
        .codeId(new FieldId(ID))
        .code(CODE)
        .build();

    final var actualValue = elementValueMapperCode.toMapped(
        elementValueCode
    );

    assertEquals(expectedValue, actualValue);
  }
}
