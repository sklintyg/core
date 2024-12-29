package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.Collections;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationDisableSubElement;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class CertificateDataValidationDisableSubElementConverter implements
    CertificateDataValidationConverter {

  @Override
  public ElementRuleType getType() {
    return ElementRuleType.DISABLE_SUB_ELEMENT;
  }

  public CertificateDataValidation convert(ElementRule rule) {
    if (rule instanceof ElementRuleExpression elementRuleExpression) {
      return CertificateDataValidationDisableSubElement.builder()
          .questionId(elementRuleExpression.id().id())
          .expression(elementRuleExpression.expression().value())
          .id(elementRuleExpression
              .affectedSubElements() != null ? elementRuleExpression
              .affectedSubElements()
              .stream()
              .map(FieldId::value)
              .toList() : Collections.emptyList()
          )
          .build();
    }
    throw new IllegalStateException(
        "Invalid rule type. Type was '%s'".formatted(rule.type())
    );
  }
}
