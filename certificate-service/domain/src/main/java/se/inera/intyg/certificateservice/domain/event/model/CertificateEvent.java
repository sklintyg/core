package se.inera.intyg.certificateservice.domain.event.model;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Builder
@Value
public class CertificateEvent {

  CertificateEventType type;
  LocalDateTime start;
  LocalDateTime end;
  Certificate certificate;
  ActionEvaluation actionEvaluation;

  public long duration() {
    return Duration.between(start, end).toMillis();
  }
}
