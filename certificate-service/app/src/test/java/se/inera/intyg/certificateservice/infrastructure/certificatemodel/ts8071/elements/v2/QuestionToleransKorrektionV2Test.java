package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.VisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionToleransKorrektionV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("24");

  @Test
  void shouldIncludeId() {
    final var element = QuestionToleransKorrektionV2.questionToleransKorrektionV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(new FieldId("24.1"))
        .name("Ange eventuella problem med tolerans av korrektionen")
        .build();

    final var element = QuestionToleransKorrektionV2.questionToleransKorrektionV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionToleransKorrektionV2.questionToleransKorrektionV2();

    assertEquals(2, element.rules().size());
  }

  @Test
  void shouldIncludeTextLimitRule() {
    final var expectedRule = ElementRuleLimit.builder()
        .id(ELEMENT_ID)
        .type(ElementRuleType.TEXT_LIMIT)
        .limit(new RuleLimit((short) 250))
        .build();

    final var element = QuestionToleransKorrektionV2.questionToleransKorrektionV2();

    assertEquals(expectedRule, element.rules().get(1));
  }

  @Test
  void shouldIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(false)
            .limit(250)
            .build()
    );

    final var element = QuestionToleransKorrektionV2.questionToleransKorrektionV2();

    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfRightEyeWithoutCorrectionLessThan08() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("5"))
              .value(
                  ElementValueVisualAcuities.builder()
                      .rightEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(0.7)
                                      .build()
                              )
                              .withCorrection(
                                  Correction.builder()
                                      .build()
                              )
                              .build()
                      )
                      .leftEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(1.0)
                                      .build()
                              )
                              .withCorrection(
                                  Correction.builder()
                                      .build()
                              )
                              .build()
                      )
                      .binocular(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(1.0)
                                      .build()
                              )
                              .withCorrection(
                                  Correction.builder()
                                      .build()
                              )
                              .build()
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionToleransKorrektionV2.questionToleransKorrektionV2();

      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnTrueIfLeftEyeWithoutCorrectionLessThan08() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("5"))
              .value(
                  ElementValueVisualAcuities.builder()
                      .rightEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(1.0)
                                      .build()
                              )
                              .withCorrection(
                                  Correction.builder()
                                      .build()
                              )
                              .build()
                      )
                      .leftEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(0.5)
                                      .build()
                              )
                              .withCorrection(
                                  Correction.builder()
                                      .build()
                              )
                              .build()
                      )
                      .binocular(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(1.0)
                                      .build()
                              )
                              .withCorrection(
                                  Correction.builder()
                                      .build()
                              )
                              .build()
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionToleransKorrektionV2.questionToleransKorrektionV2();

      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnTrueIfBinocularWithoutCorrectionLessThan08() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("5"))
              .value(
                  ElementValueVisualAcuities.builder()
                      .rightEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(1.0)
                                      .build()
                              )
                              .withCorrection(
                                  Correction.builder()
                                      .build()
                              )
                              .build()
                      )
                      .leftEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(1.0)
                                      .build()
                              )
                              .withCorrection(
                                  Correction.builder()
                                      .build()
                              )
                              .build()
                      )
                      .binocular(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(0.6)
                                      .build()
                              )
                              .withCorrection(
                                  Correction.builder()
                                      .build()
                              )
                              .build()
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionToleransKorrektionV2.questionToleransKorrektionV2();

      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("4.1"))
              .value(
                  ElementValueVisualAcuities.builder()
                      .rightEye(VisualAcuity.builder().build())
                      .leftEye(VisualAcuity.builder().build())
                      .binocular(VisualAcuity.builder().build())
                      .build()
              )
              .build()
      );

      final var element = QuestionToleransKorrektionV2.questionToleransKorrektionV2();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfAllValuesAreGreaterThanOrEqualTo08() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("5"))
              .value(
                  ElementValueVisualAcuities.builder()
                      .rightEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(0.8)
                                      .build()
                              )
                              .withCorrection(
                                  Correction.builder()
                                      .build()
                              )
                              .build()
                      )
                      .leftEye(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(1.0)
                                      .build()
                              )
                              .withCorrection(
                                  Correction.builder()
                                      .build()
                              )
                              .build()
                      )
                      .binocular(
                          VisualAcuity.builder()
                              .withoutCorrection(
                                  Correction.builder()
                                      .value(1.0)
                                      .build()
                              )
                              .withCorrection(
                                  Correction.builder()
                                      .build()
                              )
                              .build()
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionToleransKorrektionV2.questionToleransKorrektionV2();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}

