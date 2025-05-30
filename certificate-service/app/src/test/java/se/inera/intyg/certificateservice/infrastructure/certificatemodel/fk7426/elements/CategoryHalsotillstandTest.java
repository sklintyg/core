package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionHalsotillstandPsykiska.QUESTION_HALSOTILLSTAND_PSYKISKA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionHalsotillstandSomatiska.QUESTION_HALSOTILLSTAND_SOMATISKA_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleMandatoryCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ExpressionOperandType;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCategory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

class CategoryHalsotillstandTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_3");

  @Test
  void shallIncludeId() {
    final var element = CategoryHalsotillstand.categoryHalsotillstand();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Barnets hälsotillstånd")
        .build();

    final var element = CategoryHalsotillstand.categoryHalsotillstand();

    assertEquals(expectedConfiguration, element.configuration());
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
                        QuestionHalsotillstandPsykiska.QUESTION_HALSOTILLSTAND_PSYKISKA_ID,
                        QuestionHalsotillstandPsykiska.QUESTION_HALSOTILLSTAND_PSYKISKA_FIELD_ID
                    ),
                    CertificateElementRuleFactory.mandatory(
                        QuestionHalsotillstandSomatiska.QUESTION_HALSOTILLSTAND_SOMATISKA_ID,
                        QuestionHalsotillstandSomatiska.QUESTION_HALSOTILLSTAND_SOMATISKA_FIELD_ID
                    )
                )
            )
            .build()
    );

    final var element = CategoryHalsotillstand.categoryHalsotillstand();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationCategory.builder()
            .mandatory(true)
            .elements(
                List.of(QUESTION_HALSOTILLSTAND_PSYKISKA_ID, QUESTION_HALSOTILLSTAND_SOMATISKA_ID))
            .build()
    );

    final var element = CategoryHalsotillstand.categoryHalsotillstand();

    assertEquals(expectedValidations, element.validations());
  }
}
