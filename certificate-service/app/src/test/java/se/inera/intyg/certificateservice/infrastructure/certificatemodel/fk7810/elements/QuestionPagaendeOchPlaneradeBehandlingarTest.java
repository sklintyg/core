package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.FK3221PdfSpecification.PDF_TEXT_FIELD_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionPagaendeOchPlaneradeBehandlingar.questionPagaendeOchPlaneradeBehandlingar;

import java.util.List;
import org.junit.jupiter.api.Disabled;
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

class QuestionPagaendeOchPlaneradeBehandlingarTest {

  private static final ElementId ELEMENT_ID = new ElementId("50");

  @Test
  void shallIncludeId() {
    final var element = questionPagaendeOchPlaneradeBehandlingar();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Ange pågående och planerade medicinska behandlingar")
        .id(new FieldId("50.1"))
        .build();

    final var element = questionPagaendeOchPlaneradeBehandlingar();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleLimit.builder()
            .id(new ElementId("50"))
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build()
    );

    final var element = questionPagaendeOchPlaneradeBehandlingar();

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

    final var element = questionPagaendeOchPlaneradeBehandlingar();

    assertEquals(expectedValidations, element.validations());
  }

  @Disabled
  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[2].flt_txtPlaneradMedicinskBehandling[0]"))
        .maxLength(PDF_TEXT_FIELD_LENGTH * 4)
        .overflowSheetFieldId(new PdfFieldId(("form1[0].#subform[4].flt_txtFortsattningsblad[0]")))
        .build();

    final var element = questionPagaendeOchPlaneradeBehandlingar();

    assertEquals(expected, element.pdfConfiguration());
  }
}