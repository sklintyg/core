package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfIcfValueGeneratorTest {

  private static final String FIELD_ID = "form1[0].#subform[0].flt_txtIcf[0]";
  private static final String VALUE = "ICF beskrivning";
  private static final String VALUE_WITH_CODES = """
      ICF_CODE_1 - ICF_CODE_2
      
      ICF beskrivning""";
  private static final PdfIcfValueGenerator pdfIcfValueGenerator = new PdfIcfValueGenerator();
  private static final String QUESTION_NAME = "ICF fråga";
  private static final PdfFieldId OVERFLOW_SHEET_ID = new PdfFieldId("OVERFLOW_ID");

  @Test
  void shouldReturnType() {
    assertEquals(ElementValueIcf.class, pdfIcfValueGenerator.getType());
  }

  @Test
  void shouldSetValueIfElementDataWithIcfText() {
    final var expected = List.of(
        PdfField.builder()
            .id(FIELD_ID)
            .value(VALUE)
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationIcf.builder()
                .name(QUESTION_NAME)
                .build()
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .build()
        )
        .build();

    final var elementValue = ElementValueIcf.builder()
        .text(VALUE)
        .build();

    final var result = pdfIcfValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }

  @Test
  void shouldSetValueIfElementDataWithIcfTextAndCodes() {
    final var expected = List.of(
        PdfField.builder()
            .id(FIELD_ID)
            .value(VALUE_WITH_CODES)
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationIcf.builder()
                .name(QUESTION_NAME)
                .build()
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .build()
        )
        .build();

    final var elementValue = ElementValueIcf.builder()
        .text(VALUE)
        .icfCodes(List.of("ICF_CODE_1", "ICF_CODE_2"))
        .build();

    final var result = pdfIcfValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }

  @Test
  void shouldReturnEmptyListIfElementDataWithoutIcfTextOrCodes() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .build()
        )
        .build();

    final var elementValue = ElementValueIcf.builder().build();

    final var result = pdfIcfValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(Collections.emptyList(), result);
  }

  @Test
  void shouldSplitValueIfOverMaxLengthWithOverflowSheet() {
    final var startValue = "ICF ...";
    final var endValue = "... beskrivning";
    final var expected = List.of(
        PdfField.builder()
            .id(FIELD_ID)
            .value(startValue)
            .build(),
        PdfField.builder()
            .id(OVERFLOW_SHEET_ID.id())
            .value(QUESTION_NAME)
            .append(true)
            .build(),
        PdfField.builder()
            .id(OVERFLOW_SHEET_ID.id())
            .value(endValue + "\n")
            .append(true)
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationIcf.builder()
                .name(QUESTION_NAME)
                .build()
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .maxLength(7)
                .overflowSheetFieldId(OVERFLOW_SHEET_ID)
                .build()
        )
        .build();

    final var elementValue = ElementValueIcf.builder()
        .text(VALUE)
        .build();

    final var result = pdfIcfValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }
}

