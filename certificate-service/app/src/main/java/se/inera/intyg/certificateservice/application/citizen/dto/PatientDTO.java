package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.PatientDTO.PatientDTOBuilder;

@Value
@Builder
@JsonDeserialize(builder = PatientDTOBuilder.class)
public class PatientDTO {

  PersonIdDTO personId;
  PersonIdDTO previousPersonId;
  String firstName;
  String lastName;
  String middleName;
  String fullName;
  String street;
  String city;
  String zipCode;
  boolean coordinationNumber;
  boolean testIndicated;
  boolean protectedPerson;
  boolean deceased;
  boolean differentNameFromEHR;
  boolean personIdChanged;
  boolean reserveId;
  boolean addressFromPU;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PatientDTOBuilder {

  }
}
