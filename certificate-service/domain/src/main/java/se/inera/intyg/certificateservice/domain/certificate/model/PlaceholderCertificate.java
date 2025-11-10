package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.service.PrefillProcessor;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateGeneralPrintText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationResult;

@Getter
@Builder
@EqualsAndHashCode
public class PlaceholderCertificate implements Certificate {

  private final CertificateId id;
  private final LocalDateTime created;
  private Status status;
  private CertificateMetaData certificateMetaData;

  @Override
  public List<CertificateAction> actions(final Optional<ActionEvaluation> actionEvaluation) {
    throw new IllegalStateException("Cannot get actions for a placeholder certificate");
  }

  @Override
  public List<CertificateAction> actionsInclude(final Optional<ActionEvaluation> actionEvaluation) {
    throw new IllegalStateException("Cannot get included actions for a placeholder certificate");
  }

  @Override
  public boolean allowTo(final CertificateActionType certificateActionType,
      final Optional<ActionEvaluation> actionEvaluation) {
    throw new IllegalStateException(
        "Cannot check if action is allowed for a placeholder certificate");
  }

  @Override
  public List<String> reasonNotAllowed(final CertificateActionType certificateActionType,
      final Optional<ActionEvaluation> actionEvaluation) {
    throw new IllegalStateException("Cannot get reason not allowed for a placeholder certificate");
  }

  @Override
  public void updateMetadata(final ActionEvaluation actionEvaluation) {
    throw new IllegalStateException("Cannot update metadata for a placeholder certificate");
  }

  @Override
  public void updateData(final List<ElementData> newData, final Revision revision,
      final ActionEvaluation actionEvaluation) {
    throw new IllegalStateException("Cannot update data for a placeholder certificate");
  }

  @Override
  public void delete(final Revision revision, final ActionEvaluation actionEvaluation) {
    throw new IllegalStateException("Cannot delete a placeholder certificate");
  }

  @Override
  public ValidationResult validate() {
    return null;
  }

  @Override
  public ValidationResult validate(List<ElementData> elementData) {
    return null;
  }

  @Override
  public boolean isDraft() {
    return false;
  }

  @Override
  public void sign(final XmlGenerator xmlGenerator, final Revision revision,
      final ActionEvaluation actionEvaluation) {
    throw new IllegalStateException("Cannot sign a placeholder certificate");
  }

  @Override
  public void sign(final XmlGenerator xmlGenerator, final Signature signature,
      final Revision revision,
      final ActionEvaluation actionEvaluation) {
    throw new IllegalStateException("Cannot sign a placeholder certificate");
  }

  @Override
  public void send(final ActionEvaluation actionEvaluation) {
    throw new IllegalStateException("Cannot send a placeholder certificate");
  }

  @Override
  public void sendByCitizen() {
    throw new IllegalStateException("Cannot send a placeholder certificate by citizen");
  }

  @Override
  public void revoke(final ActionEvaluation actionEvaluation,
      final RevokedInformation revokedInformation) {
    if (this.status != Status.SIGNED) {
      throw new IllegalStateException(
          "Incorrect status '%s' - required status is '%s' or '%s' to revoke".formatted(this.status,
              Status.SIGNED, Status.LOCKED_DRAFT)
      );
    }

    this.status = Status.REVOKED;
  }

  @Override
  public void readyForSign(final ActionEvaluation actionEvaluation) {
    throw new IllegalStateException("Cannot set ready for sign for a placeholder certificate");
  }

  @Override
  public void externalReference(final ExternalReference externalReference) {
    throw new IllegalStateException("Cannot set external reference for a placeholder certificate");
  }

  @Override
  public Optional<ElementData> getElementDataById(ElementId id) {
    return Optional.empty();
  }

  @Override
  public Certificate replace(ActionEvaluation actionEvaluation) {
    return null;
  }

  @Override
  public Certificate complement(ActionEvaluation actionEvaluation) {
    return null;
  }

  @Override
  public Certificate renew(ActionEvaluation actionEvaluation) {
    return null;
  }

  @Override
  public boolean isSendActiveForCitizen() {
    throw new IllegalStateException(
        "Cannot check if send is active for citizen on a placeholder certificate");
  }

  @Override
  public boolean isCertificateIssuedOnPatient(PersonId citizen) {
    throw new IllegalStateException(
        "Cannot check if certificate is issued on patient for a placeholder certificate");
  }

  @Override
  public Optional<Relation> latestChildRelation(RelationType relationType) {
    throw new IllegalStateException(
        "Cannot get latest child relation for a placeholder certificate");
  }

