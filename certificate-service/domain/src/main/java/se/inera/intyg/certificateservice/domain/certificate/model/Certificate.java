package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

@Data
@Builder
public class Certificate {

  private CertificateId id;
  private CertificateModel certificateModel;
  private CertificateMetaData certificateMetaData;

  public List<CertificateAction> actions(ActionEvaluation actionEvaluation) {
    return Collections.emptyList();
  }

  public void updateMetadata(ActionEvaluation actionEvaluation) {

  }


}
