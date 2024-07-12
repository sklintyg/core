package se.inera.intyg.certificateservice.domain.event.model;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;

@Builder
@Value
public class MessageEvent {

  MessageEventType type;
  LocalDateTime start;
  LocalDateTime end;
  MessageId messageId;
  CertificateId certificateId;
  ActionEvaluation actionEvaluation;

  public long duration() {
    return Duration.between(start, end).toMillis();
  }

}
