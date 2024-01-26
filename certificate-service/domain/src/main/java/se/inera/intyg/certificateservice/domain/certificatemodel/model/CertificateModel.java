package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionFactory;

@Value
@Builder
public class CertificateModel {

  CertificateModelId id;
  String name;
  String description;
  LocalDateTime activeFrom;
  List<CertificateActionSpecification> certificateActionSpecifications;

  public List<CertificateAction> actions(ActionEvaluation actionEvaluation) {
    return certificateActionSpecifications.stream()
        .map(CertificateActionFactory::create)
        .filter(Objects::nonNull)
        .filter(certificateAction -> certificateAction.evaluate(actionEvaluation))
        .toList();
  }
}
