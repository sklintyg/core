package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionOvrigtTest {

//  @Test
//  void shouldContainCorrectPdfConfiguration() {
//    final var elementSpecification = QuestionOvrigt.questionOvrigt();
//    final var expected = PdfConfigurationText.builder()
//        .pdfFieldId(new PdfFieldId("form1[0].Sida3[0].flt_txtOvrigaUpplysningarl[0]"))
//        .overflowSheetFieldId(new PdfFieldId("form1[0].#subform[4].flt_txtFortsattningsblad[0]"))
//        .maxLength(8 * FK7804PdfSpecification.PDF_TEXT_FIELD_ROW_LENGTH)
//        .build();
//    assertEquals(expected, elementSpecification.pdfConfiguration());
//  }

  @Test
  void shouldIncludeId() {
    final var element = QuestionOvrigt.questionOvrigt();
    assertEquals(new ElementId("25"), element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(new FieldId("25.1"))
        .name("Övriga upplysningar")
        .build();
    final var element = QuestionOvrigt.questionOvrigt();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleLimit.builder()
            .id(new ElementId("25"))
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build()
    );
    final var element = QuestionOvrigt.questionOvrigt();
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(false)
            .limit(4000)
            .build()
    );
    final var element = QuestionOvrigt.questionOvrigt();
    assertEquals(expectedValidations, element.validations());
  }

}