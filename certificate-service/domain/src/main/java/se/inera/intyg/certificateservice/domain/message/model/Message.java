package se.inera.intyg.certificateservice.domain.message.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
  private List<Complement> complements;
  private Answer answer;
  private List<Reminder> reminders;
}
