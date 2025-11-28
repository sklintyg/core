package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.List;
import java.util.Optional;
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

public interface Certificate {

  List<CertificateAction> actions(Optional<ActionEvaluation> actionEvaluation);

  List<CertificateAction> actionsInclude(Optional<ActionEvaluation> actionEvaluation);

  boolean allowTo(CertificateActionType certificateActionType,
      Optional<ActionEvaluation> actionEvaluation);

  List<String> reasonNotAllowed(CertificateActionType certificateActionType,
      Optional<ActionEvaluation> actionEvaluation);

  void updateMetadata(ActionEvaluation actionEvaluation);

  void updateData(List<ElementData> newData, Revision revision,
      ActionEvaluation actionEvaluation);

  void delete(Revision revision, ActionEvaluation actionEvaluation);

  ValidationResult validate();

  ValidationResult validate(List<ElementData> elementData);

  boolean isDraft();

  void sign(XmlGenerator xmlGenerator, Revision revision,
      ActionEvaluation actionEvaluation);

  void sign(XmlGenerator xmlGenerator, Signature signature, Revision revision,
      ActionEvaluation actionEvaluation);

  void send(ActionEvaluation actionEvaluation);

  void sendByCitizen();

  void revoke(ActionEvaluation actionEvaluation, RevokedInformation revokedInformation);

  void readyForSign(ActionEvaluation actionEvaluation);

  void externalReference(ExternalReference externalReference);

  Optional<ElementData> getElementDataById(ElementId id);

  Certificate replace(ActionEvaluation actionEvaluation);

  Certificate complement(ActionEvaluation actionEvaluation);

  Certificate renew(ActionEvaluation actionEvaluation);

  boolean isSendActiveForCitizen();

  boolean isCertificateIssuedOnPatient(PersonId citizen);

  Optional<Relation> latestChildRelation(RelationType relationType);

  boolean hasParent(RelationType... relationType);

  List<Message> messages(MessageType type);

  void answerComplement(ActionEvaluation actionEvaluation, Content content);

  void forwardMessages();

  void forward();

  void lock();

  boolean isWithinCareUnit(ActionEvaluation actionEvaluation);

  boolean isWithinCareProvider(ActionEvaluation actionEvaluation);

  Optional<ElementValueUnitContactInformation> unitContactInformation();

  void prefill(Xml prefillXml, PrefillProcessor prefillProcessor);

  CertificateId id();

  CertificateModel certificateModel();

  java.time.LocalDateTime created();

  java.time.LocalDateTime signed();

  java.time.LocalDateTime modified();

  java.time.LocalDateTime locked();

  ReadyForSign readyForSign();

  CertificateMetaData certificateMetaData();

  List<ElementData> elementData();

  Revision revision();

  Status status();

  Xml xml();

  Sent sent();

  Revoked revoked();

  ExternalReference externalReference();

  Relation parent();

  List<Relation> children();

  default boolean isReplaced() {
    return false;
  }

  default boolean isComplemented() {
    return false;
  }

  List<Message> messages();

  Forwarded forwarded();

  default boolean isPlaceholder() {
    return false;
  }

  void fillFromCertificate(Certificate certificate);

  Optional<Certificate> candidateForUpdate();

  CertificateMetaData getMetadataForPrint();

  Optional<CertificateGeneralPrintText> getGeneralPrintText();

  void updateMetadata(Patient patient);

  void updateMetadata(CertificateMetaData updatedMetadata);
}