package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataIntegerConfigConverterTest {

  CertificateDataIntegerConfigConverter converter;

  @BeforeEach
  void setUp() {
    converter = new CertificateDataIntegerConfigConverter();
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(ElementConfigurationDate.builder().build())
        .build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(elementSpecification, FK7210_CERTIFICATE)
    );
  }

  @Test
  void shouldReturnIntegerType() {
    assertEquals(ElementType.INTEGER, converter.getType());
  }

  @Test
  void shouldReturnConvertedConfig() {
    final var expected = CertificateDataConfigInteger.builder()
        .id("ID")
        .description("DESCRIPTION")
        .text("NAME")
        .header("HEADER")
        .min(1)
        .max(10)
        .unitOfMeasurement("st")
        .build();

    final var response = converter.convert(
        ElementSpecification.builder()
            .configuration(
                ElementConfigurationInteger.builder()
                    .id(new FieldId("ID"))
                    .name("NAME")
                    .description("DESCRIPTION")
                    .header("HEADER")
                    .min(1)
                    .max(10)
                    .unitOfMeasurement("st")
                    .build()
            ).build(),
        FK7210_CERTIFICATE
    );

    assertEquals(expected, response);
  }
}

