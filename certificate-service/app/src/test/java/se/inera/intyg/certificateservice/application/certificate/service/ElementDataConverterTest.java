package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.application.certificate.service.converter.ElementDataConverter;
import se.inera.intyg.certificateservice.application.certificate.service.converter.ElementValueConverterDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;

@ExtendWith(MockitoExtension.class)
class ElementDataConverterTest {

  private static final String EXPECTED_ID = "expectedId";
  @Mock
  private ElementValueConverterDate elementValueConverterDate;

  private ElementDataConverter elementDataConverter;

  @BeforeEach
  void setUp() {
    elementDataConverter = new ElementDataConverter(List.of(elementValueConverterDate));
  }

  private final CertificateDataElement.CertificateDataElementBuilder certificateDataElementBuilder =
      CertificateDataElement.builder()
          .config(
              CertificateDataConfigDate.builder().build()
          )
          .value(
              CertificateDataValueDate.builder()
                  .date(LocalDate.now())
                  .build()
          );

  @Test
  void shallConvertQuestionId() {
    final var element = certificateDataElementBuilder.build();

    doReturn(CertificateDataValueType.DATE).when(elementValueConverterDate).getType();
    doReturn(ElementValueDate.builder().build()).when(elementValueConverterDate)
        .convert(element.getValue());

    final var result = elementDataConverter.convert(
        EXPECTED_ID,
        element
    );

    assertEquals(EXPECTED_ID, result.id().id());
  }

  @Test
  void shallConvertValue() {
    final var expectedValue = ElementValueDate.builder()
        .date(LocalDate.now())
        .build();

    final var element = certificateDataElementBuilder.value(
            CertificateDataValueDate.builder()
                .date(LocalDate.now())
                .build()
        )
        .build();

    doReturn(CertificateDataValueType.DATE).when(elementValueConverterDate).getType();
    doReturn(expectedValue).when(elementValueConverterDate)
        .convert(element.getValue());

    final var result = elementDataConverter.convert(
        EXPECTED_ID,
        element
    );

    final var actualValue = (ElementValueDate) result.value();

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallThrowIfConverterNotFound() {
    final var element = certificateDataElementBuilder.build();
    doReturn(CertificateDataValueType.DATE).when(elementValueConverterDate).getType();
    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> elementDataConverter.convert(
            EXPECTED_ID,
            element
        )
    );
    assertTrue(illegalStateException.getMessage().contains("Could not find converter for type"));
  }
}
