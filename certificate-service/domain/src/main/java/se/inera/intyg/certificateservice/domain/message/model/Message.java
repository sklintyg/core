package se.inera.intyg.certificateservice.domain.message.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.action.message.model.MessageAction;
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
  private List<MessageAction> messageActions;

  public List<MessageActionLink> actionsInclude(ActionEvaluation actionEvaluation,
      Certificate certificate) {
    return actions(actionEvaluation, certificate);
  }

  public List<MessageActionLink> actions(ActionEvaluation actionEvaluation,
      Certificate certificate) {
    final var certificateActions = certificate.actionsInclude(Optional.of(actionEvaluation));
    return certificate.certificateModel().messageActions().stream()
        .filter(messageAction -> messageAction.evaluate(certificateActions, this))
        .map(MessageAction::actionLink)
        .toList();
  }

  public boolean isUnhandledComplement() {
    return type.equals(MessageType.COMPLEMENT) && !status.equals(MessageStatus.HANDLED);
  }

  public boolean isAdministrativeMessage() {
    return !type.equals(MessageType.COMPLEMENT);
  }

  public boolean actionAvailable(CertificateActionType certificateActionType,
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

  public void unhandle() {
    if (this.status == MessageStatus.HANDLED) {
      this.status = MessageStatus.SENT;
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
    if (answer == null) {
      this.answer = Answer.builder()
          .id(new MessageId(UUID.randomUUID().toString()))
          .subject(subject)
          .content(content)
          .status(MessageStatus.DRAFT)
          .author(new Author(staff.name().fullName()))
          .authoredStaff(staff)
          .build();
    } else {
      answer.save(staff, content);
    }
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
    this.answer.delete();
  }

  public void sendAnswer(Staff staff, Content content) {
    if (type(List.of(MessageType.COMPLEMENT, MessageType.REMINDER, MessageType.MISSING,
        MessageType.ANSWER))) {
      throw new IllegalStateException(
          "Incorrect type '%s' - required type is '%s' or '%s' to send answer".formatted(
              this.type,
              MessageType.CONTACT, MessageType.OTHER)
      );
    }
    this.answer.send(staff, content);
    this.handle();
  }

  public boolean type(List<MessageType> messageTypes) {
    return messageTypes.stream().anyMatch(messageType -> messageType.equals(this.type));
  }

  public boolean isSent() {
    return this.status == MessageStatus.SENT;
  }

  public boolean isHandled() {
    return this.status == MessageStatus.HANDLED;
  }

  public void forward() {
    if (isSent()) {
      this.forwarded = new Forwarded(true);
    }
  }

  public boolean hasAnswer() {
    return this.answer != null;
  }

  public boolean hasAuthoredStaff() {
    return this.authoredStaff != null;
  }
}
