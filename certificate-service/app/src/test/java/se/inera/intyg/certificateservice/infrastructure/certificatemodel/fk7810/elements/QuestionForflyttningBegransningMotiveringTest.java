package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.OVERFLOW_SHEET_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.PDF_TEXT_FIELD_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_MOVEMENT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionForflyttningBegransningMotivering.questionForflyttningBegransningMotivering;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisibilityConfigurationsCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionForflyttningBegransningMotiveringTest {

  private static final ElementId ELEMENT_ID = new ElementId("67");

  @Test
  void shallIncludeId() {
    final var element = questionForflyttningBegransningMotivering();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Förflyttning")
        .label(
            "Beskriv aktivitetsbegränsningen. Ange grad om det är möjligt och hur aktivitetsbegränsningen kan korrigeras med hjälpmedel.")
        .description(
            "Med förflyttning menas till exempel att röra sig genom att ändra kroppsställning, att förflytta sig och att använda handens finmotorik för att flytta föremål.")
        .id(new FieldId("67.1"))
        .build();

    final var element = questionForflyttningBegransningMotivering();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$67.1"))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("aktivitetsbegransning"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$17.2"))
            .build()
    );

    final var element = questionForflyttningBegransningMotivering();

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

    final var element = questionForflyttningBegransningMotivering();

    assertEquals(expectedValidations, element.validations());
  }


  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].Sida4[0].flt_txtForflyttning[0]"))
        .maxLength(PDF_TEXT_FIELD_LENGTH * 4)
        .overflowSheetFieldId(OVERFLOW_SHEET_FIELD_ID)
        .build();

    final var element = questionForflyttningBegransningMotivering();

    assertEquals(expected, element.pdfConfiguration());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfElementPresent() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("aktivitetsbegransning"))
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .codeId(new FieldId("17.2"))
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = questionForflyttningBegransningMotivering();

      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementPresent() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("aktivitetsbegransning"))
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .codeId(new FieldId("missing"))
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = questionForflyttningBegransningMotivering();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }

  @Test
  void shallIncludeVisibilityConfiguration() {
    final var expectedVisibilityConfiguration = ElementVisibilityConfigurationsCheckboxMultipleCode.builder()
        .elementId(AKTIVITETSBAGRENSNINGAR_ID)
        .fieldId(AKTIVITETSBAGRENSNINGAR_MOVEMENT_ID)
        .build();

    final var element = questionForflyttningBegransningMotivering();

    assertEquals(element.visibilityConfiguration(), expectedVisibilityConfiguration);
  }
}