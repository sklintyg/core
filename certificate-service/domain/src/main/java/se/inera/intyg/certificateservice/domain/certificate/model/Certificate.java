package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

@Getter
@Builder
@EqualsAndHashCode
@Accessors(fluent = true)
public class Certificate {

  private final CertificateId id;
  private final CertificateModel certificateModel;
  private final LocalDateTime created;
  private CertificateMetaData certificateMetaData;

  public List<CertificateAction> actions(ActionEvaluation actionEvaluation) {
    return certificateModel.actions().stream()
        .filter(
            certificateAction -> certificateAction.evaluate(Optional.of(this), actionEvaluation)
        )
        .toList();
  }

  public boolean allowTo(CertificateActionType certificateActionType,
      ActionEvaluation actionEvaluation) {
    return certificateModel.actions().stream()
        .filter(certificateAction -> certificateActionType.equals(certificateAction.getType()))
        .findFirst()
        .map(certificateAction -> certificateAction.evaluate(Optional.of(this), actionEvaluation))
        .orElse(false);
  }

  public void updateMetadata(ActionEvaluation actionEvaluation) {
    certificateMetaData = CertificateMetaData.builder()
        .patient(actionEvaluation.getPatient())
        .issuer(Staff.create(actionEvaluation.getUser()))
        .careUnit(actionEvaluation.getCareUnit())
        .careProvider(actionEvaluation.getCareProvider())
        .issuingUnit(actionEvaluation.getSubUnit())
        .build();
  }
}
