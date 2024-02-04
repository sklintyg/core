package se.inera.intyg.certificateservice.application.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO.PatientDTOBuilder;

@JsonDeserialize(builder = PatientDTOBuilder.class)
@Value
@Builder
public class PatientDTO {

  PersonIdDTO id;
  String firstName;
  String lastName;
  String middleName;
  String fullName;
  String street;
  String city;
  String zipCode;
  Boolean testIndicated;
  Boolean protectedPerson;
  Boolean deceased;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PatientDTOBuilder {

  }
}
