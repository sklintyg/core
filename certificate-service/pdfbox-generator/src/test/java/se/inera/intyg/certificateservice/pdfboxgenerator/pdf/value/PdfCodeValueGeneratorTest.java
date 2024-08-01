package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfCodeValueGeneratorTest {

  private static final String PDF_FIELD_ID = "form1[0].#subform[1].ksr_AkutLivshotande[0]";
  private static final FieldId CODE_FIELD_ID = new FieldId("AKUT_LIVSHOTANDE");
  private static final String CHECKBOX_VALUE = "1";

  private static final PdfCodeValueGenerator pdfCodeValueGenerator = new PdfCodeValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(ElementValueCode.class, pdfCodeValueGenerator.getType());
  }

  @Test
  void shouldSetValueIfElementDataWithCodeValue() {
    final var expected = List.of(
        PdfField.builder()
            .id(PDF_FIELD_ID)
            .value(CHECKBOX_VALUE)
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationCode.builder()
                .codes(
                    Map.of(CODE_FIELD_ID, new PdfFieldId(PDF_FIELD_ID))
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueCode.builder()
        .codeId(CODE_FIELD_ID)
        .code(CODE_FIELD_ID.value())
        .build();

    final var result = pdfCodeValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }

  @Test
  void shouldReturnEmptyListIfElementDataWithoutCodeValue() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationCode.builder()
                .codes(
                    Map.of(CODE_FIELD_ID, new PdfFieldId(PDF_FIELD_ID))
                )
                .build()
        )
        .build();

    final var result = pdfCodeValueGenerator.generate(elementSpecification, null);

    assertEquals(Collections.emptyList(), result);
  }

  @Test
  void shouldThrowExceptionIfCodeIdIsMissingFromPdfConfiguration() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationCode.builder()
                .codes(
                    Map.of(CODE_FIELD_ID, new PdfFieldId(PDF_FIELD_ID))
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueCode.builder()
        .codeId(new FieldId("missing codeId"))
        .code(CODE_FIELD_ID.value())
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> pdfCodeValueGenerator.generate(elementSpecification, elementValue)
    );
  }
}
