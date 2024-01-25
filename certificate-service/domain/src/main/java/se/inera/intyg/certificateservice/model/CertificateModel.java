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
    if (specificationContainsActionType(CertificationActionType.CREATE)) {
      if (Boolean.FALSE.equals(actionEvaluation.getPatient().getDeceased())) {
        certificateActions.add(
            new CertificateAction(CertificationActionType.CREATE)
        );
      }
    }
    return certificateActions;
  }

  private boolean specificationContainsActionType(CertificationActionType certificationActionType) {
    return getCertificateActionSpecifications().stream()
        .anyMatch(
            action -> certificationActionType.equals(action.getCertificationActionType()));
  }
}
