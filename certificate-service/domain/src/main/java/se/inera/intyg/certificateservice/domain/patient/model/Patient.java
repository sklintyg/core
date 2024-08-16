package se.inera.intyg.certificateservice.domain.patient.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
    final var birthdayString = id.id().substring(0, 8);
    final var format = DateTimeFormatter.ofPattern("yyyyMMdd");
    return Period.between(LocalDate.parse(birthdayString, format), LocalDate.now()).getYears();
  }
}
