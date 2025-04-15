package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType.DATE_RANGE;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@ExtendWith(MockitoExtension.class)
class ElementValueConverterDateRangeTest {

  @InjectMocks
  private ElementValueConverterDateRange elementValueConverterDateRange;

  @Test
  void shallReturnTypeDateRange() {
    assertEquals(DATE_RANGE, elementValueConverterDateRange.getType());
  }

  @Test
  void shallThrowIfTypeIsNotCertificateDataValueDateRange() {
    final var wrongType = CertificateDataValueDate.builder().build();
    final var exception = assertThrows(IllegalStateException.class,
        () -> elementValueConverterDateRange.convert(wrongType)
    );
    assertEquals("Invalid value type. Type was 'DATE'",
        exception.getMessage()
    );
  }

  @Test
  void shallConvertToElementValueDateRange() {
    final var value = CertificateDataValueDateRange.builder()
        .id("id")
        .from(LocalDate.now())
        .to(LocalDate.now().plusDays(1))
        .build();

    final var expectedResult = ElementValueDateRange.builder()
        .id(new FieldId("id"))
        .fromDate(LocalDate.now())
        .toDate(LocalDate.now().plusDays(1))
        .build();

    final var result = elementValueConverterDateRange.convert(value);
    assertEquals(expectedResult, result);
  }
}