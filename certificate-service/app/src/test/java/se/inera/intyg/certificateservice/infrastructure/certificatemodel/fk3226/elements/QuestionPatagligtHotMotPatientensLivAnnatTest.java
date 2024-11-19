package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionPatagligtHotMotPatientensLivAnnatTest {

  private static final ElementId ELEMENT_ID = new ElementId("52.7");

  @Test
  void shallIncludeId() {
    final var element = QuestionPatagligtHotMotPatientensLivAnnat.questionPatagligtHotMotPatientensLivAnnat();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(new FieldId("52.7"))
        .name(
            "Beskriv på vilket sätt sjukdomstillståndet utgör ett påtagligt hot mot patientens liv"
        )
        .label(
            "Ange när tillståndet blev livshotande, och om det är möjligt hur länge hotet mot livet kvarstår när patienten får vård enligt den vårdplan som gäller."
        )
        .build();

    final var element = QuestionPatagligtHotMotPatientensLivAnnat.questionPatagligtHotMotPatientensLivAnnat();

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
                    "$52.7"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("52"))
            .type(ElementRuleType.SHOW)
            .expression(
                new RuleExpression(
                    "$ANNAT"
                )
            )
            .build(),
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 265))
            .build()
    );

    final var element = QuestionPatagligtHotMotPatientensLivAnnat.questionPatagligtHotMotPatientensLivAnnat();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(265)
            .build()
    );

    final var element = QuestionPatagligtHotMotPatientensLivAnnat.questionPatagligtHotMotPatientensLivAnnat();

    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfElementPresent() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("52"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId("ANNAT"))
                      .build()
              )
              .build()
      );

      final var element = QuestionPatagligtHotMotPatientensLivAnnat.questionPatagligtHotMotPatientensLivAnnat();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID)
          .shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("5"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId("annat"))
                      .build()
              )
              .build()
      );

      final var element = QuestionPatagligtHotMotPatientensLivAnnat.questionPatagligtHotMotPatientensLivAnnat();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID)
          .shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }

  @Test
  void shallIncludeCustomMapping() {
    final var expectedConfiguration = new ElementMapping(
        new ElementId("52"), null
    );

    final var element = QuestionPatagligtHotMotPatientensLivAnnat.questionPatagligtHotMotPatientensLivAnnat();

    assertEquals(expectedConfiguration, element.mapping());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[1].flt_txtBeskrivSjukdomstillstandet[0]"))
        .maxLength(265)
        .build();

    final var element = QuestionPatagligtHotMotPatientensLivAnnat.questionPatagligtHotMotPatientensLivAnnat();

    assertEquals(expected, element.pdfConfiguration());
  }
}