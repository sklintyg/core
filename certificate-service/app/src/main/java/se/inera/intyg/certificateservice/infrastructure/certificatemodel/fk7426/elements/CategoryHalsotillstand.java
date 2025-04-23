package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionHalsotillstandPsykiska.QUESTION_HALSOTILLSTAND_PSYKISKA_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionHalsotillstandPsykiska.QUESTION_HALSOTILLSTAND_PSYKISKA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionHalsotillstandSomatiska.QUESTION_HALSOTILLSTAND_SOMATISKA_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionHalsotillstandSomatiska.QUESTION_HALSOTILLSTAND_SOMATISKA_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleMandatoryCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ExpressionOperandType;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCategory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class CategoryHalsotillstand {

  private static final ElementId HALSOTILLSTAND_CATEGORY_ID = new ElementId("KAT_3");

  private CategoryHalsotillstand() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryHalsotillstand(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(HALSOTILLSTAND_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Barnets hälsotillstånd")
                .build()
        )
        .children(
            List.of(children)
        )
        .rules(
            List.of(
                ElementRuleMandatoryCategory.builder()
                    .operandType(ExpressionOperandType.OR)
                    .type(ElementRuleType.CATEGORY_MANDATORY)
                    .elementRuleExpressions(
                        List.of(
                            CertificateElementRuleFactory.mandatory(
                                QUESTION_HALSOTILLSTAND_PSYKISKA_ID,
                                QUESTION_HALSOTILLSTAND_PSYKISKA_FIELD_ID
                            ),
                            CertificateElementRuleFactory.mandatory(
                                QUESTION_HALSOTILLSTAND_SOMATISKA_ID,
                                QUESTION_HALSOTILLSTAND_SOMATISKA_FIELD_ID
                            )
                        )
                    )
                    .build()
            )
        )
        .validations(
            List.of(
                ElementValidationCategory.builder()
                    .mandatory(true)
                    .elements(List.of(QUESTION_HALSOTILLSTAND_PSYKISKA_ID,
                        QUESTION_HALSOTILLSTAND_SOMATISKA_ID))
                    .build()
            )
        )
        .build();
  }
}
