package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationMandatory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;

@Component
public class CertificateDataValidationConverter {

  public CertificateDataValidation[] convert(List<ElementRule> rules) {
    if (rules == null) {
      return new CertificateDataValidation[0];
    }
    return rules.stream()
        .map(CertificateDataValidationConverter::getCertificateDataValidation)
        .toArray(CertificateDataValidation[]::new);
  }

  private static CertificateDataValidation getCertificateDataValidation(
      ElementRule rule) {
    return switch (rule.type()) {
      case MANDATORY -> CertificateDataValidationMandatory.builder()
          .questionId(rule.id().id())
          .expression(rule.expression().value())
          .build();
    };
  }
}
