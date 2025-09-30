package se.inera.intyg.certificateservice.application.certificate.service.converter;

import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationAutoFill;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;

public class CertificateDataValidationAutoFillConverter implements
    CertificateDataValidationConverter {

  @Override
  public ElementRuleType getType() {
    return ElementRuleType.AUTO_FILL;
  }

  @Override
  public CertificateDataValidation convert(ElementRule rule) {
    if (!(rule instanceof ElementRuleExpression elementRuleExpression)) {
      throw new IllegalStateException(
          "Invalid rule type. Type was '%s'".formatted(rule.type())
      );
    }
    return CertificateDataValidationAutoFill.builder()
        .questionId(elementRuleExpression.id().id())
        .expression(elementRuleExpression.expression().value())
        .build();
  }
}
