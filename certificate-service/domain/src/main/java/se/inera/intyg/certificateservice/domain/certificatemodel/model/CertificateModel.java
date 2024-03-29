package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;

@Value
@Builder
public class CertificateModel {

  CertificateModelId id;
  Code type;
  String name;
  String description;
  Recipient recipient;
  LocalDateTime activeFrom;
  List<CertificateActionSpecification> certificateActionSpecifications;
  List<ElementSpecification> elementSpecifications;
  String pdfTemplatePath;

  public List<CertificateAction> actions() {
    return certificateActionSpecifications.stream()
        .map(CertificateActionFactory::create)
        .filter(Objects::nonNull)
        .toList();
  }

  public List<CertificateAction> actions(ActionEvaluation actionEvaluation) {
    return actions().stream()
        .filter(certificateAction -> certificateAction.evaluate(actionEvaluation))
        .toList();
  }

  public boolean allowTo(CertificateActionType certificateActionType,
      ActionEvaluation actionEvaluation) {
    return actions().stream()
        .filter(certificateAction -> certificateActionType.equals(certificateAction.getType()))
        .findFirst()
        .map(certificateAction -> certificateAction.evaluate(actionEvaluation))
        .orElse(false);
  }

  public boolean elementSpecificationExists(ElementId id) {
    return elementSpecifications.stream()
        .anyMatch(elementSpecification -> elementSpecification.exists(id));
  }

  public ElementSpecification elementSpecification(ElementId id) {
    return elementSpecifications.stream()
        .filter(elementSpecification -> elementSpecification.exists(id))
        .map(elementSpecification -> elementSpecification.elementSpecification(id))
        .findAny()
        .orElseThrow(
            () -> new IllegalArgumentException("No element with id '%s' exists".formatted(id))
        );
  }
}
