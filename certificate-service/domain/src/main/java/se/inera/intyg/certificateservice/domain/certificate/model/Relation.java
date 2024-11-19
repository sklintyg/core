package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Relation {

  RelationType type;
  LocalDateTime created;
  Certificate certificate;
}
