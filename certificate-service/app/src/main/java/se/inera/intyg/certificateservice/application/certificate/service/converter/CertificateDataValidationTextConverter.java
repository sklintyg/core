package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;

@Component
public class CertificateDataValidationTextConverter implements
    CertificateDataValidationConverter {

  @Override
  public ElementRuleType getType() {
    return ElementRuleType.TEXT_LIMIT;
  }

  public CertificateDataValidation convert(ElementRule rule) {
    if (rule instanceof ElementRuleLimit elementRuleLimit) {
      return CertificateDataValidationText.builder()
          .id(elementRuleLimit.id().id())
          .limit(elementRuleLimit.limit().value())
          .build();
    }
    throw new IllegalStateException(
        "Invalid rule type. Type was '%s'".formatted(rule.type())
    );
  }
}
