package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.OVERFLOW_SHEET_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.PDF_TEXT_FIELD_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_KOMMUNIKATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionKommunikationBegransningMotivering.questionKommunikationBegransningMotivering;

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

class QuestionKommunikationBegransningMotiveringTest {

  private static final ElementId ELEMENT_ID = new ElementId("66");

  @Test
  void shallIncludeId() {
    final var element = questionKommunikationBegransningMotivering();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Kommunikation")
        .label(
            "Beskriv aktivitetsbegränsningen. Ange grad om det är möjligt och hur aktivitetsbegränsningen kan korrigeras med hjälpmedel.")
        .description(
            "Med kommunikation menas till exempel att kommunicera genom språk, tecken och symboler. Det innefattar att ta emot och att förmedla budskap, att genomföra samtal samt att använda olika metoder och hjälpmedel för detta.")
        .id(new FieldId("66.1"))
        .build();

    final var element = questionKommunikationBegransningMotivering();

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
            .expression(new RuleExpression("$66.1"))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("aktivitetsbegransning"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$16.2"))
            .build()
    );

    final var element = questionKommunikationBegransningMotivering();

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

    final var element = questionKommunikationBegransningMotivering();

    assertEquals(expectedValidations, element.validations());
  }


  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].Sida4[0].flt_txtKommunikation[0]"))
        .maxLength(PDF_TEXT_FIELD_LENGTH * 4)
        .overflowSheetFieldId(OVERFLOW_SHEET_FIELD_ID)
        .build();

    final var element = questionKommunikationBegransningMotivering();

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
                                  .codeId(new FieldId("16.2"))
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = questionKommunikationBegransningMotivering();

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

      final var element = questionKommunikationBegransningMotivering();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }

  @Test
  void shallIncludeVisibilityConfiguration() {
    final var expectedVisibilityConfiguration = ElementVisibilityConfigurationsCheckboxMultipleCode.builder()
        .elementId(AKTIVITETSBAGRENSNINGAR_ID)
        .fieldId(AKTIVITETSBAGRENSNINGAR_KOMMUNIKATION_ID)
        .build();

    final var element = questionKommunikationBegransningMotivering();

    assertEquals(element.visibilityConfiguration(), expectedVisibilityConfiguration);
  }
}