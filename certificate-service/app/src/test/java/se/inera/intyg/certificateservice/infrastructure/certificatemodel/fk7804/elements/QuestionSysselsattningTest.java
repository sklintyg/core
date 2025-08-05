package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.ARBETSSOKANDE;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.FORALDRALEDIG;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.NUVARANDE_ARBETE;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.STUDIER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSysselsattning.SYSSELSATTNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSysselsattning.SYSSELSATTNING_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;

class QuestionSysselsattningTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionSysselsattning.questionSysselsattning();
    assertEquals(SYSSELSATTNING_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxMultipleCode.builder()
        .id(SYSSELSATTNING_FIELD_ID)
        .name("I relation till vilken sysselsättning bedömer du arbetsförmågan?")
        .elementLayout(ElementLayout.ROWS)
        .list(
            List.of(
                new ElementConfigurationCode(new FieldId(NUVARANDE_ARBETE.code()),
                    NUVARANDE_ARBETE.displayName(), NUVARANDE_ARBETE),
                new ElementConfigurationCode(new FieldId(ARBETSSOKANDE.code()),
                    ARBETSSOKANDE.displayName(), ARBETSSOKANDE),
                new ElementConfigurationCode(new FieldId(FORALDRALEDIG.code()),
                    FORALDRALEDIG.displayName(), FORALDRALEDIG),
                new ElementConfigurationCode(new FieldId(STUDIER.code()), STUDIER.displayName(),
                    STUDIER)
            )
        )
        .description(
            "Om du kryssar i flera val är det viktigt att du tydliggör under \"Övriga upplysningar\" om sjukskrivningens omfattning eller period skiljer sig åt mellan olika sysselsättningar.")
        .build();

    final var element = QuestionSysselsattning.questionSysselsattning();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionSysselsattning.questionSysselsattning();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(SYSSELSATTNING_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($ARBETSSOKANDE) || exists($FORALDRALEDIG) || exists($NUVARANDE_ARBETE) || exists($STUDIER)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(QUESTION_SMITTBARARPENNING_ID)
            .type(ElementRuleType.HIDE)
            .expression(
                new RuleExpression(
                    "$27.1"
                )
            )
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionSysselsattning.questionSysselsattning();
    final var expectedValidations = List.of(
        ElementValidationCodeList.builder()
            .mandatory(true)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }
}
