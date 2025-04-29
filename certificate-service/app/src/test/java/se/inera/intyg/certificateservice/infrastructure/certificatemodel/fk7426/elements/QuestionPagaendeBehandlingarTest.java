package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.FK7426PdfSpecification.ROW_MAX_LENGTH;

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

class QuestionPagaendeBehandlingarTest {

  private static final ElementId ELEMENT_ID = new ElementId("19");

  @Test
  void shallIncludeId() {
    final var element = QuestionPagaendeBehandlingar.questionPagaendeBehandlingar();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Pågående medicinska behandlingar/åtgärder")
        .description("Ange vad syftet är och om möjligt tidplan samt ansvarig vårdenhet.")
        .id(new FieldId("19.1"))
        .build();

    final var element = QuestionPagaendeBehandlingar.questionPagaendeBehandlingar();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleLimit.builder()
            .id(new ElementId("19"))
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build()
    );

    final var element = QuestionPagaendeBehandlingar.questionPagaendeBehandlingar();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(false)
            .limit(4000)
            .build()
    );

    final var element = QuestionPagaendeBehandlingar.questionPagaendeBehandlingar();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[2].flt_txt_PagaendeBehandling[0]"))
        .maxLength(ROW_MAX_LENGTH * 4)
        .overflowSheetFieldId(
            new PdfFieldId("form1[0].#subform[4].flt_txtFortsattningsblad[0]"))
        .build();

    final var element = QuestionPagaendeBehandlingar.questionPagaendeBehandlingar();

    assertEquals(expected, element.pdfConfiguration());
  }
}
