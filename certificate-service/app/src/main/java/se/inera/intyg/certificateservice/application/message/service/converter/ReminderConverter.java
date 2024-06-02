package se.inera.intyg.certificateservice.application.message.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.ReminderDTO;
import se.inera.intyg.certificateservice.domain.message.model.Reminder;

@Component
public class ReminderConverter {

  public ReminderDTO convert(Reminder reminder) {
    return ReminderDTO.builder()
        .id(reminder.id().id())
        .sent(reminder.sent())
        .message(reminder.content().content())
        .author(reminder.author().name())
        .build();
  }
}
