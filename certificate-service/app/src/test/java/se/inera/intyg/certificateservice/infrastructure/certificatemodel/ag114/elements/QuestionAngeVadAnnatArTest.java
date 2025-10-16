package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionAngeVadAnnatAr.QUESTION_ANGE_VAD_ANNAT_AR_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionAngeVadAnnatAr.QUESTION_ANGE_VAD_ANNAT_AR_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001.ANNAT;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001;

class QuestionAngeVadAnnatArTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionAngeVadAnnatAr.questionAngeVadAnnatAr();
    assertEquals(QUESTION_ANGE_VAD_ANNAT_AR_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextField.builder()
        .id(QUESTION_ANGE_VAD_ANNAT_AR_FIELD_ID)
        .name("Ange vad annat är")
        .build();

    final var element = QuestionAngeVadAnnatAr.questionAngeVadAnnatAr();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionAngeVadAnnatAr.questionAngeVadAnnatAr();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID)
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$ANNAT"))
            .build(),
        ElementRuleExpression.builder()
            .id(QUESTION_ANGE_VAD_ANNAT_AR_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$10.3"))
            .build(),
        ElementRuleLimit.builder()
            .id(QUESTION_ANGE_VAD_ANNAT_AR_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 50))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionAngeVadAnnatAr.questionAngeVadAnnatAr();
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(50)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeMapping() {
    final var element = QuestionAngeVadAnnatAr.questionAngeVadAnnatAr();
    final var expectedMapping = new ElementMapping(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
        CodeSystemKvFkmu0001.ANNAT);
    assertEquals(expectedMapping, element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfAnnatIsSelected() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID)
              .value(
                  ElementValueDateList.builder()
                      .dateListId(new FieldId("10.1"))
                      .dateList(List.of(
                          ElementValueDate.builder()
                              .dateId(new FieldId(ANNAT.code()))
                              .build()
                      ))
                      .build()
              )
              .build()
      );

      final var element = QuestionAngeVadAnnatAr.questionAngeVadAnnatAr();
      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfCodeIsDifferentFromAnnat() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID)
              .value(
                  ElementValueDateList.builder()
                      .dateListId(new FieldId("10.1"))
                      .dateList(List.of(
                          ElementValueDate.builder()
                              .dateId(new FieldId("SOME_OTHER_CODE"))
                              .build()
                      ))
                      .build()
              )
              .build()
      );

      final var element = QuestionAngeVadAnnatAr.questionAngeVadAnnatAr();
      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfCodeIsNull() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID)
              .value(
                  ElementValueDateList.builder()
                      .dateListId(new FieldId("10.1"))
                      .dateList(List.of())
                      .build()
              )
              .build()
      );

      final var element = QuestionAngeVadAnnatAr.questionAngeVadAnnatAr();
      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}
