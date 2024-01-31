package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;


@Builder
@EqualsAndHashCode
public class Certificate {

  private final CertificateId id;
  private final CertificateModel certificateModel;
  private CertificateMetaData certificateMetaData;

  public List<CertificateAction> actions(ActionEvaluation actionEvaluation) {
    return Collections.emptyList();
  }

  public void updateMetadata(ActionEvaluation actionEvaluation) {

  }

  public CertificateId getId() {
    return id;
  }

  public CertificateModel getCertificateModel() {
    return certificateModel;
  }

  public CertificateMetaData getCertificateMetaData() {
    return certificateMetaData;
  }
}
