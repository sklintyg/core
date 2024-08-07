package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationMedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfMedicalInvestigationListValueGeneratorTest {

  private static final String DATE_FIELD_ID = "field1";
  private static final String SOURCE_FIELD_ID = "field2";
  private static final String INVESTIGATION_TYPE_FIELD_ID = "field3";
  private static final Map<String, String> INVESTIGATION_OPTIONS = Map.of(
      CodeSystemKvFkmu0005.DIETIST.code(),
      "Dietist",
      CodeSystemKvFkmu0005.ARBETSTERAPEUT.code(),
      "Arbetsterapeut"
  );
  private static final FieldId FIELD_ID = new FieldId("medicalInvestigation");
  private static final LocalDate DATE_VALUE = LocalDate.now();
  private static final String INVESTIGATION_TYPE_VALUE = CodeSystemKvFkmu0005.ARBETSTERAPEUT.code();
  private static final String SOURCE_VALUE = "source";

  private static final PdfMedicalInvestigationListValueGenerator valueGenerator = new PdfMedicalInvestigationListValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(ElementValueMedicalInvestigationList.class, valueGenerator.getType());
  }

  @Test
  void shouldSetValueIfElementDataWithMedicalInvestigationListValue() {
    final var expected = List.of(
        PdfField.builder()
            .id(DATE_FIELD_ID)
            .value(DATE_VALUE.toString())
            .build(),
        PdfField.builder()
            .id(SOURCE_FIELD_ID)
            .value(SOURCE_VALUE)
            .build(),
        PdfField.builder()
            .id(INVESTIGATION_TYPE_FIELD_ID)
            .value("Arbetsterapeut")
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationMedicalInvestigationList.builder()
                .list(
                    Map.of(
                        FIELD_ID,
                        PdfConfigurationMedicalInvestigation.builder()
                            .investigationPdfOptions(INVESTIGATION_OPTIONS)
                            .investigationPdfFieldId(new PdfFieldId(INVESTIGATION_TYPE_FIELD_ID))
                            .sourceTypePdfFieldId(new PdfFieldId(SOURCE_FIELD_ID))
                            .datePdfFieldId(new PdfFieldId(DATE_FIELD_ID))
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueMedicalInvestigationList.builder()
        .list(
            List.of(
                MedicalInvestigation.builder()
                    .id(FIELD_ID)
                    .date(
                        ElementValueDate.builder()
                            .date(DATE_VALUE)
                            .build()
                    )
                    .informationSource(
                        ElementValueText.builder()
                            .text(SOURCE_VALUE)
                            .build()
                    )
                    .investigationType(
                        ElementValueCode.builder()
                            .code(INVESTIGATION_TYPE_VALUE)
                            .build()
                    )
                    .build()
            )
        ).build();

    final var result = valueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }

  @Test
  void shouldReturnEmptyListIfNoDateIsProvided() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationMedicalInvestigationList.builder()
                .list(
                    Map.of(
                        FIELD_ID,
                        PdfConfigurationMedicalInvestigation.builder()
                            .investigationPdfOptions(INVESTIGATION_OPTIONS)
                            .investigationPdfFieldId(new PdfFieldId(INVESTIGATION_TYPE_FIELD_ID))
                            .sourceTypePdfFieldId(new PdfFieldId(SOURCE_FIELD_ID))
                            .datePdfFieldId(new PdfFieldId(DATE_FIELD_ID))
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueMedicalInvestigationList.builder()
        .list(
            List.of(
                MedicalInvestigation.builder()
                    .id(FIELD_ID)
                    .date(
                        ElementValueDate.builder()
                            .build()
                    )
                    .informationSource(
                        ElementValueText.builder()
                            .build()
                    )
                    .investigationType(
                        ElementValueCode.builder()
                            .build()
                    )
                    .build()
            )
        ).build();

    final var result = valueGenerator.generate(elementSpecification, elementValue);

    assertEquals(Collections.emptyList(), result);
  }
}