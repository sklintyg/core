package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.action.message.model.MessageAction;
import se.inera.intyg.certificateservice.domain.action.message.model.MessageActionFactory;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;

@Value
@Builder
@RequiredArgsConstructor
public class CertificateModel implements Comparator<ElementId> {

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
  List<CertificateMessageType> messageTypes;
  PdfSpecification pdfSpecification;
  CertificateConfirmationModalProvider confirmationModalProvider;
  @With
  CertificateActionFactory certificateActionFactory;
  SickLeaveProvider sickLeaveProvider;
  CertificateAvailableFunctionsProvider availableFunctionsProvider;


  public List<CertificateAction> actions() {
    return certificateActionSpecifications.stream()
        .map(certificateActionFactory::create)
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

  public Optional<CertificateActionSpecification> certificateAction(
      CertificateActionType certificateActionType) {
    return certificateActionSpecifications().stream()
        .filter(
            specification -> specification.certificateActionType().equals(certificateActionType)
        )
        .findFirst();
  }

  @Override
  public int compare(ElementId elementId1, ElementId elementId2) {
    final var flattened = flatten(elementSpecifications);
    return Integer.compare(flattened.indexOf(elementId1), flattened.indexOf(elementId2));
  }

  private static List<ElementId> flatten(List<ElementSpecification> elementSpecifications) {
    if (elementSpecifications.isEmpty()) {
      return Collections.emptyList();
    }

    return elementSpecifications.stream()
        .flatMap(element ->
            Stream.concat(
                Stream.of(element.id()),
                flatten(element.children()).stream()
            )
        )
        .toList();
  }
}