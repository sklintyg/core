package se.inera.intyg.certificateservice.domain.message.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;
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

  private final MessageId id;
  private final CertificateId certificateId;
  private final PersonId personId;
  private final SenderReference reference;
  private final MessageType type;
  private Subject subject;
  private Content content;
  private final Author author;
  private final LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime sent;
  private MessageStatus status;
  private Forwarded forwarded;
  private LocalDate lastDateToReply;
  private MessageContactInfo contactInfo;
  @Builder.Default
  private final List<Complement> complements = Collections.emptyList();
  @With
  private Answer answer;
  @Builder.Default
  private List<Reminder> reminders = Collections.emptyList();

  public List<MessageAction> actionsInclude(ActionEvaluation actionEvaluation,
      Certificate certificate) {
    return actions(actionEvaluation, certificate);
  }

  public List<MessageAction> actions(ActionEvaluation actionEvaluation,
      Certificate certificate) {
    final var messageActions = new ArrayList<MessageAction>();
    final var certificateActions = certificate.actionsInclude(Optional.of(actionEvaluation));

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

    if (isUnhandledComplement() && actionAvailable(CertificateActionType.HANDLE_COMPLEMENT,
        certificateActions)) {
      messageActions.add(
          MessageActionFactory.handleComplement()
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

  public void handle() {
    if (this.status != MessageStatus.HANDLED) {
      this.status = MessageStatus.HANDLED;
      this.modified = LocalDateTime.now(ZoneId.systemDefault());
    }
  }

  public void remind(Reminder reminder) {
    this.reminders = Stream.concat(
            this.reminders.stream(),
            Stream.of(reminder)
        )
        .toList();
  }
}
