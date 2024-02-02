package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.PatientDTO.PatientDTOBuilder;

@JsonDeserialize(builder = PatientDTOBuilder.class)
@Value
@Builder
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
