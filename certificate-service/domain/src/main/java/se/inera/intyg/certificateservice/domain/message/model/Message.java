package se.inera.intyg.certificateservice.domain.message.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@Getter
@Builder
@EqualsAndHashCode
public class Message {

  private MessageId id;
  private CertificateId certificateId;
  private PersonId personId;
  private SenderReference reference;
  private MessageType type;
  private Subject subject;
  private Content content;
  private Author author;
  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime sent;
  private MessageStatus status;
  private Forwarded forwarded;
  private LocalDate lastDateToReply;
  private MessageContactInfo contactInfo;
  @Builder.Default
  private List<Complement> complements = Collections.emptyList();
  private Answer answer;
  @Builder.Default
  private List<Reminder> reminders = Collections.emptyList();
  @Builder.Default
  private List<MessageAction> availableActions = Collections.emptyList();

  public List<MessageAction> actionsInclude(ActionEvaluation actionEvaluation,
      Certificate certificate) {
    return actions(actionEvaluation, certificate);
  }

  public List<MessageAction> actions(ActionEvaluation actionEvaluation,
      Certificate certificate) {
    final var messageActions = new ArrayList<MessageAction>();
    final var certificateActions = certificate.certificateModel()
        .actions(Optional.of(actionEvaluation));

    if (isUnhandledComplement() && actionAvailable(CertificateActionType.COMPLEMENT,
        certificateActions)) {
      messageActions.add(
          MessageActionFactory.complement()
      );
    }

    if (isUnhandledComplement() && actionAvailable(CertificateActionType.CANNOT_COMPLEMENT,
        certificateActions)) {
      messageActions.add(
          MessageActionFactory.cannotComplement()
      );
    }

    if (!status.equals(MessageStatus.HANDLED) && actionAvailable(
        CertificateActionType.FORWARD_MESSAGE, certificateActions)) {
      messageActions.add(
          MessageActionFactory.forward()
      );
    }

    return messageActions;
  }

  private boolean isUnhandledComplement() {
    return type.equals(MessageType.COMPLEMENT) && !status.equals(MessageStatus.HANDLED);
  }

  private boolean actionAvailable(CertificateActionType certificateActionType,
      List<CertificateAction> certificateActions) {
    return certificateActions.stream()
        .anyMatch(certificateAction -> certificateAction.getType()
            .equals(certificateActionType));
  }
}
