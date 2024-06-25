package se.inera.intyg.certificateservice.domain.message.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
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
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@Getter
@Builder
@EqualsAndHashCode
public class Message {

  private final MessageId id;
  private final CertificateId certificateId;
  private final PersonId personId;
  private final SenderReference reference;
  private MessageType type;
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
  private Answer answer;
  @Builder.Default
  private List<Reminder> reminders = Collections.emptyList();
  private Staff authoredStaff;

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

    if (this.authoredStaff == null && !type.equals(MessageType.COMPLEMENT) && this.answer == null
        && actionAvailable(CertificateActionType.ANSWER_MESSAGE, certificateActions)) {
      messageActions.add(
          MessageActionFactory.answer()
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


  public static Message create(MessageType messageType, Content content,
      CertificateId certificateId, Staff staff) {
    return Message.builder()
        .id(new MessageId(UUID.randomUUID().toString()))
        .author(new Author(staff.name().fullName()))
        .authoredStaff(staff)
        .type(messageType)
        .content(content)
        .subject(new Subject(messageType.displayName()))
        .status(MessageStatus.DRAFT)
        .forwarded(new Forwarded(false))
        .certificateId(certificateId)
        .build();
  }

  public void update(Content content, MessageType messageType, Staff staff, Subject subject) {
    this.content = content;
    this.type = messageType;
    this.authoredStaff = staff;
    this.subject = subject;
  }

  public void send() {
    this.status = MessageStatus.SENT;
    this.sent = LocalDateTime.now();
  }

  public void delete() {
    if (this.status != MessageStatus.DRAFT) {
      throw new IllegalStateException(
          "Incorrect status '%s' - required status is '%s' to delete".formatted(this.status,
              MessageStatus.DRAFT)
      );
    }
    this.status = MessageStatus.DELETED_DRAFT;
  }

  public void answer(Answer answer) {
    this.answer = answer;
  }

  public void saveAnswer(Staff staff, Content content) {
    this.answer = Answer.builder()
        .id(answer != null ? answer.id() : new MessageId(UUID.randomUUID().toString()))
        .subject(subject)
        .content(content)
        .status(MessageStatus.DRAFT)
        .author(new Author(staff.name().fullName()))
        .authoredStaff(staff)
        .build();
  }

  public void deleteAnswer() {
    if (this.answer == null) {
      throw new IllegalStateException(
          "Can`t delete answer because answer is null"
      );
    }
    if (this.answer.status() != MessageStatus.DRAFT) {
      throw new IllegalStateException(
          "Incorrect status '%s' - required status is '%s' to delete answer".formatted(this.status,
              MessageStatus.DRAFT)
      );
    }
    this.answer.updateStatus(MessageStatus.DELETED_DRAFT);
  }
}
