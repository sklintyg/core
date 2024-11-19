package se.inera.intyg.certificateservice.application.certificate.service.converter;

import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;

public interface CertificateDataValidationConverter {

  ElementRuleType getType();

  CertificateDataValidation convert(ElementRule rule);
}
