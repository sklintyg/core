package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;

public interface SickLeaveProvider {

  SickLeaveCertificate build(Certificate certificate);
}