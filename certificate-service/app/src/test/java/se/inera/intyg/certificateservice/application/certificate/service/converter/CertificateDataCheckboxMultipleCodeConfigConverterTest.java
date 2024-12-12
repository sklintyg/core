package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK3226_CERTIFICATE;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxMultipleCode;
import se.inera.intyg.certificateservice.application.certificate.dto.config.Layout;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class CertificateDataCheckboxMultipleCodeConfigConverterTest {

  private CertificateDataCheckboxMultipleCodeConfigConverter certificateDataCheckboxMultipleCodeConfigConverter;

  @BeforeEach
  void setUp() {
    certificateDataCheckboxMultipleCodeConfigConverter = new CertificateDataCheckboxMultipleCodeConfigConverter();
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextArea.builder().build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataCheckboxMultipleCodeConfigConverter.convert(elementSpecification,
            FK3226_CERTIFICATE)
    );
  }

  @Test
  void shallSetCorrectTextForCode() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .name("NAME")
                .list(Collections.emptyList())
                .elementLayout(ElementLayout.ROWS)
                .build())
        .build();

    final var result = certificateDataCheckboxMultipleCodeConfigConverter.convert(
        elementSpecification, FK3226_CERTIFICATE);

    assertEquals("NAME", result.getText());
  }

  @Test
  void shallSetCorrectValuesForList() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .name("NAME")
                .list(
                    List.of(
                        new ElementConfigurationCode(
                            new FieldId("ID_ONE"),
                            "LABEL_ONE",
                            new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME")),
                        new ElementConfigurationCode(
                            new FieldId("ID_TWO"),
                            "LABEL_TWO",
                            new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME"))
                    )
                )
                .elementLayout(ElementLayout.COLUMNS)
                .build()
        )
        .build();

    final CertificateDataConfigCheckboxMultipleCode result = (CertificateDataConfigCheckboxMultipleCode) certificateDataCheckboxMultipleCodeConfigConverter.convert(
        elementSpecification, FK3226_CERTIFICATE);

    assertAll(
        () -> assertEquals("NAME", result.getText()),
        () -> assertEquals("ID_ONE", result.getList().getFirst().getId()),
        () -> assertEquals("LABEL_ONE", result.getList().getFirst().getLabel()),
        () -> assertEquals("ID_TWO", result.getList().get(1).getId()),
        () -> assertEquals("LABEL_TWO", result.getList().get(1).getLabel()),
        () -> assertEquals(Layout.COLUMNS, result.getLayout())
    );
  }

  @Test
  void shallSetCorrectValuesForListWithMessage() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .name("NAME")
                .list(
                    List.of(
                        new ElementConfigurationCode(
                            new FieldId("ID_ONE"),
                            "LABEL_ONE",
                            new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME")),
                        new ElementConfigurationCode(
                            new FieldId("ID_TWO"),
                            "LABEL_TWO",
                            new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME"))
                    )
                )
                .message(
                    ElementMessage.builder()
                        .content("Message")
                        .level(MessageLevel.INFO)
                        .includedForStatuses(List.of(Status.DRAFT))
                        .build()
                )
                .elementLayout(ElementLayout.COLUMNS)
                .build()
        )
        .build();

    final CertificateDataConfigCheckboxMultipleCode result = (CertificateDataConfigCheckboxMultipleCode) certificateDataCheckboxMultipleCodeConfigConverter.convert(
        elementSpecification, FK3226_CERTIFICATE);

    assertAll(
        () -> assertEquals("NAME", result.getText()),
        () -> assertEquals("ID_ONE", result.getList().getFirst().getId()),
        () -> assertEquals("LABEL_ONE", result.getList().getFirst().getLabel()),
        () -> assertEquals("ID_TWO", result.getList().get(1).getId()),
        () -> assertEquals("LABEL_TWO", result.getList().get(1).getLabel()),
        () -> assertEquals(Layout.COLUMNS, result.getLayout()),
        () -> assertEquals("Message", result.getMessage().getContent()),
        () -> assertEquals(
            se.inera.intyg.certificateservice.application.certificate.dto.config.MessageLevel.INFO,
            result.getMessage().getLevel())
    );
  }
}
