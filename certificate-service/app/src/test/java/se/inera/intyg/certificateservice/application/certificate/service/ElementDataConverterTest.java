package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCategory;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;

class ElementDataConverterTest {

  private static final String EXPECTED_ID = "expectedId";
  private ElementDataConverter elementDataConverter;

  private CertificateDataElement.CertificateDataElementBuilder certificateDataElementBuilder =
      CertificateDataElement.builder()
          .config(
              CertificateDataConfigDate.builder().build()
          );

  @BeforeEach
  void setUp() {
    elementDataConverter = new ElementDataConverter();
  }

  @Test
  void shallReturnNullIfCategory() {
    final var result = elementDataConverter.convert(EXPECTED_ID,
        certificateDataElementBuilder
            .config(
                CertificateDataConfigCategory.builder().build()
            )
            .build()
    );

    assertNull(result);
  }

  @Test
  void shallConvertQuestionId() {
    final var result = elementDataConverter.convert(EXPECTED_ID,
        certificateDataElementBuilder.value(
                CertificateDataValueDate.builder()
                    .date(LocalDate.now())
                    .build()
            )
            .build()
    );

    assertEquals(EXPECTED_ID, result.id().id());
  }

  @Test
  void shallConvertValueDate() {
    final var expectedDate = LocalDate.now();
    final var result = elementDataConverter.convert(EXPECTED_ID,
        certificateDataElementBuilder
            .value(
                CertificateDataValueDate.builder()
                    .date(expectedDate)
                    .build()
            )
            .build()
    );
    final var value = (ElementValueDate) result.value();
    assertEquals(expectedDate, value.date());
  }
}
