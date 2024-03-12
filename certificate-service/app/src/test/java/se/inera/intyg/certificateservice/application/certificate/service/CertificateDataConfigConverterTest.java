package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCategory;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDate;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateDataConfigConverter;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate.ElementConfigurationDateBuilder;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataConfigConverterTest {

  private static final String CATEGORY_NAME = "Test Category";
  private static final FieldId DATE_ID = new FieldId("dateId");
  private static final String DATE_NAME = "Test Date";
  private static final LocalDate MIN_DATE = LocalDate.now(ZoneId.systemDefault()).minusDays(1);
  private static final LocalDate MAX_DATE = LocalDate.now(ZoneId.systemDefault()).plusDays(5);
  private final CertificateDataConfigConverter converter = new CertificateDataConfigConverter();

  @Test
  void shallThrowIfConfigTypeNotSupported() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationUnitContactInformation.builder()
                .build()
        )
        .build();

    assertThrows(IllegalStateException.class, () -> converter.convert(elementSpecification));
  }

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

    private ElementConfigurationDateBuilder elementConfigurationDateBuilder;

    @BeforeEach
    void setUp() {
      elementConfigurationDateBuilder = ElementConfigurationDate.builder()
          .id(DATE_ID);
    }

    @Test
    void shallCreateCertificateDataConfigDate() {
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              elementConfigurationDateBuilder
                  .build())
          .build();

      final var result = converter.convert(elementSpecification);

      assertInstanceOf(CertificateDataConfigDate.class, result);
    }

    @Test
    void shallSetCorrectIdForDate() {
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              elementConfigurationDateBuilder
                  .id(DATE_ID)
                  .build())
          .build();

      final var result = converter.convert(elementSpecification);

      assertEquals(DATE_ID.value(), ((CertificateDataConfigDate) result).getId());
    }

    @Test
    void shallSetCorrectTextForDate() {
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              elementConfigurationDateBuilder
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
              elementConfigurationDateBuilder
                  .min(Period.ofDays(-1))
                  .build())
          .build();

      final var result = converter.convert(elementSpecification);

      assertEquals(MIN_DATE, ((CertificateDataConfigDate) result).getMinDate());
    }

    @Test
    void shallSetCorrectMaxDateForDate() {
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              elementConfigurationDateBuilder
                  .max(Period.ofDays(5))
                  .build())
          .build();

      final var result = converter.convert(elementSpecification);

      assertEquals(MAX_DATE, ((CertificateDataConfigDate) result).getMaxDate());
    }
  }
}
