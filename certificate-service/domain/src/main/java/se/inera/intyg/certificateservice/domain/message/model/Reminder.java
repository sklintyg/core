package se.inera.intyg.certificateservice.domain.message.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Reminder {

  private MessageId id;
  private SenderReference reference;
  private Subject subject;
  private Content content;
  private Author author;
  private LocalDateTime created;
  private LocalDateTime sent;
  private MessageContactInfo contactInfo;
}
