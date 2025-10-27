package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1.QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1.QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKorrigeringAvSynskarpaV1.QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1Test {

  @Test
  void shallIncludeId() {
    final var element = QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1.questionKorrigeringAvSynskarpaIngenStyrkaOverV1();
    assertEquals(QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .id(QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_FIELD_ID)
        .selectedText("Ja")
        .unselectedText("Nej")
        .name(
            "Glasögon, inget av glasen har en styrka över plus 8 dioptrier. Tolereras korrektionen väl?"
        )
        .build();
    final var element = QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1.questionKorrigeringAvSynskarpaIngenStyrkaOverV1();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidation = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1.questionKorrigeringAvSynskarpaIngenStyrkaOverV1();
    assertEquals(expectedValidation, element.validations());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_ID)
            .expression(new RuleExpression("exists($6.2)"))
            .type(ElementRuleType.MANDATORY)
            .build(),
        ElementRuleExpression.builder()
            .id(QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID)
            .expression(new RuleExpression("$6.1"))
            .type(ElementRuleType.SHOW)
            .build()
    );

    final var element = QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1.questionKorrigeringAvSynskarpaIngenStyrkaOverV1();
    assertEquals(expectedRules, element.rules());
  }

  @Nested
  class ShouldValidateTests {

    @Test
    void shallReturnTrueIfContainsCode() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID)
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .codeId(new FieldId("6.1"))
                                  .code("6.1")
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1.questionKorrigeringAvSynskarpaIngenStyrkaOverV1();
      assertTrue(element.shouldValidate().test(elementData));
    }

    @Test
    void shallReturnFalseIfNotContainsCode() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID)
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .codeId(new FieldId("GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER"))
                                  .code("GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER")
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1.questionKorrigeringAvSynskarpaIngenStyrkaOverV1();
      assertFalse(element.shouldValidate().test(elementData));
    }
  }

  @Test
  void shallIncludeMapping() {
    final var expectedMapping = new ElementMapping(QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID, null);
    final var element = QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1.questionKorrigeringAvSynskarpaIngenStyrkaOverV1();
    assertEquals(expectedMapping, element.mapping());
  }
}