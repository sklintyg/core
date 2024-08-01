package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfTextValueGeneratorTest {

  private static final String FIELD_ID = "form1[0].#subform[0].flt_txtDiagnos[0]";
  private static final String VALUE = "Diagnos är okänd men symtomen är hosta.";

  private static final PdfTextValueGenerator pdfTextValueGenerator = new PdfTextValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(ElementValueText.class, pdfTextValueGenerator.getType());
  }

  @Test
  void shouldSetValueIfElementDataWithTextValue() {
    final var expected = List.of(
        PdfField.builder()
            .id(FIELD_ID)
            .value(VALUE)
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .build()
        )
        .build();

    final var elementValue = ElementValueText.builder()
        .text(VALUE)
        .build();

    final var result = pdfTextValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }

  @Test
  void shouldReturnEmptyListIfElementDataWithoutTextValue() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .build()
        )
        .build();

    final var elementValue = ElementValueText.builder()
        .build();

    final var result = pdfTextValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(Collections.emptyList(), result);
  }
}
