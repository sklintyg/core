package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationMandatory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

@Component
public class CertificateDataValidationMandatoryConverter implements
    CertificateDataValidationConverter {

  @Override
  public ElementRuleType getType() {
    return ElementRuleType.MANDATORY;
  }

  public CertificateDataValidation convert(ElementRule rule) {
    final var value = (RuleExpression) rule.rule();
    return CertificateDataValidationMandatory.builder()
        .questionId(rule.id().id())
        .expression(value.value())
        .build();
  }
}
