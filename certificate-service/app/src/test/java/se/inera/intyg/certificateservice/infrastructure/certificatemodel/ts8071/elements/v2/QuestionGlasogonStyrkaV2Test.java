package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.LEFT_EYE_WITHOUT_CORRECTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.QUESTION_SYNSKARPA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.RIGHT_EYE_WITHOUT_CORRECTION_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.VisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionGlasogonStyrkaV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("25");

  @Test
  void shouldIncludeId() {
    final var element = QuestionGlasogonStyrkaV2.questionGlasogonStyrkaV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .id(new FieldId("25.1"))
        .name(
            "Vid korrigering av synskärpa med glasögon, har något av glasen en styrka över plus 8 dioptrier i den mest brytande meridianen?")
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionGlasogonStyrkaV2.questionGlasogonStyrkaV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_SYNSKARPA_ID)
            .type(ElementRuleType.SHOW)
            .expression(
                new RuleExpression(
                    String.format(
                        "(('%s' < 0.8 && '%s' < 0.8) && (!empty('%s') && !empty('%s'))) || (('%s' < 0.1 || '%s' < 0.1) && (!empty('%s') && !empty('%s')))",
                        LEFT_EYE_WITHOUT_CORRECTION_ID,
                        RIGHT_EYE_WITHOUT_CORRECTION_ID,
                        LEFT_EYE_WITHOUT_CORRECTION_ID,
                        RIGHT_EYE_WITHOUT_CORRECTION_ID,
                        LEFT_EYE_WITHOUT_CORRECTION_ID,
                        RIGHT_EYE_WITHOUT_CORRECTION_ID,
                        LEFT_EYE_WITHOUT_CORRECTION_ID,
                        RIGHT_EYE_WITHOUT_CORRECTION_ID
                    )
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("exists($25.1)"))
            .build()
    );

    final var element = QuestionGlasogonStyrkaV2.questionGlasogonStyrkaV2();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionGlasogonStyrkaV2.questionGlasogonStyrkaV2();

    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfSightWithoutCorrectionForBestEyeIs08AndWorstIs00() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_SYNSKARPA_ID)
              .value(
                  ElementValueVisualAcuities.builder()
                      .rightEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(0.8)
                                      .build()
                              )
                              .build()
                      )
                      .leftEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(0.0)
                                      .build()
                              )
                              .build()
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionGlasogonStyrkaV2.questionGlasogonStyrkaV2();
      assertTrue(element.shouldValidate().test(elementData));
    }

    @Test
    void shallReturnTrueIfSightWithoutCorrectionForBestEyeIs07AndWorstIs01() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_SYNSKARPA_ID)
              .value(
                  ElementValueVisualAcuities.builder()
                      .rightEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(0.1)
                                      .build()
                              )
                              .build()
                      )
                      .leftEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(0.7)
                                      .build()
                              )
                              .build()
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionGlasogonStyrkaV2.questionGlasogonStyrkaV2();
      assertTrue(element.shouldValidate().test(elementData));
    }

    @Test
    void shallReturnFalseIfSightWithoutCorrectionForBestEyeIs08AndWorstIs01() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_SYNSKARPA_ID)
              .value(
                  ElementValueVisualAcuities.builder()
                      .rightEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(0.1)
                                      .build()
                              )
                              .build()
                      )
                      .leftEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(0.8)
                                      .build()
                              )
                              .build()
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionGlasogonStyrkaV2.questionGlasogonStyrkaV2();
      assertFalse(element.shouldValidate().test(elementData));
    }

    @Test
    void shallReturnFalseIfSightWithoutCorrectionForLeftEyeIsNullAndRightEyeIs08() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_SYNSKARPA_ID)
              .value(
                  ElementValueVisualAcuities.builder()
                      .rightEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(0.8)
                                      .build()
                              )
                              .build()
                      )
                      .leftEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(null)
                                      .build()
                              )
                              .build()
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionGlasogonStyrkaV2.questionGlasogonStyrkaV2();
      assertFalse(element.shouldValidate().test(elementData));
    }

    @Test
    void shallReturnFalseIfSightForRightEyeIsNullAndLeftEyeIs08() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_SYNSKARPA_ID)
              .value(
                  ElementValueVisualAcuities.builder()
                      .rightEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(null)
                                      .build()
                              )
                              .build()
                      )
                      .leftEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(0.8)
                                      .build()
                              )
                              .build()
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionGlasogonStyrkaV2.questionGlasogonStyrkaV2();
      assertFalse(element.shouldValidate().test(elementData));
    }
  }
}

