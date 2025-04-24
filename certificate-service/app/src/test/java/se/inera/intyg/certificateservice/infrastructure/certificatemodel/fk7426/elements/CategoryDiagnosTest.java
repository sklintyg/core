package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.CategoryDiagnos.categoryDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionDiagnos.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionSymtom.QUESTION_SYMTOM_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleMandatoryCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ExpressionOperandType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCategory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

class CategoryDiagnosTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_2");

  @Test
  void shallIncludeId() {
    final var element = categoryDiagnos();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Diagnos")
        .build();

    final var element = categoryDiagnos();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeChildren() {
    final var child = ElementSpecification.builder().id(new ElementId("CHILD_1")).build();

    final var element = categoryDiagnos(child);

    assertEquals(List.of(child), element.children());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleMandatoryCategory.builder()
            .operandType(ExpressionOperandType.OR)
            .type(ElementRuleType.CATEGORY_MANDATORY)
            .elementRuleExpressions(
                List.of(
                    CertificateElementRuleFactory.mandatory(
                        new ElementId("58"),
                        new FieldId("huvuddiagnos")
                    ),
                    CertificateElementRuleFactory.mandatory(
                        new ElementId("55"),
                        new FieldId("55.1")
                    )
                )
            )
            .build());

    final var element = categoryDiagnos();

    assertEquals(expectedRules, element.rules());
  }


  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationCategory.builder()
            .mandatory(true)
            .elements(
                List.of(DIAGNOSIS_ID, QUESTION_SYMTOM_ID))
            .build()
    );

    final var element = categoryDiagnos();

    assertEquals(expectedValidations, element.validations());
  }
}
