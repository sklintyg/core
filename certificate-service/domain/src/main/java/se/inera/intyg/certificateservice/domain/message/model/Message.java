package se.inera.intyg.certificateservice.domain.message.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
  private final List<Complement> complements;
  private Answer answer;
  private List<Reminder> reminders;

  public void handle() {
    if (this.status != MessageStatus.HANDLED) {
      this.status = MessageStatus.HANDLED;
      this.modified = LocalDateTime.now(ZoneId.systemDefault());
    }
  }
}
