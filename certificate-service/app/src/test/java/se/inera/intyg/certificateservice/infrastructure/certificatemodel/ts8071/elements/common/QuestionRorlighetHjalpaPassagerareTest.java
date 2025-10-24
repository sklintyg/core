package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.GR_II;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.TAXI;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionRorlighetHjalpaPassagerareTest {

  private static final ElementId ELEMENT_ID = new ElementId("10.3");

  @Test
  void shallIncludeId() {
    final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Har personen en nedsättning av rörelseförmågan som gör att personen inte kan "
                + "hjälpa passagerare in och ut ur fordonet samt med bilbälte?")
        .id(new FieldId("10.3"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRule = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($10.3)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("1"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression(
                "exists(gr_II_III) || exists(forlang_gr_II_III) || exists(tax_leg)"))
            .build()
    );

    final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

    assertEquals(new ElementMapping(new ElementId("10"), null), element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfCodeIsGR23() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("1"))
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .codeId(new FieldId(GR_II_III.code()))
                                  .code(GR_II_III.code())
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnTrueIfCodeIsForlangGR23F() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("1"))
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .codeId(new FieldId(FORLANG_GR_II_III.code()))
                                  .code(FORLANG_GR_II_III.code())
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }


    @Test
    void shallReturnTrueIfCodeIsTaxi() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("1"))
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .codeId(new FieldId(TAXI.code()))
                                  .code(TAXI.code())
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("7.1"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementCodeIsNotGR23OrTaxi() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("1"))
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .codeId(new FieldId(GR_II.code()))
                                  .code(GR_II.code())
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}
