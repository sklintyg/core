package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
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
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.FK7804PdfSpecification;

class QuestionAtgarderSomKanFramjaAtergangTest {

  private static final ElementId ELEMENT_ID = new ElementId("44");

  @Test
  void shouldIncludeId() {
    final var element = QuestionAtgarderSomKanFramjaAtergang.questionAtgarderSomKanFramjaAtergang();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(new FieldId("44.1"))
        .name(
            "Här kan du beskriva andra åtgärder än åtgärder inom hälso- och sjukvården som kan främja återgången i arbete")
        .label(
            "Beskriv gärna hur åtgärderna kan främja återgången i arbete eller annan aktuell sysselsättning.")
        .description("""
            Åtgärderna kan exempelvis handla om att patienten har en regelbunden kontakt med arbetsplatsen. Det kan också vara arbetsanpassning, som anpassning av arbetstider, arbetsuppgifter eller arbetsplatsen.
            
            Du kan även föreslå att patienten får arbetsträna vilket innebär att vara på en arbetsplats och delta
            i verksamheten utan krav på produktivitet.\s
            
            Tänk på att det är Försäkringskassan eller Arbetsförmedlingen som beslutar om arbetsträning. De föreslagna åtgärderna är exempel på möjliga åtgärder och det kan finnas flera åtgärder som är lämpliga.
            """)
        .build();
    final var element = QuestionAtgarderSomKanFramjaAtergang.questionAtgarderSomKanFramjaAtergang();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("27"))
            .type(ElementRuleType.HIDE)
            .expression(new RuleExpression("$27.1"))
            .build()
    );
    final var element = QuestionAtgarderSomKanFramjaAtergang.questionAtgarderSomKanFramjaAtergang();
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(false)
            .limit(4000)
            .build()
    );
    final var element = QuestionAtgarderSomKanFramjaAtergang.questionAtgarderSomKanFramjaAtergang();
    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldHaveCorrectPdfConfiguration() {
    final var element = QuestionAtgarderSomKanFramjaAtergang.questionAtgarderSomKanFramjaAtergang();
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(
            new PdfFieldId("form1[0].Sida3[0].flt_txtArbetslivsinriktadAtgarderUnderlatta[0]"))
        .overflowSheetFieldId(new PdfFieldId("form1[0].#subform[4].flt_txtFortsattningsblad[0]"))
        .maxLength(7 * FK7804PdfSpecification.PDF_TEXT_FIELD_ROW_LENGTH)
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
      final var element = QuestionAtgarderSomKanFramjaAtergang.questionAtgarderSomKanFramjaAtergang();
      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();
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
      final var element = QuestionAtgarderSomKanFramjaAtergang.questionAtgarderSomKanFramjaAtergang();
      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();
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
      final var element = QuestionAtgarderSomKanFramjaAtergang.questionAtgarderSomKanFramjaAtergang();
      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();
      assertFalse(shouldValidate.test(elementData));
    }
  }
}
