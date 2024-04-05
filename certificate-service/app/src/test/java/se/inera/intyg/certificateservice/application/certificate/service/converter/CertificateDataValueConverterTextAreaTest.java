package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataValueConverterTextAreaTest {

  private static final String ELEMENT_ID = "elementId";
  private static final String TEST_TEXT = "testText";
  private static final FieldId FIELD_ID = new FieldId("fieldId");
  private static final String NAME = "NAME";
  private final CertificateDataValueConverterTextArea converter =
      new CertificateDataValueConverterTextArea();
  private final ElementConfigurationTextArea elementConfigurationTextArea =
      ElementConfigurationTextArea.builder()
          .id(FIELD_ID)
          .name(NAME)
          .build();

  @Test
  void shallReturnType() {
    assertEquals(ElementType.TEXT_AREA, converter.getType());
  }

  @Test
  void shallCreateCertificateDataTextValue() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(elementConfigurationTextArea)
        .build();

    final var elementValueText = ElementValueText.builder().build();

    final var result = converter.convert(configuration, elementValueText);

    assertInstanceOf(CertificateDataValueText.class, result);
  }

  @Test
  void shallSetIdFromConfigurationTextValue() {
    final var configuration = ElementSpecification.builder()
        .configuration(
            elementConfigurationTextArea
        )
        .build();

    final var elementValueText = ElementValueText.builder().build();

    final var result = converter.convert(configuration, elementValueText);

    assertEquals(FIELD_ID.value(), ((CertificateDataValueText) result).getId());
  }

  @Test
  void shallSetCorrectTextForTextValue() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            elementConfigurationTextArea
        )
        .build();

    final var elementValueText = ElementValueText.builder()
        .text(TEST_TEXT)
        .build();

    final var result = converter.convert(configuration, elementValueText);

    assertEquals(TEST_TEXT, ((CertificateDataValueText) result).getText());
  }

  @Test
  void shallSetValueToNull() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            elementConfigurationTextArea
        )
        .build();

    final var elementValueText = ElementValueText.builder().build();

    final var result = converter.convert(configuration, elementValueText);

    assertNull(((CertificateDataValueText) result).getText(),
        "If no value is provided value should be null");
  }
}