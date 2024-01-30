package se.inera.intyg.certificateservice.domain.patient.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Patient {

  PersonId id;
  String firstName;
  String lastName;
  String middleName;
  String fullName;
  String street;
  String city;
  String zipCode;
  boolean deceased;
  boolean testIndicated;
  boolean protectedPerson;
}
