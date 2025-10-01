package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.FK3221PdfSpecification.PDF_TEXT_FIELD_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionIntellektuellFunktionMotivering.questionIntellektuellFunktionMotivering;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisibilityConfigurationsCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionIntellektuellFunktionMotiveringTest {

  private static final ElementId ELEMENT_ID = new ElementId("8");

  @Test
  void shallIncludeId() {
    final var element = questionIntellektuellFunktionMotivering();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Intellektuell funktion")
        .label("Beskriv eventuella iakttagelser alternativt testresultat från psykologutredning")
        .description(
            "Med intellektuell funktion, teoretisk begåvning eller intelligens menas förmågan att tänka logiskt. För att bedöma nivån krävs det att test utförts av en psykolog.")
        .id(new FieldId("8.1"))
        .build();

    final var element = questionIntellektuellFunktionMotivering();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$8.1"))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("funktionsnedsattning"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$8.2"))
            .build()
    );

    final var element = questionIntellektuellFunktionMotivering();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(4000)
            .build()
    );

    final var element = questionIntellektuellFunktionMotivering();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[1].flt_txtIntellektuellFunktion[0]"))
        .maxLength(PDF_TEXT_FIELD_LENGTH * 4)
        .overflowSheetFieldId(new PdfFieldId(("form1[0].#subform[4].flt_txtFortsattningsblad[0]")))
        .build();

    final var element = questionIntellektuellFunktionMotivering();

    assertEquals(expected, element.pdfConfiguration());
  }

  @Test
  void shallIncludeVisibilityConfiguration() {
    final var expectedVisibilityConfiguration = ElementVisibilityConfigurationsCheckboxMultipleCode.builder()
        .elementId(FUNKTIONSNEDSATTNING_ID)
        .fieldId(QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID)
        .build();
    final var element = questionIntellektuellFunktionMotivering();
    assertEquals(expectedVisibilityConfiguration, element.visibilityConfiguration());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfElementPresent() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("funktionsnedsattning"))
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .codeId(new FieldId("8.2"))
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = questionIntellektuellFunktionMotivering();

      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementPresent() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("funktionsnedsattning"))
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .codeId(new FieldId("missing"))
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = questionIntellektuellFunktionMotivering();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}