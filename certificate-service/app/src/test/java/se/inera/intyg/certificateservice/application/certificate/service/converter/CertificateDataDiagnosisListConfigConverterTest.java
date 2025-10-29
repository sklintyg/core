package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK3226_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk3226CertificateBuilder;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDiagnoses;
import se.inera.intyg.certificateservice.application.certificate.dto.config.DiagnosesListItem;
import se.inera.intyg.certificateservice.application.certificate.dto.config.DiagnosesTerminology;
import se.inera.intyg.certificateservice.application.certificate.dto.config.Message;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisTerminology;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;

class CertificateDataDiagnosisListConfigConverterTest {

  private static final String EXPECTED_CONTENT = "expectedContent";
  private static final String EXPECTED_ID = "expectedId";
  private static final String EXPECTED_LABEL = "expectedLabel";
  private static final String EXPECTED_TEXT = "expectedText";
  private static final String CODE_SYSTEM = "codeSystem";
  private CertificateDataDiagnosisListConfigConverter certificateDataDiagnosisListConfigConverter;

  @BeforeEach
  void setUp() {
    certificateDataDiagnosisListConfigConverter = new CertificateDataDiagnosisListConfigConverter();
  }

  @Test
  void shallReturnType() {
    assertEquals(ElementType.DIAGNOSIS, certificateDataDiagnosisListConfigConverter.getType());
  }

  @Test
  void shallThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDate.builder().build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataDiagnosisListConfigConverter.convert(elementSpecification,
            FK7210_CERTIFICATE)
    );
  }

  @Test
  void shallConvertToCertificateDataConfigDiagnosesWithMessage() {
    final var expectedMessage = Message.builder()
        .content(EXPECTED_CONTENT)
        .level(
            se.inera.intyg.certificateservice.application.certificate.dto.config.MessageLevel.INFO)
        .build();

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .message(
                    ElementMessage.builder()
                        .content(EXPECTED_CONTENT)
                        .level(MessageLevel.INFO)
                        .includedForStatuses(List.of(Status.DRAFT))
                        .build()
                )
                .build()
        )
        .build();

    final var certificate = fk3226CertificateBuilder()
        .status(Status.DRAFT)
        .build();

    final var certificateDataConfig = certificateDataDiagnosisListConfigConverter.convert(
        elementSpecification, certificate);
    assertEquals(expectedMessage, certificateDataConfig.getMessage());
  }

  @Test
  void shallConvertToCertificateDataConfigDiagnosesWithExcludedMessageIfNull() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .build()
        )
        .build();

    final var certificateDataConfig = certificateDataDiagnosisListConfigConverter.convert(
        elementSpecification, FK3226_CERTIFICATE);
    assertNull(certificateDataConfig.getMessage());
  }

  @Test
  void shallConvertToCertificateDataConfigDiagnosesWithExcludedMessageIfCertificateIsSigned() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .message(
                    ElementMessage.builder()
                        .content(EXPECTED_CONTENT)
                        .level(MessageLevel.INFO)
                        .includedForStatuses(List.of(Status.DRAFT))
                        .build()
                )
                .build()
        )
        .build();

    final var certificate = fk3226CertificateBuilder()
        .status(Status.SIGNED)
        .build();

    final var certificateDataConfig = certificateDataDiagnosisListConfigConverter.convert(
        elementSpecification, certificate);
    assertNull(certificateDataConfig.getMessage());
  }

  @Test
  void shallConvertToCertificateDataConfigDiagnosesWithListOfDiagnosesTerminology() {
    final var expectedTerminlogy = List.of(
        DiagnosesTerminology.builder()
            .id(EXPECTED_ID)
            .label(EXPECTED_LABEL)
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .terminology(
                    List.of(
                        new ElementDiagnosisTerminology(EXPECTED_ID, EXPECTED_LABEL, CODE_SYSTEM,
                            List.of())
                    )
                )
                .build()
        )
        .build();

    final var certificateDataConfig = (CertificateDataConfigDiagnoses) certificateDataDiagnosisListConfigConverter.convert(
        elementSpecification, FK3226_CERTIFICATE);
    assertEquals(expectedTerminlogy, certificateDataConfig.getTerminology());
  }

  @Test
  void shallConvertToCertificateDataConfigDiagnosesWithListOfDiagnosesListItem() {
    final var expectedDiagnosesListItem = List.of(
        DiagnosesListItem.builder()
            .id(EXPECTED_ID)
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .list(
                    List.of(
                        new ElementDiagnosisListItem(new FieldId(EXPECTED_ID))
                    )
                )
                .build()
        )
        .build();

    final var certificateDataConfig = (CertificateDataConfigDiagnoses) certificateDataDiagnosisListConfigConverter.convert(
        elementSpecification, FK3226_CERTIFICATE);
    assertEquals(expectedDiagnosesListItem, certificateDataConfig.getList());
  }

  @Test
  void shallConvertToCertificateDataConfigDiagnosesWithText() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .name(EXPECTED_TEXT)
                .build()
        )
        .build();

    final var certificateDataConfig = certificateDataDiagnosisListConfigConverter.convert(
        elementSpecification, FK3226_CERTIFICATE);
    assertEquals(EXPECTED_TEXT, certificateDataConfig.getText());
  }

  @Test
  void shallConvertToCertificateDataConfigDiagnosesWithDescription() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .description(EXPECTED_TEXT)
                .build()
        )
        .build();

    final var certificateDataConfig = certificateDataDiagnosisListConfigConverter.convert(
        elementSpecification, FK3226_CERTIFICATE);
    assertEquals(EXPECTED_TEXT, certificateDataConfig.getDescription());
  }
}
