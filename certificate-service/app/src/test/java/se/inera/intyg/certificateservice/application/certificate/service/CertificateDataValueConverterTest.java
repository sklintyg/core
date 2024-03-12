package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateDataValueConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate.ElementConfigurationDateBuilder;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataValueConverterTest {

  private static final String ELEMENT_ID = "elementId";
  private static final LocalDate TEST_DATE = LocalDate.of(2021, 5, 20);
  private static final FieldId FIELD_ID = new FieldId("fieldId");
  private final CertificateDataValueConverter converter = new CertificateDataValueConverter();

  @Test
  void shallReturnNullIfTypeCategory() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            ElementConfigurationCategory.builder()
                .build()
        )
        .build();

    final var result = converter.convert(configuration, null);

    assertNull(result);
  }

  @Test
  void shallThrowIfConfigNotSupported() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            ElementConfigurationUnitContactInformation.builder()
                .build()
        )
        .build();

    assertThrows(IllegalStateException.class, () -> converter.convert(configuration, null));
  }

  @Nested
  class DateTypeValue {

    private ElementConfigurationDateBuilder elementConfigurationDateBuilder;

    @BeforeEach
    void setUp() {
      elementConfigurationDateBuilder = ElementConfigurationDate.builder()
          .id(FIELD_ID);
    }

    @Test
    void shallCreateCertificateDataValueDate() {
      final var configuration = ElementSpecification.builder()
          .id(new ElementId(ELEMENT_ID))
          .configuration(
              elementConfigurationDateBuilder
                  .build()
          )
          .build();

      final var elementValueDate = ElementValueDate.builder().build();

      final var result = converter.convert(configuration, elementValueDate);

      assertInstanceOf(CertificateDataValueDate.class, result);
    }

    @Test
    void shallSetIdFromConfigurationDateValue() {
      final var configuration = ElementSpecification.builder()
          .configuration(
              elementConfigurationDateBuilder.build()
          )
          .build();

      final var elementValueDate = ElementValueDate.builder().build();

      final var result = converter.convert(configuration, elementValueDate);

      assertEquals(FIELD_ID.value(), ((CertificateDataValueDate) result).getId());
    }

    @Test
    void shallSetCorrectDateForDateValue() {
      final var configuration = ElementSpecification.builder()
          .id(new ElementId(ELEMENT_ID))
          .configuration(
              elementConfigurationDateBuilder.build()
          )
          .build();

      final var elementValueDate = ElementValueDate.builder()
          .date(TEST_DATE)
          .build();

      final var result = converter.convert(configuration, elementValueDate);

      assertEquals(TEST_DATE, ((CertificateDataValueDate) result).getDate());
    }

    @Test
    void shallSetValueToNull() {
      final var configuration = ElementSpecification.builder()
          .id(new ElementId(ELEMENT_ID))
          .configuration(
              elementConfigurationDateBuilder.build()
          )
          .build();

      final var elementValueDate = ElementValueDate.builder()
          .build();

      final var result = converter.convert(configuration, elementValueDate);

      assertNull(((CertificateDataValueDate) result).getDate(),
          "If no value is provided value should be null");
    }
  }
}
