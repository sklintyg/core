package se.inera.intyg.certificateservice.domain.common.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.message.model.Message;

@Value
@Builder
public class MessagesResponse {

  List<Certificate> certificates;
  List<Message> messages;
}
