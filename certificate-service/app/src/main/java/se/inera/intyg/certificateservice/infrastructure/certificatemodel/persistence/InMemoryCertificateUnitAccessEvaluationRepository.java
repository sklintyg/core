package se.inera.intyg.certificateservice.infrastructure.certificatemodel.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateUnitAccessEvaluationRepository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class InMemoryCertificateUnitAccessEvaluationRepository implements
    CertificateUnitAccessEvaluationRepository {

  @Override
  public boolean evaluate(ActionEvaluation actionEvaluation, CertificateModel certificateModel) {
    return false;
  }
}
