package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDateTime;
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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
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
  public List<CertificateAction> actions(Optional<ActionEvaluation> actionEvaluation) {
    return List.of();
  }

  @Override
  public List<CertificateAction> actionsInclude(Optional<ActionEvaluation> actionEvaluation) {
    return List.of();
  }

  @Override
  public boolean allowTo(CertificateActionType certificateActionType,
      Optional<ActionEvaluation> actionEvaluation) {
    return false;
  }

  @Override
  public List<String> reasonNotAllowed(CertificateActionType certificateActionType,
      Optional<ActionEvaluation> actionEvaluation) {
    return List.of();
  }

  @Override
  public void updateMetadata(ActionEvaluation actionEvaluation) {

  }

  @Override
  public void updateData(List<ElementData> newData, Revision revision,
      ActionEvaluation actionEvaluation) {

  }

  @Override
  public void delete(Revision revision, ActionEvaluation actionEvaluation) {

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
  public void sign(XmlGenerator xmlGenerator, Revision revision,
      ActionEvaluation actionEvaluation) {

  }

  @Override
  public void sign(XmlGenerator xmlGenerator, Signature signature, Revision revision,
      ActionEvaluation actionEvaluation) {

  }

  @Override
  public void send(ActionEvaluation actionEvaluation) {

  }

  @Override
  public void sendByCitizen() {

  }

  @Override
  public void revoke(ActionEvaluation actionEvaluation, RevokedInformation revokedInformation) {

  }

  @Override
  public void readyForSign(ActionEvaluation actionEvaluation) {

  }

  @Override
  public void externalReference(ExternalReference externalReference) {

  }

  @Override
  public void parent(Relation relation) {

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
    return false;
  }

  @Override
  public boolean isCertificateIssuedOnPatient(PersonId citizen) {
    return false;
  }

  @Override
  public Optional<Relation> latestChildRelation(RelationType relationType) {
    return Optional.empty();
  }

  @Override
  public boolean hasParent(RelationType... relationType) {
    return false;
  }

  @Override
  public List<Message> messages(MessageType type) {
    return List.of();
  }

  @Override
  public void answerComplement(ActionEvaluation actionEvaluation, Content content) {

  }

  @Override
  public void forwardMessages() {

  }

  @Override
  public void forward() {

  }

  @Override
  public void lock() {

  }

  @Override
  public boolean isWithinCareUnit(ActionEvaluation actionEvaluation) {
    return false;
  }

  @Override
  public boolean isWithinCareProvider(ActionEvaluation actionEvaluation) {
    return false;
  }

  @Override
  public Optional<ElementValueUnitContactInformation> unitContactInformation() {
    return Optional.empty();
  }

  @Override
  public void prefill(Xml prefillXml, PrefillProcessor prefillProcessor, SubUnit subUnit) {

  }

  @Override
  public CertificateModel certificateModel() {
    return null;
  }

  @Override
  public LocalDateTime signed() {
    return null;
  }

  @Override
  public LocalDateTime modified() {
    return null;
  }

  @Override
  public LocalDateTime locked() {
    return null;
  }

  @Override
  public ReadyForSign readyForSign() {
    return null;
  }

  @Override
  public List<ElementData> elementData() {
    return List.of();
  }

  @Override
  public Revision revision() {
    return null;
  }

  @Override
  public Xml xml() {
    return null;
  }

  @Override
  public Sent sent() {
    return null;
  }

  @Override
  public Revoked revoked() {
    return null;
  }

  @Override
  public ExternalReference externalReference() {
    return null;
  }

  @Override
  public Relation parent() {
    return null;
  }

  @Override
  public List<Relation> children() {
    return List.of();
  }

  @Override
  public List<Message> messages() {
    return List.of();
  }

  @Override
  public Forwarded forwarded() {
    return null;
  }

  @Override
  public boolean isPlaceholder() {
    return true;
  }
}