package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

class QuestionHalsotillstandPsykiskaTest {

  private static final ElementId ELEMENT_ID = new ElementId("59.2");

  @Test
  void shallIncludeId() {
    final var element = QuestionHalsotillstandPsykiska.questionHalsotillstandPsykiska();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Barnets aktuella psykiska hälsotillstånd")
        .description(
            "Beskriv barnets nuvarande psykiska påverkan. Ta med aktuella undersökningsfynd, testresultat och observationer som har betydelse för din bedömning av allvarligt sjukt barn.")
        .id(new FieldId("59.2"))
        .build();

    final var element = QuestionHalsotillstandPsykiska.questionHalsotillstandPsykiska();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleLimit.builder()
            .id(new ElementId("59.2"))
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build()
    );

    final var element = QuestionHalsotillstandPsykiska.questionHalsotillstandPsykiska();

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

    final var element = QuestionHalsotillstandPsykiska.questionHalsotillstandPsykiska();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[2].flt_txtBarnetsHalsotillstand[0]"))
        .maxLength(21)
        .overflowSheetFieldId(
            new PdfFieldId("form1[0].#subform[4].flt_txtFortsattningsblad[0]"))
        .build();

    final var element = QuestionHalsotillstandPsykiska.questionHalsotillstandPsykiska();

    assertEquals(expected, element.pdfConfiguration());
  }
}
