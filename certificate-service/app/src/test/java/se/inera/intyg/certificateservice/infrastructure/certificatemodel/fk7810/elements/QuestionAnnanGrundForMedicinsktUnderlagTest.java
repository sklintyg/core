package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.OVERFLOW_SHEET_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAnnanGrundForMedicinsktUnderlag.questionAnnanGrundForMedicinsktUnderlag;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionAnnanGrundForMedicinsktUnderlagTest {

  private static final ElementId ELEMENT_ID = new ElementId("1.3");

  @Test
  void shallIncludeId() {
    final var element = questionAnnanGrundForMedicinsktUnderlag();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextField.builder()
        .name(
            "Ange vad annat är")
        .id(new FieldId("1.3"))
        .build();

    final var element = questionAnnanGrundForMedicinsktUnderlag();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "$1.3"
                )
            )
            .build(),
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("1"))
            .type(ElementRuleType.SHOW)
            .expression(
                new RuleExpression(
                    "$annat"
                )
            )
            .build()
    );

    final var element = questionAnnanGrundForMedicinsktUnderlag();

    assertEquals(expectedRules, element.rules());

  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(4000)
            .build()
    );

    final var element = questionAnnanGrundForMedicinsktUnderlag();

    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfElementPresent() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("1"))
              .value(
                  ElementValueDateList.builder()
                      .dateList(
                          List.of(
                              ElementValueDate.builder()
                                  .dateId(new FieldId("annat"))
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = questionAnnanGrundForMedicinsktUnderlag();

      final var shouldValidate = element
          .shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("2"))
              .value(
                  ElementValueDateList.builder()
                      .dateList(
                          List.of(
                              ElementValueDate.builder()
                                  .dateId(new FieldId("annat"))
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = questionAnnanGrundForMedicinsktUnderlag();

      final var shouldValidate = element
          .shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtAnnatUnderlagUtlatande[0]"))
        .maxLength(27)
        .overflowSheetFieldId(OVERFLOW_SHEET_FIELD_ID)
        .build();

    final var element = questionAnnanGrundForMedicinsktUnderlag();

    assertEquals(expected, element.pdfConfiguration());
  }
}
