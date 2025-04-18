package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKorrigeringAvSynskarpa.GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKorrigeringAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKorrigeringAvSynskarpa.KONTAKTLINSER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionKorrigeringAvSynskarpa.QUESTION_KORRIGERING_AV_SYNSKARPA_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionKorrigeringAvSynskarpa.QUESTION_KORRIGERING_AV_SYNSKARPA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionKorrigeringAvSynskarpa.questionKorrigeringAvSynskarpa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynskarpa.QUESTION_SYNSKARPA_ID;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.CodeListToBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.VisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;

class QuestionKorrigeringAvSynskarpaTest {


  @Test
  void shallIncludeId() {
    assertEquals(QUESTION_KORRIGERING_AV_SYNSKARPA_ID,
        QuestionKorrigeringAvSynskarpa.questionKorrigeringAvSynskarpa().id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxMultipleCode.builder()
        .id(QUESTION_KORRIGERING_AV_SYNSKARPA_FIELD_ID)
        .name("Korrigering av synskärpa genom")
        .elementLayout(ElementLayout.ROWS)
        .list(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER.code()),
                    GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER.displayName(),
                    GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER
                ),
                new ElementConfigurationCode(
                    new FieldId(GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.code()),
                    GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.displayName(),
                    GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER
                ),
                new ElementConfigurationCode(
                    new FieldId(KONTAKTLINSER.code()),
                    KONTAKTLINSER.displayName(),
                    KONTAKTLINSER
                )
            )
        )
        .build();

    final var element = QuestionKorrigeringAvSynskarpa.questionKorrigeringAvSynskarpa();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_SYNSKARPA_ID)
            .type(ElementRuleType.SHOW)
            .expression(
                new RuleExpression(
                    "(('5.2' < 0.8 && '5.1' < 0.8) && (!empty('5.2') && !empty('5.1'))) || (('5.2' < 0.1 || '5.1' < 0.1) && (!empty('5.2') && !empty('5.1')))"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(QUESTION_KORRIGERING_AV_SYNSKARPA_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($6.3) || exists($6.1) || exists($6.5)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(QUESTION_KORRIGERING_AV_SYNSKARPA_ID)
            .type(ElementRuleType.DISABLE_SUB_ELEMENT)
            .expression(
                new RuleExpression(
                    "exists($6.3)"
                )
            )
            .affectedSubElements(
                List.of(new FieldId(GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER.code()))
            )
            .build(),
        ElementRuleExpression.builder()
            .id(QUESTION_KORRIGERING_AV_SYNSKARPA_ID)
            .type(ElementRuleType.DISABLE_SUB_ELEMENT)
            .expression(
                new RuleExpression(
                    "exists($6.1)"
                )
            )
            .affectedSubElements(
                List.of(new FieldId(GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.code()))
            )
            .build()
    );

    final var element = QuestionKorrigeringAvSynskarpa.questionKorrigeringAvSynskarpa();
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = ElementValidationCodeList.builder()
        .mandatory(true)
        .build();

    final var element = QuestionKorrigeringAvSynskarpa.questionKorrigeringAvSynskarpa();
    assertEquals(expectedValidations, element.validations().getFirst());
  }

  @Test
  void shallIncludeChildren() {
    final var expectedChild = questionKorrigeringAvSynskarpa();
    final var element = questionKorrigeringAvSynskarpa(
        expectedChild
    );

    assertEquals(expectedChild, element.children().getFirst());
  }

  @Nested
  class ShouldValidateTests {

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

      final var element = QuestionKorrigeringAvSynskarpa.questionKorrigeringAvSynskarpa();
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

      final var element = QuestionKorrigeringAvSynskarpa.questionKorrigeringAvSynskarpa();
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

      final var element = QuestionKorrigeringAvSynskarpa.questionKorrigeringAvSynskarpa();
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

      final var element = QuestionKorrigeringAvSynskarpa.questionKorrigeringAvSynskarpa();
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

      final var element = QuestionKorrigeringAvSynskarpa.questionKorrigeringAvSynskarpa();
      assertFalse(element.shouldValidate().test(elementData));
    }
  }

  @Test
  void shallIncludeMapping() {
    final var expectedMapping = new ElementMapping(
        null,
        null,
        Optional.of(CodeListToBoolean.class)
    );

    final var element = QuestionKorrigeringAvSynskarpa.questionKorrigeringAvSynskarpa();
    assertEquals(expectedMapping, element.mapping());
  }
}
