package se.inera.intyg.certificateservice.application.message.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.ReminderDTO;
import se.inera.intyg.certificateservice.domain.message.model.Reminder;

@Component
public class ReminderConverter {

  public ReminderDTO convert(Reminder reminder) {
    //TODO: Handle in INTYGFV-16615
    return ReminderDTO.builder().build();
  }
}
