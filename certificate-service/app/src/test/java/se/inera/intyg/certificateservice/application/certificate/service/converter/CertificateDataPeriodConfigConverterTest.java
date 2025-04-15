package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7427_CERTIFICATE;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigPeriod;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationPeriod;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

class CertificateDataPeriodConfigConverterTest {

  private static final LocalDate MIN_DATE = LocalDate.now(ZoneId.systemDefault()).minusDays(1);
  private static final LocalDate MAX_DATE = LocalDate.now(ZoneId.systemDefault()).plusDays(5);

  private static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .id(new ElementId("ID"))
      .configuration(ElementConfigurationPeriod.builder()
          .fromDate(ElementDate.builder()
              .label("LABEL_FROM")
              .minDate(Period.ofDays(-1))
              .maxDate(Period.ofDays(5))
              .build())
          .toDate(ElementDate.builder()
              .label("LABEL_TO")
              .minDate(Period.ofDays(-1))
              .maxDate(Period.ofDays(5))
              .build())
          .build())
      .build();

  private CertificateDataPeriodConfigConverter certificateDataPeriodConfigConverter;

  @BeforeEach
  void setUp() {
    certificateDataPeriodConfigConverter = new CertificateDataPeriodConfigConverter();
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextArea.builder().build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataPeriodConfigConverter.convert(elementSpecification, FK7427_CERTIFICATE)
    );
  }

  @Test
  void shallSetCorrectIdForPeriod() {
    final var result = certificateDataPeriodConfigConverter.convert(ELEMENT_SPECIFICATION,
        FK7427_CERTIFICATE);

    assertEquals("ID", ((CertificateDataConfigPeriod) result).getId());
  }

  @Test
  void shallSetCorrectLabelForFromDate() {
    final var result = certificateDataPeriodConfigConverter.convert(ELEMENT_SPECIFICATION,
        FK7427_CERTIFICATE);

    assertEquals("LABEL_FROM", ((CertificateDataConfigPeriod) result).getFromDate().getLabel());
  }

  @Test
  void shallSetCorrectLabelForToDate() {
    final var result = certificateDataPeriodConfigConverter.convert(ELEMENT_SPECIFICATION,
        FK7427_CERTIFICATE);

    assertEquals("LABEL_TO", ((CertificateDataConfigPeriod) result).getToDate().getLabel());
  }

  @Test
  void shallSetCorrectMinDateForFromDate() {
    final var result = certificateDataPeriodConfigConverter.convert(ELEMENT_SPECIFICATION,
        FK7427_CERTIFICATE);

    assertEquals(MIN_DATE, ((CertificateDataConfigPeriod) result).getFromDate().getMinDate());
  }

  @Test
  void shallSetCorrectMinDateForToDate() {
    final var result = certificateDataPeriodConfigConverter.convert(ELEMENT_SPECIFICATION,
        FK7427_CERTIFICATE);

    assertEquals(MIN_DATE, ((CertificateDataConfigPeriod) result).getToDate().getMinDate());
  }

  @Test
  void shallSetCorrectMaxDateForFromDate() {
    final var result = certificateDataPeriodConfigConverter.convert(ELEMENT_SPECIFICATION,
        FK7427_CERTIFICATE);

    assertEquals(MAX_DATE, ((CertificateDataConfigPeriod) result).getFromDate().getMaxDate());
  }

  @Test
  void shallSetCorrectMaxDateForToDate() {
    final var result = certificateDataPeriodConfigConverter.convert(ELEMENT_SPECIFICATION,
        FK7427_CERTIFICATE);

    assertEquals(MAX_DATE, ((CertificateDataConfigPeriod) result).getToDate().getMaxDate());
  }

}
