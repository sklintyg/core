package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Period;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;

class QuestionTillstandetUppskattasLivshotandeTillOchMedTest {

  private static final ElementId ELEMENT_ID = new ElementId("52.6");

  @Test
  void shallIncludeId() {
    final var element = QuestionTillstandetUppskattasLivshotandeTillOchMed.questionTillstandetUppskattasLivshotandeTillOchMed();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationDate.builder()
        .id(new FieldId("52.6"))
        .name("Till och med vilket datum")
        .build();

    final var element = QuestionTillstandetUppskattasLivshotandeTillOchMed.questionTillstandetUppskattasLivshotandeTillOchMed();

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
                    "$52.6"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("52.5"))
            .type(ElementRuleType.SHOW)
            .expression(
                new RuleExpression(
                    "$52.5"
                )
            )
            .build()
    );

    final var element = QuestionTillstandetUppskattasLivshotandeTillOchMed.questionTillstandetUppskattasLivshotandeTillOchMed();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationDate.builder()
            .mandatory(true)
            .min(Period.ofDays(0))
            .build()
    );

    final var element = QuestionTillstandetUppskattasLivshotandeTillOchMed.questionTillstandetUppskattasLivshotandeTillOchMed();

    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfElementPresent() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("52.5"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionTillstandetUppskattasLivshotandeTillOchMed.questionTillstandetUppskattasLivshotandeTillOchMed();

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
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionTillstandetUppskattasLivshotandeTillOchMed.questionTillstandetUppskattasLivshotandeTillOchMed();

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

    final var element = QuestionTillstandetUppskattasLivshotandeTillOchMed.questionTillstandetUppskattasLivshotandeTillOchMed();

    assertEquals(expectedConfiguration, element.mapping());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationDate.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[1].flt_datumTillMed[0]"))
        .build();

    final var element = QuestionTillstandetUppskattasLivshotandeTillOchMed.questionTillstandetUppskattasLivshotandeTillOchMed();

    assertEquals(expected, element.pdfConfiguration());
  }
}