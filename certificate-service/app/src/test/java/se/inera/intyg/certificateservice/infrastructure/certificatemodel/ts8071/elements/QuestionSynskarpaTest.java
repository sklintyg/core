package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.ANNAT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.TAXI;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionIntygetAvser.QUESTION_INTYGET_AVSER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynskarpa.QUESTION_SYNSKARPA_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationVisualAcuities;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

class QuestionSynskarpaTest {

  private static final ElementId ELEMENT_ID = new ElementId("5");

  @Test
  void shallIncludeId() {
    final var element = QuestionSynskarpa.questionSynskarpa();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationVisualAcuities.builder()
        .id(new FieldId("5.1"))
        .name("Synskärpa")
        .withCorrectionLabel("Med korrektion")
        .withoutCorrectionLabel("Utan korrektion")
        .min(0.0)
        .max(2.0)
        .rightEye(
            ElementVisualAcuity.builder()
                .label("Höger öga")
                .withoutCorrectionId("5.2")
                .withCorrectionId("5.5")
                .build()
        )
        .leftEye(
            ElementVisualAcuity.builder()
                .label("Vänster öga")
                .withoutCorrectionId("5.3")
                .withCorrectionId("5.6")
                .build()
        )
        .binocular(
            ElementVisualAcuity.builder()
                .label("Binokulärt")
                .withoutCorrectionId("5.4")
                .withCorrectionId("5.7")
                .build()
        )
        .build();

    final var element = QuestionSynskarpa.questionSynskarpa();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRule = List.of(
        CertificateElementRuleFactory.showIfNot(
            QUESTION_SYNFUNKTIONER_ID,
            QUESTION_SYNFUNKTIONER_FIELD_ID
        ),
        CertificateElementRuleFactory.mandatoryAndExist(
            QUESTION_SYNSKARPA_ID,
            List.of(
                new FieldId("5.2"),
                new FieldId("5.3"),
                new FieldId("5.4")
            )
        )
    );

    final var element = QuestionSynskarpa.questionSynskarpa();

    assertEquals(expectedRule, element.rules());
  }

  @Nested
  class ValidationTests {

    @Test
    void shallIncludeValidation() {
      final var validation = (ElementValidationVisualAcuities) QuestionSynskarpa.questionSynskarpa()
          .validations().getFirst();

      assertAll(
          () -> assertEquals(0.0, validation.min()),
          () -> assertEquals(2.0, validation.max()),
          () -> assertTrue(validation.mandatory()),
          () -> assertNotNull(validation.shouldValidateSightOnBothEyes())
      );
    }

    @Test
    void shallReturnTrueIfFieldHasValue() {
      final var validation = (ElementValidationVisualAcuities) QuestionSynskarpa.questionSynskarpa()
          .validations().getFirst();

      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_INTYGET_AVSER_ID)
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .code(TAXI.code())
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      assertTrue(validation.shouldValidateSightOnBothEyes().test(elementData));
    }

    @Test
    void shallReturnFalseIfFieldDontHaveValue() {
      final var validation = (ElementValidationVisualAcuities) QuestionSynskarpa.questionSynskarpa()
          .validations().getFirst();

      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_INTYGET_AVSER_ID)
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .code(ANNAT.code())
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      assertFalse(validation.shouldValidateSightOnBothEyes().test(elementData));
    }
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfBooleanIsFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("4"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionSynskarpa.questionSynskarpa();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("8.1"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionSynskarpa.questionSynskarpa();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("4"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionSynskarpa.questionSynskarpa();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}