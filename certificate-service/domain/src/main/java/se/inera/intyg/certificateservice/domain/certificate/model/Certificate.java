package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.exception.ConcurrentModificationException;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
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
  private LocalDateTime modified;
  private CertificateMetaData certificateMetaData;
  @Builder.Default
  private List<ElementData> elementData = Collections.emptyList();
  private Revision revision;
  @Builder.Default
  private Status status = Status.DRAFT;
  private Xml xml;
  private Sent sent;
  private Revoked revoked;
  private ExternalReference externalReference;
  private Relation parent;
  @Builder.Default
  private List<Relation> children = Collections.emptyList();
  @Builder.Default
  private List<Message> messages = Collections.emptyList();

  public List<CertificateAction> actions(Optional<ActionEvaluation> actionEvaluation) {
    return certificateModel.actions().stream()
        .filter(
            certificateAction -> certificateAction.evaluate(
                Optional.of(this),
                addPatientIfMissing(actionEvaluation)
            )
        )
        .toList();
  }

  public List<CertificateAction> actionsInclude(Optional<ActionEvaluation> actionEvaluation) {
    return certificateModel.actions().stream()
        .filter(certificateAction -> certificateAction.include(Optional.of(this),
            addPatientIfMissing(actionEvaluation)))
        .toList();
  }

  public boolean allowTo(CertificateActionType certificateActionType,
      Optional<ActionEvaluation> actionEvaluation) {
    return certificateModel.actions().stream()
        .filter(certificateAction -> certificateActionType.equals(certificateAction.getType()))
        .findFirst()
        .map(certificateAction ->
            certificateAction.evaluate(
                Optional.of(this),
                addPatientIfMissing(actionEvaluation)
            )
        )
        .orElse(false);
  }

  public List<String> reasonNotAllowed(CertificateActionType certificateActionType,
      Optional<ActionEvaluation> actionEvaluation) {
    return certificateModel.actions().stream()
        .filter(certificateAction -> certificateActionType.equals(certificateAction.getType()))
        .findFirst()
        .map(certificateAction -> certificateAction.reasonNotAllowed(Optional.of(this),
            addPatientIfMissing(actionEvaluation)))
        .orElse(Collections.emptyList());
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
    this.xml = xmlGenerator.generate(this, true);
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

  private Optional<ActionEvaluation> addPatientIfMissing(
      Optional<ActionEvaluation> actionEvaluation) {
    if (actionEvaluation.isEmpty()) {
      return actionEvaluation;
    }
    if (actionEvaluation.get().patient() != null) {
      return actionEvaluation;
    }
    return Optional.of(
        actionEvaluation.get().withPatient(certificateMetaData.patient())
    );
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

  public void revoke(ActionEvaluation actionEvaluation, RevokedInformation revokedInformation) {
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

    this.status = Status.REVOKED;
    this.revoked = Revoked.builder()
        .revokedInformation(revokedInformation)
        .revokedBy(Staff.create(actionEvaluation.user()))
        .revokedAt(LocalDateTime.now(ZoneId.systemDefault()))
        .build();
  }

  public void externalReference(ExternalReference externalReference) {
    if (this.externalReference != null) {
      throw new IllegalStateException(
          "Certificate with id '%s' already has an external reference".formatted(id().id()));
    }
    this.externalReference = externalReference;
  }

  public Optional<ElementData> getElementDataById(ElementId id) {
    return elementData.stream()
        .filter(data -> id.id().equals(data.id().id()))
        .findFirst();
  }

  public Certificate replace(ActionEvaluation actionEvaluation) {
    final var newCertificate = createCertificate(actionEvaluation, RelationType.REPLACE);

    newCertificate.elementData = this.elementData().stream().toList();

    return newCertificate;
  }

  public Certificate complement(ActionEvaluation actionEvaluation) {
    final var newCertificate = createCertificate(actionEvaluation, RelationType.COMPLEMENT);

    newCertificate.elementData = this.elementData().stream().toList();

    return newCertificate;
  }

  public Certificate renew(ActionEvaluation actionEvaluation) {
    final var newCertificate = createCertificate(actionEvaluation, RelationType.RENEW);

    newCertificate.elementData = this.elementData().stream()
        .filter(data ->
            !certificateModel.elementSpecificationExists(data.id())
                || certificateModel.elementSpecification(data.id()).includeWhenRenewing()
        )
        .toList();

    return newCertificate;
  }

  private Certificate createCertificate(ActionEvaluation actionEvaluation,
      RelationType relationType) {
    final var newCertificate = Certificate.builder()
        .id(new CertificateId(UUID.randomUUID().toString()))
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(this.certificateModel())
        .revision(new Revision(0))
        .build();

    newCertificate.certificateMetaData = this.certificateMetaData();
    newCertificate.updateMetadata(actionEvaluation);

    newCertificate.parent = Relation.builder()
        .certificate(this)
        .type(relationType)
        .created(newCertificate.created())
        .build();

    this.children = Stream.concat(
        this.children().stream(),
        Stream.of(
            Relation.builder()
                .certificate(newCertificate)
                .type(relationType)
                .created(newCertificate.parent().created())
                .build()
        )
    ).toList();

    return newCertificate;
  }

  public boolean isSendActiveForCitizen() {
    if (this.status() != Status.SIGNED) {
      return false;
    }

    if (this.certificateModel().recipient() == null) {
      return false;
    }

    if (this.sent() != null && this.sent().sentAt() != null) {
      return false;
    }

    return this.children().stream()
        .noneMatch(relation -> relation.type() == RelationType.REPLACE
            && relation.certificate().status() == Status.SIGNED
        );
  }

  public boolean isCertificateIssuedOnPatient(PersonId citizen) {
    return this.certificateMetaData().patient().id().idWithoutDash()
        .equals(citizen.idWithoutDash());
  }

  public Optional<Relation> latestChildRelation(RelationType relationType) {
    return this.children().stream()
        .filter(child -> child.type().equals(relationType))
        .max(Comparator.comparing(Relation::created));
  }

  public boolean hasParent(RelationType... relationType) {
    if (this.parent() == null) {
      return false;
    }
    return Arrays.stream(relationType).anyMatch(type -> this.parent.type().equals(type));
  }

  public List<Message> messages(MessageType type) {
    return messages.stream()
        .filter(message -> message.type().equals(type))
        .toList();
  }

  public void answerComplement(ActionEvaluation actionEvaluation, Content content) {
    this.messages = this.messages.stream()
        .map(message -> {
          if (message.type().equals(MessageType.COMPLEMENT)) {
            return message.withAnswer(
                Answer.builder()
                    .id(message.id())
                    .type(message.type())
                    .created(LocalDateTime.now())
                    .subject(message.subject())
                    .content(content)
                    .modified(LocalDateTime.now())
                    .sent(LocalDateTime.now())
                    .status(MessageStatus.HANDLED)
                    .author(new Author("WC"))
                    .authoredStaff(Staff.create(actionEvaluation.user()))
                    .build()
            );
          } else {
            return message;
          }
        })
        .toList();
  }
}

