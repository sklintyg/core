package se.inera.intyg.certificateservice.domain.patient.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Patient {

  PersonId id;
  PersonName name;
  //TODO: Diskutera om vi ska ha ett objekt för address-relaterad information för både unit/patient
  PersonAddress address;
  Deceased deceased;
  TestIndicated testIndicated;
  ProtectedPerson protectedPerson;
}
