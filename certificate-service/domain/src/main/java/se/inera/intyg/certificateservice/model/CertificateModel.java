package se.inera.intyg.certificateservice.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CertificateModel {

  CertificateModelId id;
  String name;
  String description;
  LocalDateTime activeFrom;
  List<CertificateActionSpecification> certificateActionSpecifications;

  public List<CertificateAction> actions(ActionEvaluation actionEvaluation) {
    final var certificateActions = new ArrayList<CertificateAction>();
    if (specificationContainsActionType(CertificateActionType.CREATE)) {
      if (Boolean.FALSE.equals(actionEvaluation.getPatient().getDeceased())) {
        certificateActions.add(
            new CertificateAction(CertificateActionType.CREATE)
        );
      }
    }
    return certificateActions;
  }

  private boolean specificationContainsActionType(CertificateActionType certificateActionType) {
    return getCertificateActionSpecifications().stream()
        .anyMatch(
            action -> certificateActionType.equals(action.getCertificateActionType()));
  }
}
