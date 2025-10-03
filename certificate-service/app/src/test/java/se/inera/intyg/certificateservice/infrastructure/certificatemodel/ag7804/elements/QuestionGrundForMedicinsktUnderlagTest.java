package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag;

import java.time.Period;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateList;

class QuestionGrundForMedicinsktUnderlagTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");

  @Test
  void shallIncludeId() {
    final var element = questionGrundForMedicinsktUnderlag();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxMultipleDate.builder()
        .name("Intyget är baserat på")
        .id(new FieldId("1.1"))
        .dates(
            List.of(
                CheckboxDate.builder()
                    .id(new FieldId("FYSISKUNDERSOKNING"))
                    .label("min undersökning vid fysiskt vårdmöte")
                    .code(
                        new Code(
                            "FYSISKUNDERSOKNING",
                            "KV_FKMU_0001",
                            "min undersökning vid fysiskt vårdmöte"
                        )
                    )
                    .max(Period.ZERO)
                    .build(),
                CheckboxDate.builder()
                    .id(new FieldId("DIGITALUNDERSOKNING"))
                    .label("min undersökning vid digitalt vårdmöte")
                    .code(
                        new Code(
                            "DIGITALUNDERSOKNING",
                            "KV_FKMU_0001",
                            "min undersökning vid digitalt vårdmöte"
                        )
                    )
                    .max(Period.ZERO)
                    .build(),
                CheckboxDate.builder()
                    .id(new FieldId("TELEFONKONTAKT"))
                    .label("min telefonkontakt med patienten")
                    .code(
                        new Code(
                            "TELEFONKONTAKT",
                            "KV_FKMU_0001",
                            "min telefonkontakt med patienten"
                        )
                    )
                    .max(Period.ZERO)
                    .build(),
                CheckboxDate.builder()
                    .id(new FieldId("JOURNALUPPGIFTER"))
                    .label("journaluppgifter från den")
                    .code(
                        new Code(
                            "JOURNALUPPGIFTER",
                            "KV_FKMU_0001",
                            "journaluppgifter från den"
                        )
                    )
                    .max(Period.ZERO)
                    .build(),
                CheckboxDate.builder()
                    .id(new FieldId("ANNAT"))
                    .label("annat")
                    .code(
                        new Code(
                            "ANNAT",
                            "KV_FKMU_0001",
                            "annat"
                        )
                    )
                    .max(Period.ZERO)
                    .build()
            )
        )
        .build();

    final var element = questionGrundForMedicinsktUnderlag();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeMandatoryRule() {
    final var element = questionGrundForMedicinsktUnderlag();
    final var rules = element.rules();

    final var mandatoryRule = rules.stream()
        .filter(r -> r.type() == ElementRuleType.MANDATORY)
        .findFirst()
        .orElseThrow();

    final var expectedExpression = new RuleExpression(
        "$FYSISKUNDERSOKNING || $DIGITALUNDERSOKNING || $TELEFONKONTAKT || $JOURNALUPPGIFTER || $ANNAT"
    );

    assertAll(
        () -> assertEquals(expectedExpression,
            ((ElementRuleExpression) mandatoryRule).expression()),
        () -> assertEquals(ELEMENT_ID, ((ElementRuleExpression) mandatoryRule).id())
    );
  }

  @Test
  void shallIncludeHideRule() {
    final var element = questionGrundForMedicinsktUnderlag();
    final var rules = element.rules();

    final var hideRule = rules.stream()
        .filter(r -> r.type() == ElementRuleType.HIDE)
        .findFirst()
        .orElseThrow();

    assertAll(
        () -> assertEquals("$27.1",
            ((ElementRuleExpression) hideRule).expression().value()),
        () -> assertEquals("27", ((ElementRuleExpression) hideRule).id().id())
    );
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationDateList.builder()
            .mandatory(true)
            .max(Period.ZERO)
            .build()
    );

    final var element = questionGrundForMedicinsktUnderlag();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldNotIncludeWhenRenewing() {
    final var element = questionGrundForMedicinsktUnderlag();
    assertFalse(element.includeWhenRenewing());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfBooleanIsFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = questionGrundForMedicinsktUnderlag();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnTrueIfElementMissing() {
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

      final var element = questionGrundForMedicinsktUnderlag();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = questionGrundForMedicinsktUnderlag();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}