package se.inera.intyg.certificateservice.domain.patient.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PersonId {

  String id;
  PersonIdType type;
}
