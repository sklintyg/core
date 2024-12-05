package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataRadioBooleanConfigConverterTest {

  private CertificateDataRadioBooleanConfigConverter certificateDataRadioBooleanConfigConverter;

  @BeforeEach
  void setUp() {
    certificateDataRadioBooleanConfigConverter = new CertificateDataRadioBooleanConfigConverter();
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextArea.builder().build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataRadioBooleanConfigConverter.convert(elementSpecification,
            FK7210_CERTIFICATE)
    );
  }

  @Test
  void shallSetCorrectId() {
    final var elementSpecification = ElementSpecification.builder()
        .id(new ElementId("ID"))
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(new FieldId("ID"))
                .build())
        .build();

    final var result = certificateDataRadioBooleanConfigConverter.convert(elementSpecification,
        FK7210_CERTIFICATE);

    assertEquals("ID", ((CertificateDataConfigRadioBoolean) result).getId());
  }

  @Test
  void shallSetCorrectText() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(new FieldId("ID"))
                .name("NAME")
                .build())
        .build();

    final var result = certificateDataRadioBooleanConfigConverter.convert(elementSpecification,
        FK7210_CERTIFICATE);

    assertEquals("NAME", result.getText());
  }

  @Test
  void shallSetCorrectDescription() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(new FieldId("ID"))
                .name("NAME")
                .description("DESCRIPTION")
                .build())
        .build();

    final var result = certificateDataRadioBooleanConfigConverter.convert(elementSpecification,
        FK7210_CERTIFICATE);

    assertEquals("DESCRIPTION", result.getDescription());
  }

  @Test
  void shallSetCorrectSelectedText() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(new FieldId("ID"))
                .selectedText("SELECTED_TEXT")
                .build())
        .build();

    final var result = certificateDataRadioBooleanConfigConverter.convert(elementSpecification,
        FK7210_CERTIFICATE);

    assertEquals("SELECTED_TEXT", ((CertificateDataConfigRadioBoolean) result).getSelectedText());
  }

  @Test
  void shallSetCorrectUnselectedText() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(new FieldId("ID"))
                .unselectedText("UNSELECTED_TEXT")
                .build())
        .build();

    final var result = certificateDataRadioBooleanConfigConverter.convert(elementSpecification,
        FK7210_CERTIFICATE);

    assertEquals("UNSELECTED_TEXT",
        ((CertificateDataConfigRadioBoolean) result).getUnselectedText());
  }
}
