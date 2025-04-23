package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionDiagnos.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionDiagnos.DIAGNOS_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionSymtom.QUESTION_SYMTOM_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionSymtom.QUESTION_SYMTOM_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleMandatoryCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ExpressionOperandType;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCategory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class CategoryDiagnos {

  private static final ElementId DIAGNOS_CATEGORY_ID = new ElementId("KAT_2");

  private CategoryDiagnos() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryDiagnos(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(DIAGNOS_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Diagnos")
                .build()
        )
        .rules(
            List.of(
                ElementRuleMandatoryCategory.builder()
                    .operandType(ExpressionOperandType.OR)
                    .type(ElementRuleType.CATEGORY_MANDATORY)
                    .elementRuleExpressions(
                        List.of(
                            CertificateElementRuleFactory.mandatory(
                                DIAGNOSIS_ID,
                                DIAGNOS_1
                            ),
                            CertificateElementRuleFactory.mandatory(
                                QUESTION_SYMTOM_ID,
                                QUESTION_SYMTOM_FIELD_ID
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
                    .elements(List.of(DIAGNOSIS_ID, QUESTION_SYMTOM_ID))
                    .build()
            )
        )
        .children(
            List.of(children)
        )
        .build();
  }
}