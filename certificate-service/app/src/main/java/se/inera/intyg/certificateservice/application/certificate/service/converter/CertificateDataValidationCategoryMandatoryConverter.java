package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationCategoryMandatory;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationMandatory;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.ExpressionTypeEnum;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleMandatoryCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;

@Component
public class CertificateDataValidationCategoryMandatoryConverter implements
    CertificateDataValidationConverter {

  @Override
  public ElementRuleType getType() {
    return ElementRuleType.CATEGORY_MANDATORY;
  }

  @Override
  public CertificateDataValidation convert(ElementRule rule) {
    if (!(rule instanceof ElementRuleMandatoryCategory elementRuleMandatoryCategory)) {
      throw new IllegalArgumentException("Invalid rule type. Type was '%s'".formatted(rule.type()));
    }

    return CertificateDataValidationCategoryMandatory.builder()
        .expressionType(
            ExpressionTypeEnum.fromValue(elementRuleMandatoryCategory.operandType().name()))
        .questions(
            elementRuleMandatoryCategory.elementRuleExpressions().stream()
                .map(elementRuleExpression ->
                    CertificateDataValidationMandatory.builder()
                        .questionId(((ElementRuleExpression) elementRuleExpression).id().id())
                        .expression(
                            ((ElementRuleExpression) elementRuleExpression).expression().value())
                        .build()
                )
                .toList()
        )
        .build();
  }
}