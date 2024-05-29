package se.inera.intyg.certificateservice.domain.message.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@Getter
@Builder
@EqualsAndHashCode
public class Answer {

  private MessageId id;
  private SenderReference reference;
  private MessageType type;
  private Subject subject;
  private Content content;
  private Author author;
  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime sent;
  private MessageStatus status;
  private MessageContactInfo contactInfo;
  private Staff authoredStaff;
}
