package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataValueConverterCheckboxBooleanTest {

  private static final String ELEMENT_ID = "elementId";
  private static final FieldId FIELD_ID = new FieldId("fieldId");
  private final CertificateDataValueConverterCheckboxBoolean converter = new CertificateDataValueConverterCheckboxBoolean();
  private ElementConfigurationCheckboxBoolean elementConfigurationCheckboxBoolean;

  @BeforeEach
  void setUp() {
    elementConfigurationCheckboxBoolean = ElementConfigurationCheckboxBoolean.builder()
        .id(FIELD_ID)
        .build();
  }

  @Test
  void shouldThrowExceptionIfWrongClassOfValueIfElementValueNotNull() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            ElementConfigurationCheckboxBoolean.builder()
                .id(FIELD_ID)
                .build()
        )
        .build();

    final var elementValueText = ElementValueText.builder().build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(configuration, elementValueText)
    );
  }

  @Test
  void shouldNotThrowExceptionIfWrongClassOfValueIfElementValueIsNull() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            ElementConfigurationCheckboxBoolean.builder()
                .id(FIELD_ID)
                .build()
        )
        .build();

    final var result = converter.convert(configuration, null);
    assertNull(((CertificateDataValueBoolean) result).getSelected());
  }

  @Test
  void shouldThrowExceptionIfWrongClassOfConfig() {
    final var configuration = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(FIELD_ID)
                .build()
        )
        .build();

    final var elementValueDate = ElementValueDate.builder().build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(configuration, elementValueDate)
    );
  }

  @Test
  void shallReturnType() {
    assertEquals(ElementType.CHECKBOX_BOOLEAN, converter.getType());
  }

  @Test
  void shallCreateCertificateDataValueBoolean() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(elementConfigurationCheckboxBoolean)
        .build();

    final var elementValueBoolean = ElementValueBoolean.builder().build();

    final var result = converter.convert(configuration, elementValueBoolean);

    assertInstanceOf(CertificateDataValueBoolean.class, result);
  }

  @Test
  void shallSetIdFromConfiguration() {
    final var configuration = ElementSpecification.builder()
        .configuration(
            elementConfigurationCheckboxBoolean
        )
        .build();

    final var elementValueBoolean = ElementValueBoolean.builder().build();

    final var result = converter.convert(configuration, elementValueBoolean);

    assertEquals(FIELD_ID.value(), ((CertificateDataValueBoolean) result).getId());
  }

  @Test
  void shallSetCorrectSelectedValue() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            elementConfigurationCheckboxBoolean
        )
        .build();

    final var elementValueBoolean = ElementValueBoolean.builder()
        .value(true)
        .build();

    final var result = converter.convert(configuration, elementValueBoolean);

    assertTrue(((CertificateDataValueBoolean) result).getSelected());
  }
}

