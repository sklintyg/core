package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataDateConfigConverterTest {

  private static final LocalDate MIN_DATE = LocalDate.now(ZoneId.systemDefault()).minusDays(1);
  private static final LocalDate MAX_DATE = LocalDate.now(ZoneId.systemDefault()).plusDays(5);

  private CertificateDataDateConfigConverter certificateDataDateConfigConverter;

  @BeforeEach
  void setUp() {
    certificateDataDateConfigConverter = new CertificateDataDateConfigConverter();
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextArea.builder().build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataDateConfigConverter.convert(elementSpecification, FK7210_CERTIFICATE)
    );
  }

  @Test
  void shallSetCorrectIdForDate() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDate.builder()
                .id(new FieldId("ID"))
                .build())
        .build();

    final var result = certificateDataDateConfigConverter.convert(elementSpecification,
        FK7210_CERTIFICATE);

    assertEquals("ID", ((CertificateDataConfigDate) result).getId());
  }

  @Test
  void shallSetCorrectTextForDate() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDate.builder()
                .id(new FieldId("ID"))
                .name("NAME")
                .build())
        .build();

    final var result = certificateDataDateConfigConverter.convert(elementSpecification,
        FK7210_CERTIFICATE);

    assertEquals("NAME", result.getText());
  }

  @Test
  void shallSetCorrectMinDateForDate() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDate.builder()
                .id(new FieldId("ID"))
                .min(Period.ofDays(-1))
                .build())
        .build();

    final var result = certificateDataDateConfigConverter.convert(elementSpecification,
        FK7210_CERTIFICATE);

    assertEquals(MIN_DATE, ((CertificateDataConfigDate) result).getMinDate());
  }

  @Test
  void shallSetCorrectMaxDateForDate() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDate.builder()
                .id(new FieldId("ID"))
                .max(Period.ofDays(5))
                .build())
        .build();

    final var result = certificateDataDateConfigConverter.convert(elementSpecification,
        FK7210_CERTIFICATE);

    assertEquals(MAX_DATE, ((CertificateDataConfigDate) result).getMaxDate());
  }
}