package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueMapperIcfTest {

  private static final String ID = "id";
  private static final String TEXT = "text";
  private static final List<String> VALUES = List.of("code1", "code2");
  private ElementValueMapperIcf elementValueMapperIcf;

  @BeforeEach
  void setUp() {
    elementValueMapperIcf = new ElementValueMapperIcf();
  }

  @Test
  void shallReturnTrueIfClassMappedElementValueIcf() {
    assertTrue(elementValueMapperIcf.supports(MappedElementValueIcf.class));
  }

  @Test
  void shallReturnTrueIfClassElementValueIcf() {
    assertTrue(elementValueMapperIcf.supports(ElementValueIcf.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapperIcf.supports(String.class));
  }

  @Test
  void shallMapToDomain() {
    final var expectedValue = ElementValueIcf.builder()
        .id(new FieldId(ID))
        .text(TEXT)
        .icfCodes(VALUES)
        .build();

    final var mappedElementValueIcf = MappedElementValueIcf.builder()
        .id(ID)
        .text(TEXT)
        .icfCodes(VALUES)
        .build();

    final var actualValue = elementValueMapperIcf.toDomain(
        mappedElementValueIcf
    );

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallMapToMapped() {
    final var expectedValue = MappedElementValueIcf.builder()
        .id(ID)
        .text(TEXT)
        .icfCodes(VALUES)
        .build();

    final var elementValueIcf = ElementValueIcf.builder()
        .id(new FieldId(ID))
        .text(TEXT)
        .icfCodes(VALUES)
        .build();

    final var actualValue = elementValueMapperIcf.toMapped(
        elementValueIcf
    );

    assertEquals(expectedValue, actualValue);
  }

}