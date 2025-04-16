package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7427_CERTIFICATE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataPeriodConfigConverterTest {

  private static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .id(new ElementId("ID"))
      .configuration(ElementConfigurationDateRange.builder()
          .id(new FieldId("ID"))
          .labelTo("LABEL_TO")
          .labelFrom("LABEL_FROM")
          .build())
      .build();

  private CertificateDataDateRangeConfigConverter certificateDataDateRangeConfigConverter;

  @BeforeEach
  void setUp() {
    certificateDataDateRangeConfigConverter = new CertificateDataDateRangeConfigConverter();
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextArea.builder().build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataDateRangeConfigConverter.convert(elementSpecification,
            FK7427_CERTIFICATE)
    );
  }

  @Test
  void shallSetCorrectIdForPeriod() {
    final var result = certificateDataDateRangeConfigConverter.convert(ELEMENT_SPECIFICATION,
        FK7427_CERTIFICATE);

    assertEquals("ID", ((CertificateDataConfigDateRange) result).getId());
  }

  @Test
  void shallSetCorrectLabelFrom() {
    final var result = certificateDataDateRangeConfigConverter.convert(ELEMENT_SPECIFICATION,
        FK7427_CERTIFICATE);

    assertEquals("LABEL_FROM", ((CertificateDataConfigDateRange) result).getFromLabel());
  }

  @Test
  void shallSetCorrectLabelTo() {
    final var result = certificateDataDateRangeConfigConverter.convert(ELEMENT_SPECIFICATION,
        FK7427_CERTIFICATE);

    assertEquals("LABEL_TO", ((CertificateDataConfigDateRange) result).getToLabel());
  }

}
