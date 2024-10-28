package se.inera.intyg.certificateservice.domain.patient.model;

import java.time.LocalDate;
import java.time.Period;
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

  public int getAge() {
    return Period.between(id.birthDate(), LocalDate.now()).getYears();
  }
}
