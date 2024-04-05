package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;

@Component
public class CertificateDataValidationTextConverter implements
    CertificateDataValidationConverter {

  @Override
  public ElementRuleType getType() {
    return ElementRuleType.TEXT_LIMIT;
  }

  public CertificateDataValidation convert(ElementRule rule) {
    final var value = (RuleLimit) rule.rule();
    return CertificateDataValidationText.builder()
        .id(rule.id().id())
        .limit(value.value().shortValue())
        .build();
  }
}
