package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK3226_CERTIFICATE;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigRadioMultipleCode;
import se.inera.intyg.certificateservice.application.certificate.dto.config.Layout;
import se.inera.intyg.certificateservice.application.certificate.dto.config.RadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class CertificateDataRadioMultipleCodeConfigConverterTest {


  private static final String ID_1 = "ID1";
  private static final String LABEL_1 = "LABEL1";
  private static final String ID_2 = "ID2";
  private static final String LABEL_2 = "LABEL2";
  private static final String NAME = "NAME";
  private CertificateDataRadioMultipleCodeConfigConverter certificateDataRadioMultipleCodeConfigConverter;

  @BeforeEach
  void setUp() {
    certificateDataRadioMultipleCodeConfigConverter = new CertificateDataRadioMultipleCodeConfigConverter();
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextArea.builder().build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataRadioMultipleCodeConfigConverter.convert(elementSpecification,
            FK3226_CERTIFICATE)
    );
  }

  @Test
  void shallIncludeText() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationRadioMultipleCode.builder()
                .name(NAME)
                .elementLayout(ElementLayout.ROWS)
                .list(Collections.emptyList())
                .build())
        .build();

    final var result = certificateDataRadioMultipleCodeConfigConverter.convert(elementSpecification,
        FK3226_CERTIFICATE);

    assertEquals(NAME, result.getText());
  }

  @Test
  void shallIncludeLayout() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationRadioMultipleCode.builder()
                .elementLayout(ElementLayout.ROWS)
                .list(Collections.emptyList())
                .build())
        .build();

    final var result = certificateDataRadioMultipleCodeConfigConverter.convert(elementSpecification,
        FK3226_CERTIFICATE);

    assertEquals(Layout.ROWS, ((CertificateDataConfigRadioMultipleCode) result).getLayout());
  }

  @Test
  void shallIncludeListOfRadioMultipleCode() {
    final var expectedCodes = List.of(
        RadioMultipleCode.builder()
            .id(ID_1)
            .label(LABEL_1)
            .build(),
        RadioMultipleCode.builder()
            .id(ID_2)
            .label(LABEL_2)
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationRadioMultipleCode.builder()
                .elementLayout(ElementLayout.ROWS)
                .list(
                    List.of(
                        new ElementConfigurationCode(new FieldId(ID_1), LABEL_1,
                            new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME")),
                        new ElementConfigurationCode(new FieldId(ID_2), LABEL_2,
                            new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME"))
                    )
                )
                .build())
        .build();

    final var result = certificateDataRadioMultipleCodeConfigConverter.convert(elementSpecification,
        FK3226_CERTIFICATE);

    assertEquals(expectedCodes, ((CertificateDataConfigRadioMultipleCode) result).getList());
  }
}
