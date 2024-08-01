package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDiagnoses;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfDiagnosisListValueGeneratorTest {

  private static final String CODE_FIELD_ID_1 = "form1[0].#subform[0].flt_txtDiaKod1[0]";
  private static final String CODE_FIELD_ID_2 = "form1[0].#subform[0].flt_txtDiaKod2[0]";
  private static final String CODE_FIELD_ID_3 = "form1[0].#subform[0].flt_txtDiaKod3[0]";
  private static final String DESCRIPTION_FIELD_ID = "form1[0].#subform[0].flt_txtDiagnoser[0]";

  private PdfDiagnosisListValueGenerator pdfDiagnosisListValueGenerator = new PdfDiagnosisListValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(ElementValueDiagnosisList.class, pdfDiagnosisListValueGenerator.getType());
  }

  @Test
  void shouldSetValueIfElementDataWithDiagnoseListValue() {
    final var expected = List.of(
        PdfField.builder()
            .id(DESCRIPTION_FIELD_ID)
            .value("description")
            .build(),
        PdfField.builder()
            .id(CODE_FIELD_ID_1)
            .value("A")
            .build(),
        PdfField.builder()
            .id(CODE_FIELD_ID_2)
            .value("B")
            .build(),
        PdfField.builder()
            .id(CODE_FIELD_ID_3)
            .value("C")
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDiagnoses.builder()
                .diagnoses(
                    Map.of(
                        new FieldId("huvuddiagnos"),
                        PdfConfigurationDiagnosis.builder()
                            .pdfNameFieldId(new PdfFieldId(DESCRIPTION_FIELD_ID))
                            .pdfCodeFieldIds(
                                List.of(
                                    new PdfFieldId(CODE_FIELD_ID_1),
                                    new PdfFieldId(CODE_FIELD_ID_2),
                                    new PdfFieldId(CODE_FIELD_ID_3)
                                )
                            )
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueDiagnosisList.builder()
        .diagnoses(
            List.of(
                ElementValueDiagnosis.builder()
                    .id(new FieldId("huvuddiagnos"))
                    .description("description")
                    .terminology("terminology")
                    .code("ABC")
                    .build()
            )
        )
        .build();

    final var result = pdfDiagnosisListValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }

  @Test
  void shouldThrowExceptionIfDiagnosIdIsMissingFromPdfConfiguration() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDiagnoses.builder()
                .diagnoses(
                    Map.of(
                        new FieldId("huvuddiagnos"),
                        PdfConfigurationDiagnosis.builder()
                            .pdfNameFieldId(new PdfFieldId(DESCRIPTION_FIELD_ID))
                            .pdfCodeFieldIds(
                                List.of(
                                    new PdfFieldId(CODE_FIELD_ID_1),
                                    new PdfFieldId(CODE_FIELD_ID_2),
                                    new PdfFieldId(CODE_FIELD_ID_3)
                                )
                            )
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueDiagnosisList.builder()
        .diagnoses(
            List.of(
                ElementValueDiagnosis.builder()
                    .id(new FieldId("missing field id"))
                    .description("description")
                    .terminology("terminology")
                    .code("ABC")
                    .build()
            )
        )
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> pdfDiagnosisListValueGenerator.generate(elementSpecification, elementValue)
    );
  }

  @Test
  void shallReturnEmptyListIfNoDiagnosis() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDiagnoses.builder()
                .diagnoses(
                    Map.of(
                        new FieldId("huvuddiagnos"),
                        PdfConfigurationDiagnosis.builder()
                            .pdfNameFieldId(new PdfFieldId(DESCRIPTION_FIELD_ID))
                            .pdfCodeFieldIds(
                                List.of(
                                    new PdfFieldId(CODE_FIELD_ID_1),
                                    new PdfFieldId(CODE_FIELD_ID_2),
                                    new PdfFieldId(CODE_FIELD_ID_3)
                                )
                            )
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueDiagnosisList.builder()
        .diagnoses(
            List.of()
        )
        .build();

    assertEquals(Collections.emptyList(),
        pdfDiagnosisListValueGenerator.generate(elementSpecification, elementValue)
    );
  }
}
