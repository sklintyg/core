package se.inera.intyg.certificateservice.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    return certificateActionSpecifications.stream()
        .map(CertificateActionFactory::create)
        .filter(Objects::nonNull)
        .filter(certificateAction -> certificateAction.evaluate(actionEvaluation))
        .toList();
  }
}
