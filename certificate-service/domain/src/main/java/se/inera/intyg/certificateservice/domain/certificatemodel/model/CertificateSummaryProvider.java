package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public interface CertificateSummaryProvider {

  CertificateSummary summaryOf(Certificate certificate);

}
