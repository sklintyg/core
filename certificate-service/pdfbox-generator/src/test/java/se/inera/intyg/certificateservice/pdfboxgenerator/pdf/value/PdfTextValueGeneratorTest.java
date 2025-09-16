package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfTextValueGeneratorTest {

  private static final String FIELD_ID = "form1[0].#subform[0].flt_txtDiagnos[0]";
  private static final String VALUE = "Diagnos är okänd men symtomen är hosta.";
  private static final String START_VALUE = "Diagnos ...";
  private static final String END_VALUE = "... är okänd men symtomen är hosta.";
  private static final String LARGE_START_VALUE = "Diagnos är ... Se fortsättningsblad!";
  private static final String LARGE_END_VALUE = "... okänd men symtomen är hosta.";

  private static final PdfTextValueGenerator pdfTextValueGenerator = new PdfTextValueGenerator();
  private static final PdfFieldId OVERFLOW_SHEET_ID = new PdfFieldId("OVERFLOW_ID");
  private static final String QUESTION_NAME = "NAME";

  @Test
  void shouldReturnType() {
    assertEquals(ElementValueText.class, pdfTextValueGenerator.getType());
  }

  @Nested
  class WithoutMaxLength {

    @Test
    void shouldSetValueIfElementDataWithTextValue() {
      final var expected = List.of(
          PdfField.builder()
              .id(FIELD_ID)
              .value(VALUE)
              .offset(0)
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

  @Nested
  class WithMaxLength {

    @Test
    void shouldSetValueIfUnderMaxLength() {
      final var expected = List.of(
          PdfField.builder()
              .id(FIELD_ID)
              .value(VALUE)
              .offset(10)
              .build()
      );

      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationTextField.builder()
                  .name(QUESTION_NAME)
                  .build()
          )
          .pdfConfiguration(
              PdfConfigurationText.builder()
                  .pdfFieldId(new PdfFieldId(FIELD_ID))
                  .maxLength(100)
                  .overflowSheetFieldId(OVERFLOW_SHEET_ID)
                  .offset(10)
                  .build()
          )
          .build();

      final var elementValue = ElementValueText.builder()
          .text(VALUE)
          .build();

      final var result = pdfTextValueGenerator.generate(elementSpecification, elementValue);

      assertEquals(expected, result);
    }
  }

  @Test
  void shouldSplitValueIfOverMaxLengthWhenShortLimit() {
    final var expected = List.of(
        PdfField.builder()
            .id(FIELD_ID)
            .value(START_VALUE)
            .build(),
        PdfField.builder()
            .id(OVERFLOW_SHEET_ID.id())
            .value(QUESTION_NAME)
            .append(true)
            .build(),
        PdfField.builder()
            .id(OVERFLOW_SHEET_ID.id())
            .value(END_VALUE + "\n")
            .append(true)
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextField.builder()
                .name(QUESTION_NAME)
                .build()
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .maxLength(12)
                .overflowSheetFieldId(OVERFLOW_SHEET_ID)
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
  void shouldSplitValueIfOverMaxLengthWhenLargeLimit() {
    final var expected = List.of(
        PdfField.builder()
            .id(FIELD_ID)
            .value(LARGE_START_VALUE)
            .build(),
        PdfField.builder()
            .id(OVERFLOW_SHEET_ID.id())
            .value(QUESTION_NAME)
            .append(true)
            .build(),
        PdfField.builder()
            .id(OVERFLOW_SHEET_ID.id())
            .value(LARGE_END_VALUE + "\n")
            .append(true)
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextField.builder()
                .name(QUESTION_NAME)
                .build()
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .maxLength(35)
                .overflowSheetFieldId(OVERFLOW_SHEET_ID)
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
  void shouldNotReturnFieldsForOverflowSheetIfNoOverFlowSheetIsProvided() {
    final var expected = List.of(
        PdfField.builder()
            .id(FIELD_ID)
            .value(START_VALUE)
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextField.builder()
                .name(QUESTION_NAME)
                .build()
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .maxLength(12)
                .build()
        )
        .build();

    final var elementValue = ElementValueText.builder()
        .text(VALUE)
        .build();

    final var result = pdfTextValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }
}
