package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.common.exception.ConcurrentModificationException;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationResult;

@Getter
@Builder
@EqualsAndHashCode
public class Certificate {

  private final CertificateId id;
  private final CertificateModel certificateModel;
  private final LocalDateTime created;
  private LocalDateTime signed;
  private CertificateMetaData certificateMetaData;
  @Builder.Default
  private List<ElementData> elementData = Collections.emptyList();
  private Revision revision;
  @Builder.Default
  private Status status = Status.DRAFT;
  private Xml xml;
  private Sent sent;
  private Revoked revoked;

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
    throwIfConcurrentModification(revision, "update", actionEvaluation);
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
    throwIfConcurrentModification(revision, "delete", actionEvaluation);
    if (this.status != Status.DRAFT) {
      throw new IllegalStateException(
          "Incorrect status '%s' - required status is '%s' to delete".formatted(this.status,
              Status.DRAFT)
      );
    }
    this.status = Status.DELETED_DRAFT;
    this.revision = this.revision.increment();
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

  public boolean isDraft() {
    return status().equals(Status.DRAFT);
  }

  public void sign(XmlGenerator xmlGenerator, Revision revision,
      ActionEvaluation actionEvaluation) {
    sign(revision, actionEvaluation);
    this.xml = xmlGenerator.generate(this);
  }

  public void sign(XmlGenerator xmlGenerator, Signature signature, Revision revision,
      ActionEvaluation actionEvaluation) {
    if (signature == null || signature.isEmpty()) {
      throw new IllegalArgumentException(
          "Incorrect signature '%s' - signature required to sign".formatted(signature)
      );
    }
    sign(revision, actionEvaluation);
    this.xml = xmlGenerator.generate(this, signature);
  }

  private void sign(Revision revision, ActionEvaluation actionEvaluation) {
    throwIfConcurrentModification(revision, "sign", actionEvaluation);
    if (this.status != Status.DRAFT) {
      throw new IllegalStateException(
          "Incorrect status '%s' - required status is '%s' to sign".formatted(this.status,
              Status.DRAFT)
      );
    }

    final var validationResult = validate();
    if (validationResult.isInvalid()) {
      throw new IllegalArgumentException(
          "Certificate '%s' cannot be signed as it is not valid".formatted(id())
      );
    }

    this.status = Status.SIGNED;
    this.signed = LocalDateTime.now(ZoneId.systemDefault());
    this.revision = this.revision.increment();
  }

  private ActionEvaluation addPatientIfMissing(ActionEvaluation actionEvaluation) {
    if (actionEvaluation.patient() != null) {
      return actionEvaluation;
    }
    return actionEvaluation.withPatient(certificateMetaData.patient());
  }

  private void throwIfConcurrentModification(Revision revision, String operation,
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

  public void send(ActionEvaluation actionEvaluation) {
    if (this.status != Status.SIGNED) {
      throw new IllegalStateException(
          "Incorrect status '%s' - required status is '%s' to send".formatted(this.status,
              Status.SIGNED)
      );
    }

    if (this.sent != null) {
      throw new IllegalStateException(
          "Certificate with id '%s' has already been sent to '%s'.".formatted(id(),
              this.sent.recipient().name())
      );
    }

    this.sent = Sent.builder()
        .recipient(certificateModel.recipient())
        .sentBy(Staff.create(actionEvaluation.user()))
        .sentAt(LocalDateTime.now(ZoneId.systemDefault()))
        .build();
  }

  public void revoke(ActionEvaluation actionEvaluation, String revokeReason, String revokeMessage) {
    if (this.status != Status.SIGNED) {
      throw new IllegalStateException(
          "Incorrect status '%s' - required status is '%s' or '%s' to revoke".formatted(this.status,
              Status.SIGNED, Status.LOCKED_DRAFT)
      );
    }

    if (this.revoked != null) {
      throw new IllegalStateException(
          "Certificate with id '%s' has already been revoked".formatted(id().id())
      );
    }

    this.revoked = Revoked.builder()
        .revokedInformation(
            new RevokedInformation(revokeReason, revokeMessage)
        )
        .revokedBy(Staff.create(actionEvaluation.user()))
        .revokedAt(LocalDateTime.now(ZoneId.systemDefault()))
        .build();
  }
}

