package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public interface CertificateConfirmationModalProvider {

  CertificateConfirmationModal of(Certificate certificate, ActionEvaluation actionEvaluation);

}
