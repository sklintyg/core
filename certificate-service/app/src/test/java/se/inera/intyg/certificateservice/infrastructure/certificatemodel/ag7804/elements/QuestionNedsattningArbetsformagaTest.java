package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRangeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0003;

class QuestionNedsattningArbetsformagaTest {

  private static final ElementId ELEMENT_ID = new ElementId("32");

  @Test
  void shallIncludeId() {
    final var element = QuestionNedsattningArbetsformaga.questionNedsattningArbetsformaga();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxDateRangeList.builder()
        .name("Min bedömning av patientens nedsättning av arbetsförmågan")
        .id(new FieldId("32.1"))
        .hideWorkingHours(false)
        .dateRanges(
            List.of(
                new ElementConfigurationCode(
                    new FieldId("EN_FJARDEDEL"),
                    "25 procent",
                    CodeSystemKvFkmu0003.EN_FJARDEDEL
                ),
                new ElementConfigurationCode(
                    new FieldId("HALFTEN"),
                    "50 procent",
                    CodeSystemKvFkmu0003.HALFTEN
                ),
                new ElementConfigurationCode(
                    new FieldId("TRE_FJARDEDEL"),
                    "75 procent",
                    CodeSystemKvFkmu0003.TRE_FJARDEDEL
                ),
                new ElementConfigurationCode(
                    new FieldId("HELT_NEDSATT"),
                    "100 procent",
                    CodeSystemKvFkmu0003.HELT_NEDSATT
                )
            )
        )
        .description(
            "Utgångspunkten är att patientens arbetsförmåga ska bedömas i förhållande till patientens normala arbetstid.")
        .build();

    final var element = QuestionNedsattningArbetsformaga.questionNedsattningArbetsformaga();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression(
                    "$EN_FJARDEDEL || $HALFTEN || $TRE_FJARDEDEL || $HELT_NEDSATT"
                )
            )
            .build()
    );

    final var element = QuestionNedsattningArbetsformaga.questionNedsattningArbetsformaga();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationDateRangeList.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionNedsattningArbetsformaga.questionNedsattningArbetsformaga();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldNotIncludeWhenRenewing() {
    final var element = QuestionNedsattningArbetsformaga.questionNedsattningArbetsformaga();
    assertFalse(element.includeWhenRenewing());
  }

}