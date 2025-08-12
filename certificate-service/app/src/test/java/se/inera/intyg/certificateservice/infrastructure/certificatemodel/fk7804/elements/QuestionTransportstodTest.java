package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionTransportstodTest {

  @Test
  void shouldIncludeId() {
    final var element = QuestionTransportstod.questionTransportstod();
    assertEquals(new ElementId("34"), element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expected = ElementConfigurationCheckboxBoolean.builder()
        .id(new FieldId("34.1"))
        .name("Transport till och från arbetsplatsen")
        .description(
            "Om patienten kan arbeta men inte kan ta sig till arbetet som vanligt kan Försäkringskassan ersätta kostnader för arbetsresor. Det innebär att patienten i stället för sjukpenning kan få ersättning för de merutgifter som uppstår för resor till och från arbetet.")
        .label(
            "Patienten skulle kunna arbeta helt eller delvis vid hjälp med transport till och från arbetsplatsen")
        .selectedText("Ja")
        .unselectedText("Ej angivet")
        .build();
    final var element = QuestionTransportstod.questionTransportstod();
    assertEquals(expected, element.configuration());
  }

  @Test
  void shouldIncludeValidation() {
    final var expected = List.of(
        ElementValidationBoolean.builder()
            .mandatory(false)
            .build()
    );
    final var element = QuestionTransportstod.questionTransportstod();
    assertEquals(expected, element.validations());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .type(ElementRuleType.HIDE)
            .id(new ElementId("27"))
            .expression(new RuleExpression("$27.1"))
            .build()
    );

    final var element = QuestionTransportstod.questionTransportstod();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldHaveCorrectPdfConfiguration() {
    final var element = QuestionTransportstod.questionTransportstod();
    final var expected = PdfConfigurationBoolean.builder()
        .checkboxTrue(new PdfFieldId("form1[0].Sida3[0].ksr_Resor[0]"))
        .build();
    assertEquals(expected, element.pdfConfiguration());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfBooleanIsFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionTransportstod.questionTransportstod();

      final var shouldValidate = element.elementSpecification(new ElementId("34")).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnTrueIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("8.1"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionTransportstod.questionTransportstod();

      final var shouldValidate = element.elementSpecification(new ElementId("34")).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionTransportstod.questionTransportstod();

      final var shouldValidate = element.elementSpecification(new ElementId("34")).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}
