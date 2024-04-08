package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationMandatory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;

@Component
public class CertificateDataValidationMandatoryConverter implements
    CertificateDataValidationConverter {

  @Override
  public ElementRuleType getType() {
    return ElementRuleType.MANDATORY;
  }

  public CertificateDataValidation convert(ElementRule rule) {
    if (rule instanceof ElementRuleExpression elementRuleExpression) {
      return CertificateDataValidationMandatory.builder()
          .questionId(elementRuleExpression.id().id())
          .expression(elementRuleExpression.expression().value())
          .build();
    }
    throw new IllegalStateException(
        "Invalid rule type. Type was '%s'".formatted(rule.type())
    );
  }
}
