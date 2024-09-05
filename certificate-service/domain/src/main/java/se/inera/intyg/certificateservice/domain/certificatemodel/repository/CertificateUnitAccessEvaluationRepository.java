package se.inera.intyg.certificateservice.domain.certificatemodel.repository;

import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

public interface CertificateUnitAccessEvaluationRepository {

  boolean evaluate(ActionEvaluation actionEvaluation, CertificateModel certificateModel);
}
