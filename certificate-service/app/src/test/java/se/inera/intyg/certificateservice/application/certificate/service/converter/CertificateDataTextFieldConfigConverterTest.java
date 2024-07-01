package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataTextFieldConfigConverterTest {

  CertificateDataTextFieldConfigConverter certificateDataTextFieldConfigConverter;

  @BeforeEach
  void setUp() {
    certificateDataTextFieldConfigConverter = new CertificateDataTextFieldConfigConverter();
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(ElementConfigurationDate.builder().build())
        .build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataTextFieldConfigConverter.convert(elementSpecification,
            FK7210_CERTIFICATE)
    );
  }

  @Test
  void shouldReturnCategoryType() {
    assertEquals(ElementType.TEXT_FIELD, certificateDataTextFieldConfigConverter.getType());
  }

  @Test
  void shouldReturnConvertedConfig() {
    final var expected = CertificateDataConfigTextField.builder()
        .id("ID")
        .text("NAME")
        .description("DESCRIPTION")
        .header("HEADER")
        .label("LABEL")
        .build();

    final var response = certificateDataTextFieldConfigConverter.convert(
        ElementSpecification.builder()
            .configuration(
                ElementConfigurationTextField.builder()
                    .id(new FieldId("ID"))
                    .name("NAME")
                    .description("DESCRIPTION")
                    .header("HEADER")
                    .label("LABEL")
                    .build()
            ).build(),
        FK7210_CERTIFICATE
    );

    assertEquals(expected, response);
  }
}
