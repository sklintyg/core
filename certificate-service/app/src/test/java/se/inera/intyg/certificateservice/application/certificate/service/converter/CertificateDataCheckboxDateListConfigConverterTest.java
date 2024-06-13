package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK3226_CERTIFICATE;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class CertificateDataCheckboxDateListConfigConverterTest {

  private CertificateDataCheckboxDateListConfigConverter certificateDataCheckboxDateListConfigConverter;

  @BeforeEach
  void setUp() {
    certificateDataCheckboxDateListConfigConverter = new CertificateDataCheckboxDateListConfigConverter();
  }

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
                        CheckboxDate.builder()
                            .id(new FieldId("ID_ONE"))
                            .label("LABEL_ONE")
                            .code(
                                new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME")
                            )
                            .min(Period.ofDays(-1))
                            .max(Period.ofDays(1))
                            .build(),
                        CheckboxDate.builder()
                            .id(new FieldId("ID_TWO"))
                            .label("LABEL_TWO")
                            .code(
                                new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME")
                            )
                            .min(Period.ofDays(-2))
                            .max(Period.ofDays(2))
                            .build()
                    )
                )
                .build()
        )
        .build();

    final CertificateDataConfigCheckboxMultipleDate result = (CertificateDataConfigCheckboxMultipleDate) certificateDataCheckboxDateListConfigConverter.convert(
        elementSpecification, FK3226_CERTIFICATE);

    assertAll(
        () -> assertEquals("ID_ONE", result.getList().get(0).getId()),
        () -> assertEquals("LABEL_ONE", result.getList().get(0).getLabel()),
        () -> assertEquals(LocalDate.now(ZoneId.systemDefault()).minusDays(1),
            result.getList().get(0).getMinDate()),
        () -> assertEquals(LocalDate.now(ZoneId.systemDefault()).plusDays(1),
            result.getList().get(0).getMaxDate()),
        () -> assertEquals("ID_TWO", result.getList().get(1).getId()),
        () -> assertEquals("LABEL_TWO", result.getList().get(1).getLabel()),
        () -> assertEquals(LocalDate.now(ZoneId.systemDefault()).minusDays(2),
            result.getList().get(1).getMinDate()),
        () -> assertEquals(LocalDate.now(ZoneId.systemDefault()).plusDays(2),
            result.getList().get(1).getMaxDate())
    );
  }
}
