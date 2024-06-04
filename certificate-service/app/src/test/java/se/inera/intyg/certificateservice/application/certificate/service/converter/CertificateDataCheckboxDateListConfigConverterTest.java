package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK3226_CERTIFICATE;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@ExtendWith(MockitoExtension.class)
class CertificateDataCheckboxDateListConfigConverterTest {

  @InjectMocks
  private CertificateDataCheckboxDateListConfigConverter certificateDataCheckboxDateListConfigConverter;

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextArea.builder().build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataCheckboxDateListConfigConverter.convert(elementSpecification,
            FK3226_CERTIFICATE)
    );
  }

  @Test
  void shallSetCorrectTextForDate() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationCheckboxMultipleDate.builder()
                .name("NAME")
                .dates(Collections.emptyList())
                .build())
        .build();

    final var result = certificateDataCheckboxDateListConfigConverter.convert(elementSpecification,
        FK3226_CERTIFICATE);

    assertEquals("NAME", result.getText());
  }

  @Test
  void shallSetCorrectValuesForList() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationCheckboxMultipleDate.builder()
                .name("NAME")
                .dates(
                    List.of(
                        new CheckboxDate(new FieldId("ID_ONE"), "LABEL_ONE"),
                        new CheckboxDate(new FieldId("ID_TWO"), "LABEL_TWO")
                    )
                )
                .build())
        .build();

    final var result = certificateDataCheckboxDateListConfigConverter.convert(elementSpecification,
        FK3226_CERTIFICATE);

    assertEquals("ID_ONE",
        ((CertificateDataConfigCheckboxMultipleDate) result).getList().get(0).getId());
    assertEquals("LABEL_ONE",
        ((CertificateDataConfigCheckboxMultipleDate) result).getList().get(0).getLabel());
    assertEquals("ID_TWO",
        ((CertificateDataConfigCheckboxMultipleDate) result).getList().get(1).getId());
    assertEquals("LABEL_TWO",
        ((CertificateDataConfigCheckboxMultipleDate) result).getList().get(1).getLabel());
  }
}
