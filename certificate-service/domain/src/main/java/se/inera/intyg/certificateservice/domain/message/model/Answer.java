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

  public void send(Staff staff, Content content) {
    this.status = MessageStatus.SENT;
    this.content = content;
    this.authoredStaff = staff;
    this.sent = LocalDateTime.now();
  }

  public void delete() {
    this.status = MessageStatus.DELETED_DRAFT;
  }

  public void save(Staff staff, Content content) {
    this.content = content;
    this.authoredStaff = staff;
    this.author = new Author(staff.name().fullName());
  }
}
