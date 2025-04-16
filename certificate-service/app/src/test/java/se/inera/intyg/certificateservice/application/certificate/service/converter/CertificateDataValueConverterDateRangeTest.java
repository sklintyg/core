package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType.DATE_RANGE;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@ExtendWith(MockitoExtension.class)
class CertificateDataValueConverterDateRangeTest {

  @InjectMocks
  private CertificateDataValueConverterDateRange certificateDataValueConverterDateRange;

  @Test
  void shallReturnTypeDateRange() {
    assertEquals(DATE_RANGE, certificateDataValueConverterDateRange.getType());
  }

  @Test
  void shallThrowIfElementValueIsWrongType() {
    final var wrongValue = ElementValueText.builder().build();
    assertThrows(
        IllegalStateException.class,
        () -> certificateDataValueConverterDateRange.convert(null, wrongValue)
    );
  }

  @Test
  void shallThrowIfConfigurationIsWrongType() {
    final var wrongValue = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextArea.builder()
                .build()
        )
        .build();

    assertThrows(
        IllegalStateException.class,
        () -> certificateDataValueConverterDateRange.convert(wrongValue, null)
    );
  }

  @Test
  void shallConvertToCertificateDataValueDateRange() {
    final var expected = CertificateDataValueDateRange.builder()
        .id("id")
        .from(LocalDate.now())
        .to(LocalDate.now().plusDays(1))
        .build();

    final var specification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDateRange.builder()
                .id(new FieldId("id"))
                .build()
        )
        .build();

    final var elementValue = ElementValueDateRange.builder()
        .fromDate(LocalDate.now())
        .toDate(LocalDate.now().plusDays(1))
        .build();

    final var result = certificateDataValueConverterDateRange.convert(specification, elementValue);
    assertEquals(expected, result);
  }

  @Test
  void shallConvertToCertificateDataValueDateRangeWithNullValues() {
    final var expected = CertificateDataValueDateRange.builder()
        .id("id")
        .build();

    final var specification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDateRange.builder()
                .id(new FieldId("id"))
                .build()
        )
        .build();

    final var result = certificateDataValueConverterDateRange.convert(specification, null);
    assertEquals(expected, result);
  }
}