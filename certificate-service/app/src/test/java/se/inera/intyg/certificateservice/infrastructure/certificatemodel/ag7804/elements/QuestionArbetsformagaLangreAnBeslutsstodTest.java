package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionArbetsformagaLangreAnBeslutsstodTest {

  private static final ElementId ELEMENT_ID = new ElementId("37");

  @Test
  void shouldIncludeId() {
    final var element = QuestionArbetsformagaLangreAnBeslutsstod.questionArbetsformagaLangreAnBeslutsstod();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name(
            "Patientens arbetsförmåga bedöms nedsatt längre tid än den som Socialstyrelsens försäkringsmedicinska beslutsstöd anger, därför att")
        .id(new FieldId("37.1"))
        .description("""
            <ul><li>Om sjukdomen inte följer förväntat förlopp ska det framgå på vilket sätt.</li><li>Om det inträffar komplikationer som gör att det tar längre tid att återfå arbetsförmågan ska du beskriva detta.</li><li>Om sjukskrivningslängden påverkas av flera sjukdomar, så kallad samsjuklighet, ska du beskriva detta.</li></ul>
            """)
        .build();

    final var element = QuestionArbetsformagaLangreAnBeslutsstod.questionArbetsformagaLangreAnBeslutsstod();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleLimit.builder()
            .id(new ElementId("37"))
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("27"))
            .type(ElementRuleType.HIDE)
            .expression(new RuleExpression("$27.1"))
            .build()
    );

    final var element = QuestionArbetsformagaLangreAnBeslutsstod.questionArbetsformagaLangreAnBeslutsstod();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(false)
            .limit(4000)
            .build()
    );

    final var element = QuestionArbetsformagaLangreAnBeslutsstod.questionArbetsformagaLangreAnBeslutsstod();

    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnFalseIfBooleanIsTrue() {
      final var element = QuestionArbetsformagaLangreAnBeslutsstod.questionArbetsformagaLangreAnBeslutsstod();
      final var shouldValidate = element.elementSpecification(new ElementId("37"))
          .shouldValidate();
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()
      );
      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnTrueIfBooleanIsFalse() {
      final var element = QuestionArbetsformagaLangreAnBeslutsstod.questionArbetsformagaLangreAnBeslutsstod();
      final var shouldValidate = element.elementSpecification(new ElementId("37"))
          .shouldValidate();
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()
      );
      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnTrueIfElementMissing() {
      final var element = QuestionArbetsformagaLangreAnBeslutsstod.questionArbetsformagaLangreAnBeslutsstod();
      final var shouldValidate = element.elementSpecification(new ElementId("37"))
          .shouldValidate();
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("not27"))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()
      );
      assertTrue(shouldValidate.test(elementData));
    }
  }

}