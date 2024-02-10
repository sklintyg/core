package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CertificateDataValueConverterTest {

  private static final String ELEMENT_ID = "elementId";
  private static final LocalDate TEST_DATE = LocalDate.of(2021, 5, 20);
  private final CertificateDataValueConverter converter = new CertificateDataValueConverter();

  @Test
  void shallReturnNullForNullElementValue() {
    final var elementValue = ElementData.builder()
        .value(null)
        .build();

    final var result = converter.convert(elementValue);

    assertNull(result);
  }

  @Nested
  class DateTypeValue {

    @Test
    void shallCreateCertificateDataValueDate() {
      final var elementValueDate = ElementValueDate.builder()
          .date(TEST_DATE)
          .build();
      final var elementData = ElementData.builder()
          .id(new ElementId(ELEMENT_ID))
          .value(elementValueDate)
          .build();

      final var result = converter.convert(elementData);

      assertInstanceOf(CertificateDataValueDate.class, result);
    }

    @Test
    void shallSetCorrectIdForDateValue() {
      final var elementValueDate = ElementValueDate.builder()
          .date(TEST_DATE)
          .build();
      final var elementData = ElementData.builder()
          .id(new ElementId(ELEMENT_ID))
          .value(elementValueDate)
          .build();

      final var result = converter.convert(elementData);

      assertEquals(ELEMENT_ID, ((CertificateDataValueDate) result).getId());
    }

    @Test
    void shallSetCorrectDateForDateValue() {
      final var elementValueDate = ElementValueDate.builder()
          .date(TEST_DATE)
          .build();
      final var elementData = ElementData.builder()
          .id(new ElementId(ELEMENT_ID))
          .value(elementValueDate)
          .build();

      final var result = converter.convert(elementData);

      assertEquals(TEST_DATE, ((CertificateDataValueDate) result).getDate());
    }
  }
}
