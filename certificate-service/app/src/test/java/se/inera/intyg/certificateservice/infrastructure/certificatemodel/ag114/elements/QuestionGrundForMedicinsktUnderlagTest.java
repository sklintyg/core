package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001.ANNAT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001.DIGITALUNDERSOKNING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001.FYSISKUNDERSOKNING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001.JOURNALUPPGIFTER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001.TELEFONKONTAKT;

import java.time.Period;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateList;

class QuestionGrundForMedicinsktUnderlagTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag();
    assertEquals(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxMultipleDate.builder()
        .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID)
        .name("Intyget är baserat på")
        .dates(List.of(
            CheckboxDate.builder()
                .id(new FieldId(FYSISKUNDERSOKNING.code()))
                .label("min undersökning vid fysiskt vårdmöte")
                .code(FYSISKUNDERSOKNING)
                .max(Period.ofDays(0))
                .build(),
            CheckboxDate.builder()
                .id(new FieldId(DIGITALUNDERSOKNING.code()))
                .label("min undersökning vid digitalt vårdmöte")
                .code(DIGITALUNDERSOKNING)
                .max(Period.ofDays(0))
                .build(),
            CheckboxDate.builder()
                .id(new FieldId(TELEFONKONTAKT.code()))
                .label("min telefonkontakt med patienten")
                .code(TELEFONKONTAKT)
                .max(Period.ofDays(0))
                .build(),
            CheckboxDate.builder()
                .id(new FieldId(JOURNALUPPGIFTER.code()))
                .label("journaluppgifter från den")
                .code(JOURNALUPPGIFTER)
                .max(Period.ofDays(0))
                .build(),
            CheckboxDate.builder()
                .id(new FieldId(ANNAT.code()))
                .label("annat")
                .code(ANNAT)
                .max(Period.ofDays(0))
                .build()
        ))
        .build();

    final var element = QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression(
                "$FYSISKUNDERSOKNING || $DIGITALUNDERSOKNING || $TELEFONKONTAKT || $JOURNALUPPGIFTER || $ANNAT"))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag();
    final var expectedValidations = List.of(
        ElementValidationDateList.builder()
            .mandatory(true)
            .max(Period.ofDays(0))
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }
}
