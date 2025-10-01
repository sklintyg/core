package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.OVERFLOW_SHEET_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.PDF_TEXT_FIELD_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_LARANDE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionLarandeBegransningMotivering.questionLarandeBegransningMotivering;

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

class QuestionLarandeBegransningMotiveringTest {

  private static final ElementId ELEMENT_ID = new ElementId("65");

  @Test
  void shallIncludeId() {
    final var element = questionLarandeBegransningMotivering();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Lärande, tillämpa kunskap samt allmänna uppgifter och krav")
        .label(
            "Beskriv aktivitetsbegränsningen. Ange grad om det är möjligt och hur aktivitetsbegränsningen kan korrigeras med hjälpmedel.")
        .description(
            "Med lärande och tillämpa kunskap menas att till exempel att förvärva färdigheter, att lösa problem och att fatta beslut. Med allmänna uppgifter och krav menas till exempel att genomföra daglig rutin, eller att hantera stress och andra psykologiska krav.")
        .id(new FieldId("65.1"))
        .build();

    final var element = questionLarandeBegransningMotivering();

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
            .expression(new RuleExpression("$65.1"))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("aktivitetsbegransning"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$15.2"))
            .build()
    );

    final var element = questionLarandeBegransningMotivering();

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

    final var element = questionLarandeBegransningMotivering();

    assertEquals(expectedValidations, element.validations());
  }


  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].Sida4[0].flt_txtLarandeKunskap[0]"))
        .maxLength(PDF_TEXT_FIELD_LENGTH * 4)
        .overflowSheetFieldId(OVERFLOW_SHEET_FIELD_ID)
        .build();

    final var element = questionLarandeBegransningMotivering();

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
                                  .codeId(new FieldId("15.2"))
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = questionLarandeBegransningMotivering();

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

      final var element = questionLarandeBegransningMotivering();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }

  @Test
  void shallIncludeVisibilityConfiguration() {
    final var expectedVisibilityConfiguration = ElementVisibilityConfigurationsCheckboxMultipleCode.builder()
        .elementId(AKTIVITETSBAGRENSNINGAR_ID)
        .fieldId(AKTIVITETSBAGRENSNINGAR_LARANDE_ID)
        .build();

    final var element = questionLarandeBegransningMotivering();

    assertEquals(element.visibilityConfiguration(), expectedVisibilityConfiguration);
  }
}