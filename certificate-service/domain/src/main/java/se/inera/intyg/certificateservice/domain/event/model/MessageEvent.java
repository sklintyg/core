package se.inera.intyg.certificateservice.domain.event.model;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.message.model.Answer;

@Builder
@Value
public class MessageEvent {

  MessageEventType type;
  LocalDateTime start;
  LocalDateTime end;
  Answer answer;
  ActionEvaluation actionEvaluation;

  public long duration() {
    return Duration.between(start, end).toMillis();
  }

}
