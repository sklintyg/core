package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID;

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

class QuestionKommunikationSocialInteraktionMotiveringTest {

  private static final ElementId ELEMENT_ID = new ElementId("9");

  @Test
  void shallIncludeId() {
    final var element = QuestionKommunikationSocialInteraktionMotivering.questionKommunikationSocialInteraktionMotivering();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Övergripande psykosociala funktioner")
        .label(
            "Beskriv funktionsnedsättningen, om möjligt med grad. Ange även eventuella undersökningsfynd.")
        .description(
            """
                Med psykosociala funktioner menas
                <ul>
                <li>förmågan till emotionell kontakt</li><li>social ömsesidighet</li><li>samspel</li><li>förmågan att på ett teoretiskt plan kunna sätta sig in i hur andra människor tänker och känner, även om man inte har varit med om samma sak själv.</li></ul>
                """
        )
        .id(new FieldId("9.1"))
        .build();

    final var element = QuestionKommunikationSocialInteraktionMotivering.questionKommunikationSocialInteraktionMotivering();

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
            .expression(new RuleExpression("$9.1"))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("funktionsnedsattning"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$9.2"))
            .build()
    );

    final var element = QuestionKommunikationSocialInteraktionMotivering.questionKommunikationSocialInteraktionMotivering();

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

    final var element = QuestionKommunikationSocialInteraktionMotivering.questionKommunikationSocialInteraktionMotivering();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[1].flt_txtIntellektuellFunktion[1]"))
        .maxLength(265)
        .overflowSheetFieldId(new PdfFieldId(("form1[0].#subform[4].flt_txtFortsattningsblad[0]")))
        .build();

    final var element = QuestionKommunikationSocialInteraktionMotivering.questionKommunikationSocialInteraktionMotivering();

    assertEquals(expected, element.pdfConfiguration());
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
                                  .codeId(new FieldId("9.2"))
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionKommunikationSocialInteraktionMotivering.questionKommunikationSocialInteraktionMotivering();

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

      final var element = QuestionKommunikationSocialInteraktionMotivering.questionKommunikationSocialInteraktionMotivering();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }

  @Test
  void shallIncludeVisibilityConfiguration() {
    final var expectedVisibilityConfiguration = ElementVisibilityConfigurationsCheckboxMultipleCode.builder()
        .elementId(FUNKTIONSNEDSATTNING_ID)
        .fieldId(FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID)
        .build();

    final var element = QuestionKommunikationSocialInteraktionMotivering.questionKommunikationSocialInteraktionMotivering();

    assertEquals(element.visibilityConfiguration(), expectedVisibilityConfiguration);
  }
}