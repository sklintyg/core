package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_HORSELFUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_ID;

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

class QuestionHorselFunktionMotiveringTest {

  private static final ElementId ELEMENT_ID = new ElementId("48");

  @Test
  void shallIncludeId() {
    final var element = QuestionHorselFunktionMotivering.questionHorselFunktionMotivering();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Hörselfunktion")
        .label("Beskriv funktionsnedsättningen och undersökningsfynd.")
        .description(
            """
                Hörselfunktion handlar om förmågan att förnimma närvaro av ljud och att urskilja lokalisering, tonhöjd, ljudstyrka och ljudkvalitet.
                
                För att Försäkringskassan ska kunna bedöma om det finns rätt till garanterad nivå av merkostnadsersättning är följande information viktig. 
                
                Beskriv eventuell nedsättning av hörseln utifrån hörselmätningar och öronstatus. Motivera konstaterade diagnoser utifrån hörseltesterna. Värdera sambandet mellan hörseltesterna och eventuella avvikelser. Beskriv förmågan till kommunikation och taluppfattning utifrån observation och resultat av mätningar eller tester. Skriv om objektiva hörselmätningar har gjorts. Ange vilken typ av hörhjälpmedel patienten använder, och om hen erbjudits utredning för cochleaimplantat (CI).
                
                Du kan skicka in
                <ul>
                <li>resultat av hörseltester – tonaudiogram med ben och luftledning</li><li>resultat av maximal taluppfattning med angiven stimuleringsnivå i decibel (dB)</li><li>taluppfattning i ljudfält 65dB med optimalt anpassade hörhjälpmedel</li><li>resultat av eventuella objektiva hörselmätningar</li><li>underlag från andra bedömningar som gäller kommunikation.</li></ul>
                """
        )
        .id(new FieldId("48.1"))
        .build();

    final var element = QuestionHorselFunktionMotivering.questionHorselFunktionMotivering();

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
            .expression(new RuleExpression("$48.1"))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("funktionsnedsattning"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$48.2"))
            .build()
    );

    final var element = QuestionHorselFunktionMotivering.questionHorselFunktionMotivering();

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

    final var element = QuestionHorselFunktionMotivering.questionHorselFunktionMotivering();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[2].flt_txtIntellektuellFunktion[4]"))
        .maxLength(265)
        .overflowSheetFieldId(new PdfFieldId(("form1[0].#subform[4].flt_txtFortsattningsblad[0]")))
        .build();

    final var element = QuestionHorselFunktionMotivering.questionHorselFunktionMotivering();

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
                                  .codeId(new FieldId("48.2"))
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionHorselFunktionMotivering.questionHorselFunktionMotivering();

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

      final var element = QuestionHorselFunktionMotivering.questionHorselFunktionMotivering();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }

  @Test
  void shallIncludeVisibilityConfiguration() {
    final var expectedVisibilityConfiguration = ElementVisibilityConfigurationsCheckboxMultipleCode.builder()
        .elementId(FUNKTIONSNEDSATTNING_ID)
        .fieldId(FUNKTIONSNEDSATTNING_HORSELFUNKTION_ID)
        .build();

    final var element = QuestionHorselFunktionMotivering.questionHorselFunktionMotivering();

    assertEquals(element.visibilityConfiguration(), expectedVisibilityConfiguration);
  }
}