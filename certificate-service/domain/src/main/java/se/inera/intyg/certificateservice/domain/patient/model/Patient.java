package se.inera.intyg.certificateservice.domain.patient.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@Value
@Builder
public class Patient {

  PersonId id;
  Name name;
  PersonAddress address;
  Deceased deceased;
  TestIndicated testIndicated;
  ProtectedPerson protectedPerson;
}
