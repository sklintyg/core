package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueMapperTextTest {

  private static final String ID = "id";
  private static final String TEXT = "text";
  private ElementValueMapperText elementValueMapperText;

  @BeforeEach
  void setUp() {
    elementValueMapperText = new ElementValueMapperText();
  }

  @Test
  void shallReturnTrueIfClassMappedElementValueText() {
    assertTrue(elementValueMapperText.supports(MappedElementValueText.class));
  }

  @Test
  void shallReturnTrueIfClassElementValueText() {
    assertTrue(elementValueMapperText.supports(ElementValueText.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapperText.supports(String.class));
  }

  @Test
  void shallMapToDomain() {
    final var expectedValue = ElementValueText.builder()
        .textId(new FieldId(ID))
        .text(TEXT)
        .build();

    final var mappedElementValueText = MappedElementValueText.builder()
        .textId(ID)
        .text(TEXT)
        .build();

    final var actualValue = elementValueMapperText.toDomain(
        mappedElementValueText
    );

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallMapToMapped() {
    final var expectedValue = MappedElementValueText.builder()
        .textId(ID)
        .text(TEXT)
        .build();

    final var elementValueText = ElementValueText.builder()
        .textId(new FieldId(ID))
        .text(TEXT)
        .build();

    final var actualValue = elementValueMapperText.toMapped(
        elementValueText
    );

    assertEquals(expectedValue, actualValue);
  }
}
