package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCategory;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

class CertificateDataConfigConverterTest {

  private static final String CATEGORY_NAME = "Test Category";
  private static final String DATE_ID = "dateId";
  private static final String DATE_NAME = "Test Date";
  private static final LocalDate MIN_DATE = LocalDate.of(2020, 1, 1);
  private static final LocalDate MAX_DATE = LocalDate.of(2023, 12, 31);
  private final CertificateDataConfigConverter converter = new CertificateDataConfigConverter();

  @Nested
  class CategoryTypeConfiguration {

    @Test
    void shallCreateCertificateDataConfigCategory() {
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationCategory.builder()
                  .name(CATEGORY_NAME)
                  .build())
          .build();

      final var result = converter.convert(elementSpecification);

      assertInstanceOf(CertificateDataConfigCategory.class, result);
    }

    @Test
    void shallSetCorrectTextForCategory() {
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationCategory.builder()
                  .name(CATEGORY_NAME)
                  .build())
          .build();

      final var result = converter.convert(elementSpecification);

      assertEquals(CATEGORY_NAME, result.getText());
    }
  }

  @Nested
  class DateTypeConfiguration {

    @Test
    void shallCreateCertificateDataConfigDate() {
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationDate.builder()
                  .build())
          .build();

      final var result = converter.convert(elementSpecification);

      assertInstanceOf(CertificateDataConfigDate.class, result);
    }

    @Test
    void shallSetCorrectIdForDate() {
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationDate.builder()
                  .id(DATE_ID)
                  .build())
          .build();

      final var result = converter.convert(elementSpecification);

      assertEquals(DATE_ID, ((CertificateDataConfigDate) result).getId());
    }

    @Test
    void shallSetCorrectTextForDate() {
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationDate.builder()
                  .name(DATE_NAME)
                  .build())
          .build();

      final var result = converter.convert(elementSpecification);

      assertEquals(DATE_NAME, result.getText());
    }

    @Test
    void shallSetCorrectMinDateForDate() {
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationDate.builder()
                  .minDate(MIN_DATE)
                  .build())
          .build();

      final var result = converter.convert(elementSpecification);

      assertEquals(MIN_DATE, ((CertificateDataConfigDate) result).getMinDate());
    }

    @Test
    void shallSetCorrectMaxDateForDate() {
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationDate.builder()
                  .maxDate(MAX_DATE)
                  .build())
          .build();

      final var result = converter.convert(elementSpecification);

      assertEquals(MAX_DATE, ((CertificateDataConfigDate) result).getMaxDate());
    }
  }
}
