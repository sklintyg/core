package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.action.message.model.MessageAction;
import se.inera.intyg.certificateservice.domain.action.message.model.MessageActionFactory;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.Role;

@Value
@Builder
public class CertificateModel {

  CertificateModelId id;
  Code type;
  String name;
  String description;
  String detailedDescription;
  Recipient recipient;
  LocalDateTime activeFrom;
  Boolean availableForCitizen;
  List<CertificateActionSpecification> certificateActionSpecifications;
  @Builder.Default
  List<MessageActionSpecification> messageActionSpecifications = Collections.emptyList();
  List<ElementSpecification> elementSpecifications;
  SchematronPath schematronPath;
  List<CertificateText> texts;
  CertificateSummaryProvider summaryProvider;
  List<Role> rolesWithAccess;
  List<CertificateMessageType> messageTypes;
  PdfSpecification pdfSpecification;

  public List<CertificateAction> actions() {
    return certificateActionSpecifications.stream()
        .map(CertificateActionFactory::create)
        .filter(Objects::nonNull)
        .toList();
  }

  public List<MessageAction> messageActions() {
    return messageActionSpecifications.stream()
        .map(MessageActionFactory::create)
        .filter(Objects::nonNull)
        .toList();
  }

  public List<CertificateAction> actions(Optional<ActionEvaluation> actionEvaluation) {
    return actions().stream()
        .filter(certificateAction ->
            certificateAction.evaluate(Optional.empty(), actionEvaluation)
        )
        .toList();
  }

  public List<CertificateAction> actionsInclude(Optional<ActionEvaluation> actionEvaluation) {
    return actions().stream()
        .filter(certificateAction -> certificateAction.include(Optional.empty(), actionEvaluation))
        .toList();
  }

  public boolean activeForUserRole(ActionEvaluation actionEvaluation) {
    return this.rolesWithAccess.contains(actionEvaluation.user().role());
  }

  public boolean allowTo(CertificateActionType certificateActionType,
      Optional<ActionEvaluation> actionEvaluation) {
    return actions().stream()
        .filter(certificateAction -> certificateActionType.equals(certificateAction.getType()))
        .findFirst()
        .map(certificateAction -> certificateAction.evaluate(Optional.empty(), actionEvaluation))
        .orElse(false);
  }

  public List<String> reasonNotAllowed(CertificateActionType certificateActionType,
      Optional<ActionEvaluation> actionEvaluation) {
    return actions().stream()
        .filter(certificateAction -> certificateActionType.equals(certificateAction.getType()))
        .findFirst()
        .map(certificateAction ->
            certificateAction.reasonNotAllowed(Optional.empty(), actionEvaluation)
        )
        .orElse(Collections.emptyList());
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

  public boolean certificateActionExists(CertificateActionType certificateActionType) {
    return certificateActionSpecifications()
        .stream()
        .anyMatch(
            specification -> specification.certificateActionType().equals(certificateActionType)
        );
  }
}
