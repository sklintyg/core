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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataValueConverterRadioBooleanTest {

  private static final String ELEMENT_ID = "elementId";
  private static final FieldId FIELD_ID = new FieldId("fieldId");
  private final CertificateDataValueConverterRadioBoolean converter = new CertificateDataValueConverterRadioBoolean();
  private ElementConfigurationRadioBoolean elementConfigurationRadioBoolean;

  @BeforeEach
  void setUp() {
    elementConfigurationRadioBoolean = ElementConfigurationRadioBoolean.builder()
        .id(FIELD_ID)
        .build();
  }

  @Test
  void shouldThrowExceptionIfWrongClassOfValueIfElementValueNotNull() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(FIELD_ID)
                .build()
        )
        .build();

    final var elementValueDate = ElementValueText.builder().build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(configuration, elementValueDate)
    );
  }

  @Test
  void shouldNotThrowExceptionIfWrongClassOfValueIfElementValueIsNull() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            ElementConfigurationRadioBoolean.builder()
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
    assertEquals(ElementType.RADIO_BOOLEAN, converter.getType());
  }

  @Test
  void shallCreateCertificateDataValueBoolean() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(elementConfigurationRadioBoolean)
        .build();

    final var elementValueBoolean = ElementValueBoolean.builder().build();

    final var result = converter.convert(configuration, elementValueBoolean);

    assertInstanceOf(CertificateDataValueBoolean.class, result);
  }

  @Test
  void shallSetIdFromConfiguration() {
    final var configuration = ElementSpecification.builder()
        .configuration(
            elementConfigurationRadioBoolean
        )
        .build();

    final var elementValueDate = ElementValueBoolean.builder().build();

    final var result = converter.convert(configuration, elementValueDate);

    assertEquals(FIELD_ID.value(), ((CertificateDataValueBoolean) result).getId());
  }

  @Test
  void shallSetCorrectSelectedValue() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            elementConfigurationRadioBoolean
        )
        .build();

    final var elementValueDate = ElementValueBoolean.builder()
        .value(true)
        .build();

    final var result = converter.convert(configuration, elementValueDate);

    assertTrue(((CertificateDataValueBoolean) result).getSelected());
  }
}
