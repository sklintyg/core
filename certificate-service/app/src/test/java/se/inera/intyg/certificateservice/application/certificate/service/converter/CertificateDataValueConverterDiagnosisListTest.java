package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDiagnosis;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataValueConverterDiagnosisListTest {

  private static final String ELEMENT_ID = "ELEMENT_ID";
  private static final FieldId FIELD_ID = new FieldId("FIELD_ID");
  private static final String EXPECTED_ID = "expectedId";
  private static final String EXPECTED_TERMINOLOGY = "expectedTerminology";
  private static final String EXPECTED_CODE = "expectedCode";
  private static final String EXPECTED_DESCRIPTION = "expectedDescription";
  private CertificateDataValueConverterDiagnosisList certificateDataValueConverterDiagnosisList;

  @BeforeEach
  void setUp() {
    certificateDataValueConverterDiagnosisList = new CertificateDataValueConverterDiagnosisList();
  }

  @Test
  void shallReturnType() {
    assertEquals(ElementType.DIAGNOSIS, certificateDataValueConverterDiagnosisList.getType());
  }

  @Test
  void shouldThrowExceptionIfWrongClassOfValueIfElementValueNotNull() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            ElementConfigurationDate.builder()
                .id(FIELD_ID)
                .build()
        )
        .build();

    final var elementValueDate = ElementValueText.builder().build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataValueConverterDiagnosisList.convert(configuration, elementValueDate)
    );
  }

  @Test
  void shouldNotThrowExceptionIfWrongClassOfValueIfElementValueIsNull() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            ElementConfigurationDate.builder()
                .id(FIELD_ID)
                .build()
        )
        .build();

    final var result = certificateDataValueConverterDiagnosisList.convert(configuration, null);
    assertTrue(((CertificateDataValueDiagnosisList) result).getList().isEmpty());
  }

  @Test
  void shallReturnListOfCertificateDataValueDiagnosis() {
    final var expectedResult = List.of(
        CertificateDataValueDiagnosis.builder()
            .id(EXPECTED_ID)
            .terminology(EXPECTED_TERMINOLOGY)
            .code(EXPECTED_CODE)
            .description(EXPECTED_DESCRIPTION)
            .build()
    );

    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .build()
        )
        .build();

    final var elementValueDiagnosisList = ElementValueDiagnosisList.builder()
        .diagnoses(
            List.of(
                ElementValueDiagnosis.builder()
                    .id(new FieldId(EXPECTED_ID))
                    .terminology(EXPECTED_TERMINOLOGY)
                    .code(EXPECTED_CODE)
                    .description(EXPECTED_DESCRIPTION)
                    .build()
            )
        )
        .build();

    final var actualResult = (CertificateDataValueDiagnosisList) certificateDataValueConverterDiagnosisList.convert(
        configuration, elementValueDiagnosisList);
    assertEquals(expectedResult, actualResult.getList());
  }
}