  @Override
  public boolean hasParent(RelationType... relationType) {
    throw new IllegalStateException("Cannot check parent relation for a placeholder certificate");
  }

  @Override
  public List<Message> messages(MessageType type) {
    throw new IllegalStateException("Cannot get messages by type for a placeholder certificate");
  }

  @Override
  public void answerComplement(ActionEvaluation actionEvaluation, Content content) {
    throw new IllegalStateException("Cannot answer complement for a placeholder certificate");
  }

  @Override
  public void forwardMessages() {
    throw new IllegalStateException("Cannot forward messages for a placeholder certificate");
  }

  @Override
  public void forward() {
    throw new IllegalStateException("Cannot forward for a placeholder certificate");
  }

  @Override
  public void lock() {
    throw new IllegalStateException("Cannot lock a placeholder certificate");
  }

  @Override
  public boolean isWithinCareUnit(ActionEvaluation actionEvaluation) {
    throw new IllegalStateException("Cannot check care unit for a placeholder certificate");
  }

  @Override
  public boolean isWithinCareProvider(ActionEvaluation actionEvaluation) {
    throw new IllegalStateException("Cannot check care provider for a placeholder certificate");
  }

  @Override
  public Optional<ElementValueUnitContactInformation> unitContactInformation() {
    throw new IllegalStateException(
        "Cannot get unit contact information for a placeholder certificate");
  }

  @Override
  public void prefill(Xml prefillXml, PrefillProcessor prefillProcessor) {
    throw new IllegalStateException("Cannot prefill a placeholder certificate");
  }

  @Override
  public CertificateModel certificateModel() {
    throw new IllegalStateException("Cannot get certificate model for a placeholder certificate");
  }

  @Override
  public LocalDateTime signed() {
    throw new IllegalStateException("Cannot get signed date for a placeholder certificate");
  }

  @Override
  public LocalDateTime modified() {
    throw new IllegalStateException("Cannot get modified date for a placeholder certificate");
  }

  @Override
  public LocalDateTime locked() {
    throw new IllegalStateException("Cannot get locked date for a placeholder certificate");
  }

  @Override
  public ReadyForSign readyForSign() {
    throw new IllegalStateException("Cannot get ready for sign for a placeholder certificate");
  }

  @Override
  public List<ElementData> elementData() {
    throw new IllegalStateException("Cannot get element data for a placeholder certificate");
  }

  @Override
  public Revision revision() {
    throw new IllegalStateException("Cannot get revision for a placeholder certificate");
  }

  @Override
  public Xml xml() {
    throw new IllegalStateException("Cannot get XML for a placeholder certificate");
  }

  @Override
  public Sent sent() {
    throw new IllegalStateException("Cannot get sent information for a placeholder certificate");
  }

  @Override
  public Revoked revoked() {
    throw new IllegalStateException("Cannot get revoked information for a placeholder certificate");
  }

  @Override
  public ExternalReference externalReference() {
    throw new IllegalStateException("Cannot get external reference for a placeholder certificate");
  }

  @Override
  public Relation parent() {
    throw new IllegalStateException("Cannot get parent relation for a placeholder certificate");
  }

  @Override
  public List<Relation> children() {
    throw new IllegalStateException("Cannot get children relations for a placeholder certificate");
  }

  @Override
  public List<Message> messages() {
    return Collections.emptyList();
  }

  @Override
  public Forwarded forwarded() {
    throw new IllegalStateException(
        "Cannot get forwarded information for a placeholder certificate");
  }

  @Override
  public boolean isPlaceholder() {
    return true;
  }

  @Override
  public void fillFromCertificate(Certificate certificate) {
    throw new IllegalStateException("Cannot fill from certificate for a placeholder certificate");
  }

  @Override
  public Optional<Certificate> candidateForUpdate() {
    throw new IllegalStateException(
        "Cannot get candidate for update for a placeholder certificate"
    );
  }

  @Override
  public CertificateMetaData getMetadataForPrint() {
    throw new IllegalStateException(
        "Cannot get metadata for print for a placeholder certificate");
  }

  @Override
  public Optional<CertificateGeneralPrintText> getGeneralPrintText() {
    throw new IllegalStateException("Cannot get general print text for a placeholder certificate");
  }

  @Override
  public void updateMetadata(Patient patient) {
    throw new IllegalStateException("Cannot update metadata for a placeholder certificate");
  }

  @Override
  public void updateMetadata(CertificateMetaData metaData) {
    throw new IllegalStateException("Cannot update metadata for a placeholder certificate");
  }
}