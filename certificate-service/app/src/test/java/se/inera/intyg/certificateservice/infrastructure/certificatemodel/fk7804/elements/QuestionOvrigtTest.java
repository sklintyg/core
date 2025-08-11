package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.FK7804PdfSpecification;

class QuestionOvrigtTest {

  @Test
  void shouldContainCorrectPdfConfiguration() {
    final var elementSpecification = QuestionOvrigt.questionOvrigt();
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].Sida3[0].flt_txtOvrigaUpplysningarl[0]"))
        .overflowSheetFieldId(new PdfFieldId("form1[0].#subform[4].flt_txtFortsattningsblad[0]"))
        .maxLength(8 * FK7804PdfSpecification.PDF_TEXT_FIELD_ROW_LENGTH)
        .build();
    assertThat(elementSpecification.pdfConfiguration()).isEqualTo(expected);
  }

  @Test
  void shouldIncludeId() {
    final var element = QuestionOvrigt.questionOvrigt();
    assertThat(element.id()).isEqualTo(new ElementId("25"));
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(new FieldId("25.1"))
        .name("Övriga upplysningar")
        .build();
    final var element = QuestionOvrigt.questionOvrigt();
    assertThat(element.configuration()).isEqualTo(expectedConfiguration);
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
    assertThat(element.rules()).isEqualTo(expectedRules);
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
    assertThat(element.validations()).isEqualTo(expectedValidations);
  }
}
