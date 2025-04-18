package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationDisable;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;

@Component
public class CertificateDataValidationDisableConverter implements
    CertificateDataValidationConverter {

  @Override
  public ElementRuleType getType() {
    return ElementRuleType.DISABLE;
  }

  public CertificateDataValidation convert(ElementRule rule) {
    if (rule instanceof ElementRuleExpression elementRuleExpression) {
      return CertificateDataValidationDisable.builder()
          .questionId(elementRuleExpression.id().id())
          .expression(elementRuleExpression.expression().value())
          .build();
    }
    throw new IllegalStateException(
        "Invalid rule type. Type was '%s'".formatted(rule.type())
    );
  }
}
