package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementDataConverterTest {

  private static final String EXPECTED_ID = "expectedId";
  private static final LocalDate DATE_VALUE = LocalDate.now();
  private static final FieldId DATE_ID = new FieldId("dateId");
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
    final var expectedDate = ElementValueDate.builder()
        .dateId(DATE_ID)
        .date(DATE_VALUE)
        .build();
    final var result = elementDataConverter.convert(EXPECTED_ID,
        certificateDataElementBuilder
            .value(
                CertificateDataValueDate.builder()
                    .id(DATE_ID.value())
                    .date(DATE_VALUE)
                    .build()
            )
            .build()
    );
    final var value = (ElementValueDate) result.value();
    assertEquals(expectedDate, value);
  }
}
