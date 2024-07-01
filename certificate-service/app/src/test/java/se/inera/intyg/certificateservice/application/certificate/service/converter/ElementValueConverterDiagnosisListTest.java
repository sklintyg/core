package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType.DIAGNOSIS_LIST;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDiagnosis;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueConverterDiagnosisListTest {

  private static final String EXPECTED_ID = "expectedId";
  private static final String EXPECTED_CODE = "expectedCode";
  private static final String EXPECTED_DESCRIPTION = "expectedDescription";
  private static final String EXPECTED_TERMINOLOGY = "expectedTerminology";
  private ElementValueConverterDiagnosisList elementValueConverterDiagnosisList;

  @BeforeEach
  void setUp() {
    elementValueConverterDiagnosisList = new ElementValueConverterDiagnosisList();
  }


  @Test
  void shallThrowIfCertificateDataValueTypeIsNotDiagnosisList() {
    final var certificateDataValueCode = CertificateDataValueCode.builder().build();
    assertThrows(IllegalStateException.class,
        () -> elementValueConverterDiagnosisList.convert(certificateDataValueCode));
  }

  @Test
  void shallReturnTypeDiagnosisList() {
    assertEquals(DIAGNOSIS_LIST, elementValueConverterDiagnosisList.getType());
  }

  @Test
  void shallConvertToElementValueDiagnosis() {
    final var expectedValue = ElementValueDiagnosis.builder()
        .id(new FieldId(EXPECTED_ID))
        .code(EXPECTED_CODE)
        .description(EXPECTED_DESCRIPTION)
        .terminology(EXPECTED_TERMINOLOGY)
        .build();

    final var certificateDataValueDiagnosisList = CertificateDataValueDiagnosisList.builder()
        .list(
            List.of(
                CertificateDataValueDiagnosis.builder()
                    .id(EXPECTED_ID)
                    .code(EXPECTED_CODE)
                    .description(EXPECTED_DESCRIPTION)
                    .terminology(EXPECTED_TERMINOLOGY)
                    .build()
            )
        )
        .build();

    final var actualValue = (ElementValueDiagnosisList) elementValueConverterDiagnosisList.convert(
        certificateDataValueDiagnosisList);
    assertEquals(expectedValue, actualValue.diagnoses().get(0));
  }

  @Test
  void shallConvertMultipleValuesToElementValueDiagnosis() {
    final var expectedValue =
        List.of(
            ElementValueDiagnosis.builder()
                .id(new FieldId(EXPECTED_ID))
                .code(EXPECTED_CODE)
                .description(EXPECTED_DESCRIPTION)
                .terminology(EXPECTED_TERMINOLOGY)
                .build(),
            ElementValueDiagnosis.builder()
                .id(new FieldId(EXPECTED_ID))
                .code(EXPECTED_CODE)
                .description(EXPECTED_DESCRIPTION)
                .terminology(EXPECTED_TERMINOLOGY)
                .build()
        );

    final var certificateDataValueDiagnosisList = CertificateDataValueDiagnosisList.builder()
        .list(
            List.of(
                CertificateDataValueDiagnosis.builder()
                    .id(EXPECTED_ID)
                    .code(EXPECTED_CODE)
                    .description(EXPECTED_DESCRIPTION)
                    .terminology(EXPECTED_TERMINOLOGY)
                    .build(),
                CertificateDataValueDiagnosis.builder()
                    .id(EXPECTED_ID)
                    .code(EXPECTED_CODE)
                    .description(EXPECTED_DESCRIPTION)
                    .terminology(EXPECTED_TERMINOLOGY)
                    .build()
            )
        )
        .build();

    final var actualValue = (ElementValueDiagnosisList) elementValueConverterDiagnosisList.convert(
        certificateDataValueDiagnosisList);
    assertEquals(expectedValue, actualValue.diagnoses());
  }
}
