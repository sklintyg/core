package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionPsykiskV1UtvecklingsstorningAllvarligV1Test {

  private static final ElementId ELEMENT_ID = new ElementId("20.7");

  @Test
  void shallIncludeId() {
    final var element = QuestionPsykiskUtvecklingsstorningAllvarligV1.questionPsykiskUtvecklingsstorningAllvarligV1();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Är det en allvarlig psykisk utvecklingsstörning?")
        .description(
            "Med allvarlig psykisk utvecklingsstörning avses mental retardation enligt DSM-IV. Det avser även grav, svår eller medelsvår psykisk utvecklingsstörning enligt ICD-10. Intellektuell funktionsnedsättning enligt DSM-5 av djupgående, svår eller måttlig grad är att jämställa med ovan.")
        .selectedText("Ja")
        .unselectedText("Nej")
        .id(new FieldId("20.7"))
        .build();

    final var element = QuestionPsykiskUtvecklingsstorningAllvarligV1.questionPsykiskUtvecklingsstorningAllvarligV1();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("20.6"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$20.6"))
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("exists($20.7)"))
            .build()
    );

    final var element = QuestionPsykiskUtvecklingsstorningAllvarligV1.questionPsykiskUtvecklingsstorningAllvarligV1();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionPsykiskUtvecklingsstorningAllvarligV1.questionPsykiskUtvecklingsstorningAllvarligV1();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionPsykiskUtvecklingsstorningAllvarligV1.questionPsykiskUtvecklingsstorningAllvarligV1();

    assertEquals(new ElementMapping(new ElementId("20"), null), element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("20.6"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionPsykiskUtvecklingsstorningAllvarligV1.questionPsykiskUtvecklingsstorningAllvarligV1();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("7.1"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionPsykiskUtvecklingsstorningAllvarligV1.questionPsykiskUtvecklingsstorningAllvarligV1();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("20.6"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionPsykiskUtvecklingsstorningAllvarligV1.questionPsykiskUtvecklingsstorningAllvarligV1();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}