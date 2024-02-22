package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.common.exception.ConcurrentModificationException;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationResult;

@Getter
@Builder
@EqualsAndHashCode
public class Certificate {

  private final CertificateId id;
  private final CertificateModel certificateModel;
  private final LocalDateTime created;
  private CertificateMetaData certificateMetaData;
  @Builder.Default
  private List<ElementData> elementData = Collections.emptyList();
  private Revision revision;
  @Builder.Default
  private Status status = Status.DRAFT;

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
        .map(certificateAction ->
            certificateAction.evaluate(Optional.of(this), addPatientIfMissing(actionEvaluation))
        )
        .orElse(false);
  }

  private ActionEvaluation addPatientIfMissing(ActionEvaluation actionEvaluation) {
    if (actionEvaluation.patient() != null) {
      return actionEvaluation;
    }
    return actionEvaluation.withPatient(certificateMetaData.patient());
  }

  public void updateMetadata(ActionEvaluation actionEvaluation) {
    certificateMetaData = CertificateMetaData.builder()
        .patient(
            actionEvaluation.patient() == null
                ? certificateMetaData.patient()
                : actionEvaluation.patient()
        )
        .issuer(Staff.create(actionEvaluation.user()))
        .careUnit(actionEvaluation.careUnit())
        .careProvider(actionEvaluation.careProvider())
        .issuingUnit(actionEvaluation.subUnit())
        .build();
  }

  public void updateData(List<ElementData> newData, Revision revision,
      ActionEvaluation actionEvaluation) {
    throwIfConcurrentModiciation(revision, "update", actionEvaluation);
    final var missingIds = newData.stream()
        .filter(newDataElement -> !certificateModel.elementSpecificationExists(newDataElement.id()))
        .map(newDataElement -> newDataElement.id().id())
        .reduce("", (s, s2) -> s.isBlank() ? s2 : s.concat(", " + s2));

    if (!missingIds.isEmpty()) {
      throw new IllegalArgumentException(
          "ElementIds '%s' are missing from certificateModelId '%s'"
              .formatted(missingIds, certificateModel.id())
      );
    }
    this.revision = this.revision.increment();
    this.elementData = newData.stream().toList();
  }

  public void delete(Revision revision, ActionEvaluation actionEvaluation) {
    throwIfConcurrentModiciation(revision, "delete", actionEvaluation);
    if (this.status != Status.DRAFT) {
      throw new IllegalStateException(
          "Incorrect status '%s' - required status is '%s' to delete".formatted(this.status,
              Status.DRAFT)
      );
    }
    this.status = Status.DELETED_DRAFT;
  }

  public ValidationResult validate() {
    return validate(this.elementData);
  }

  public ValidationResult validate(List<ElementData> elementData) {
    return ValidationResult.builder()
        .errors(
            certificateModel.elementSpecifications().stream()
                .map(elementSpecification ->
                    elementSpecification.validate(elementData, Optional.empty())
                )
                .flatMap(List::stream)
                .toList()
        )
        .build();
  }

  private void throwIfConcurrentModiciation(Revision revision, String operation,
      ActionEvaluation actionEvaluation) {
    if (!this.revision.equals(revision)) {
      throw new ConcurrentModificationException(
          "Incorrect revision '%s' != '%s' - unable to %s".formatted(revision, this.revision,
              operation),
          actionEvaluation.user(),
          actionEvaluation.subUnit()
      );
    }
  }

  public boolean isDraft() {
    return status().equals(Status.DRAFT);
  }
}

