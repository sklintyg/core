package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionNedsattningArbetsformaga.QUESTION_NEDSATTNING_ARBETSFORMAGA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSvarareAtergangVidOjamnArbetstid.QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSvarareAtergangVidOjamnArbetstid.QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0003;

class QuestionSvarareAtergangVidOjamnArbetstidTest {

  @Test
  void shouldIncludeId() {
    final var element = QuestionSvarareAtergangVidOjamnArbetstid.questionSvarareAtergangVidOjamnArbetstid();
    assertEquals(QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .id(QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_FIELD_ID)
        .name(
            "Kommer möjligheterna till återgång i arbete försämras om arbetstiden förläggs ojämnt vid deltidssjukskrivning?")
        .selectedText("Ja")
        .unselectedText("Nej")
        .description(
            """
                När du besvarar frågan ska du utgå från de uppgifter som du har om arbetstidens förläggning vid sjukskrivningstillfället, det vill säga den arbetstidsförläggning som du diskuterat med patienten.
                
                Att förläggningen försämrar patientens möjligheter till återgång i arbete kan exempelvis vara att hälsotillståndet påverkas negativt eller att sjukdomen innebär att en annan förläggning av arbetstiden än jämn minskning varje dag skulle motverka rehabiliteringen.
                """
        )
        .build();

    final var element = QuestionSvarareAtergangVidOjamnArbetstid.questionSvarareAtergangVidOjamnArbetstid();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionSvarareAtergangVidOjamnArbetstid.questionSvarareAtergangVidOjamnArbetstid();

    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($33.1)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(QUESTION_NEDSATTNING_ARBETSFORMAGA_ID)
            .type(ElementRuleType.SHOW)
            .expression(
                new RuleExpression(
                    "exists($" + CodeSystemKvFkmu0003.HALFTEN.code() + ") || exists($" +
                        CodeSystemKvFkmu0003.TRE_FJARDEDEL.code() + ") || exists($" +
                        CodeSystemKvFkmu0003.EN_FJARDEDEL.code() + ")"
                )
            )
            .build()
    );

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionSvarareAtergangVidOjamnArbetstid.questionSvarareAtergangVidOjamnArbetstid();

    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    assertEquals(expectedValidations, element.validations());
  }
}