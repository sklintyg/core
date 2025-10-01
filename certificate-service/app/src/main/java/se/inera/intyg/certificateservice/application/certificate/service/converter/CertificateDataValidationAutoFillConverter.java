package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationAutoFill;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleAutofill;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;

@Component
public class CertificateDataValidationAutoFillConverter implements
    CertificateDataValidationConverter {

  @Override
  public ElementRuleType getType() {
    return ElementRuleType.AUTO_FILL;
  }

  @Override
  public CertificateDataValidation convert(ElementRule rule) {
    if (!(rule instanceof ElementRuleAutofill elementRuleAutofill)) {
      throw new IllegalStateException(
          "Invalid rule type. Type was '%s'".formatted(rule.type())
      );
    }
    return CertificateDataValidationAutoFill.builder()
        .questionId(elementRuleAutofill.id().id())
        .expression(elementRuleAutofill.expression().value())
        .fillValue(convertAutoFillValue(elementRuleAutofill.fillValue()))
        .build();
  }

  private CertificateDataValue convertAutoFillValue(ElementValueBoolean valueBoolean) {

    if (valueBoolean == null) {
      throw new IllegalStateException("Auto fill value is null");
    }

    if (!valueBoolean.type().equals("boolean")) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(valueBoolean.type()));
    }

    return CertificateDataValueBoolean.builder()
        .id(valueBoolean.id())
        .selected(valueBoolean.selected())
        .build();
  }
}
