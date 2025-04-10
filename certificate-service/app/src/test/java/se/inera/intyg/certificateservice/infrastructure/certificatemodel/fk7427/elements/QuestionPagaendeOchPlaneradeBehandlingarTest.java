package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.FK7427PdfSpecification.ROW_MAX_LENGTH;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

class QuestionPagaendeOchPlaneradeBehandlingarTest {

  private static final ElementId ELEMENT_ID = new ElementId("19");

  @Test
  void shallIncludeId() {
    final var element = QuestionPagaendeOchPlaneradeBehandlingar.questionPagaendeOchPlaneradeBehandlingar();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Ange pågående och planerade behandlingar")
        .id(new FieldId("19.1"))
        .build();

    final var element = QuestionPagaendeOchPlaneradeBehandlingar.questionPagaendeOchPlaneradeBehandlingar();

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

    final var element = QuestionPagaendeOchPlaneradeBehandlingar.questionPagaendeOchPlaneradeBehandlingar();

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

    final var element = QuestionPagaendeOchPlaneradeBehandlingar.questionPagaendeOchPlaneradeBehandlingar();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expectedPdfConfiguration = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[2].flt_txtBeskrivBarnetsHalsotillstand[0]"))
        .maxLength(ROW_MAX_LENGTH * 8)
        .overflowSheetFieldId(new PdfFieldId("form1[0].#subform[3].flt_txtFortsattningsblad[0]"))
        .build();

    final var element = QuestionPagaendeOchPlaneradeBehandlingar.questionPagaendeOchPlaneradeBehandlingar();

    assertEquals(expectedPdfConfiguration, element.pdfConfiguration());
  }
}
