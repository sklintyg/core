package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionPrognos.ATER_X_ANTAL_MANADER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionPrognos.PROGNOS_OKLAR_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionPrognos.QUESTION_PROGNOS_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionPrognos.QUESTION_PROGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionPrognos.SANNOLIKT_INTE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionPrognos.STOR_SANNOLIKHET_FIELD_ID;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationRadioCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0006;

class QuestionPrognosTest {

  final List<ElementConfigurationCode> radioMultipleCodes = List.of(
      new ElementConfigurationCode(
          STOR_SANNOLIKHET_FIELD_ID,
          CodeSystemKvFkmu0006.STOR_SANNOLIKHET.displayName(),
          CodeSystemKvFkmu0006.STOR_SANNOLIKHET
      ),
      new ElementConfigurationCode(
          ATER_X_ANTAL_MANADER_FIELD_ID,
          CodeSystemKvFkmu0006.ATER_X_ANTAL_MANADER.displayName(),
          CodeSystemKvFkmu0006.ATER_X_ANTAL_MANADER
      ),
      new ElementConfigurationCode(
          SANNOLIKT_INTE_FIELD_ID,
          CodeSystemKvFkmu0006.SANNOLIKT_INTE.displayName(),
          CodeSystemKvFkmu0006.SANNOLIKT_INTE
      ),
      new ElementConfigurationCode(
          PROGNOS_OKLAR_FIELD_ID,
          CodeSystemKvFkmu0006.PROGNOS_OKLAR.displayName(),
          CodeSystemKvFkmu0006.PROGNOS_OKLAR
      )
  );

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionPrognos.questionPrognos();
    assertEquals(QUESTION_PROGNOS_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioMultipleCode.builder()
        .id(QUESTION_PROGNOS_FIELD_ID)
        .name("Prognos för arbetsförmåga utifrån aktuellt undersökningstillfälle")
        .description("En viktig information för att underlätta planeringen.")
        .elementLayout(ElementLayout.ROWS)
        .list(radioMultipleCodes)
        .build();

    final var element = QuestionPrognos.questionPrognos();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionPrognos.questionPrognos();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_PROGNOS_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($STOR_SANNOLIKHET) || exists($ATER_X_ANTAL_MANADER) || exists($SANNOLIKT_INTE) || exists($PROGNOS_OKLAR)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("27"))
            .type(ElementRuleType.HIDE)
            .expression(new RuleExpression("$27.1"))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionPrognos.questionPrognos();
    final var expectedValidations = List.of(
        ElementValidationCode.builder()
            .mandatory(true)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfBooleanIsFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()
      );
      final var element = QuestionPrognos.questionPrognos();
      final var shouldValidate = element.shouldValidate();
      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnTrueIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("7"))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()
      );
      final var element = QuestionPrognos.questionPrognos();
      final var shouldValidate = element.shouldValidate();
      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()
      );
      final var element = QuestionPrognos.questionPrognos();
      final var shouldValidate = element.shouldValidate();
      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnTrueIfBooleanIsNull() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(ElementValueBoolean.builder().value(null).build())
              .build()
      );
      final var element = QuestionPrognos.questionPrognos();
      final var shouldValidate = element.shouldValidate();
      assertTrue(shouldValidate.test(elementData));
    }
  }

  @Test
  void shouldContainCorrectPdfConfiguration() {
    final var elementSpecification = QuestionPrognos.questionPrognos();
    final var expected = PdfConfigurationRadioCode.builder()
        .radioGroupFieldId(new PdfFieldId("form1[0].Sida3[0].RadioButtonList4[0]"))
        .codes(Map.of(
            new FieldId(CodeSystemKvFkmu0006.STOR_SANNOLIKHET.code()), new PdfFieldId("1"),
            new FieldId(CodeSystemKvFkmu0006.ATER_X_ANTAL_MANADER.code()), new PdfFieldId("2"),
            new FieldId(CodeSystemKvFkmu0006.SANNOLIKT_INTE.code()), new PdfFieldId("3"),
            new FieldId(CodeSystemKvFkmu0006.PROGNOS_OKLAR.code()), new PdfFieldId("4")
        ))
        .build();
    assertEquals(expected, elementSpecification.pdfConfiguration());
  }

  @Test
  void shouldNotIncludeWhenRenewing() {
    final var element = QuestionPrognos.questionPrognos();
    assertFalse(element.includeWhenRenewing());
  }
}
