package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;

public interface SickLeaveProvider {

  default Optional<SickLeaveCertificate> build(Certificate certificate, boolean ignoreModelRules) {
    return build(certificate);
  }

  Optional<SickLeaveCertificate> build(Certificate certificate);
}