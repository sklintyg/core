package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
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
import se.inera.intyg.certificateservice.domain.common.model.Code;

class QuestionUtlatandeBaseratPaAnnatTest {

  private static final ElementId ELEMENT_ID = new ElementId("1.3");

  @Test
  void shallIncludeId() {
    final var element = QuestionUtlatandeBaseratPaAnnat.questionUtlatandeBaseratPaAnnat();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name(
            "Ange vad annat är")
        .id(new FieldId("1.3"))
        .build();

    final var element = QuestionUtlatandeBaseratPaAnnat.questionUtlatandeBaseratPaAnnat();

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

    final var element = QuestionUtlatandeBaseratPaAnnat.questionUtlatandeBaseratPaAnnat();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeCustomMapping() {
    final var expectedConfiguration = new ElementMapping(
        new ElementId("1"),
        new Code(
            "ANNAT",
            "KV_FKMU_0001",
            "annat"
        )
    );

    final var element = QuestionUtlatandeBaseratPaAnnat.questionUtlatandeBaseratPaAnnat();

    assertEquals(expectedConfiguration, element.mapping());
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

      final var element = QuestionUtlatandeBaseratPaAnnat.questionUtlatandeBaseratPaAnnat();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

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

      final var element = QuestionUtlatandeBaseratPaAnnat.questionUtlatandeBaseratPaAnnat();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtAnnatAngeVad[0]"))
        .build();

    final var element = QuestionUtlatandeBaseratPaAnnat.questionUtlatandeBaseratPaAnnat();

    assertEquals(expected, element.pdfConfiguration());
  }
}