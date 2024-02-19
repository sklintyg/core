package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO.StaffDTOBuilder;

@JsonDeserialize(builder = StaffDTOBuilder.class)
@Value
@Builder
public class StaffDTO {

  String personId;
  String firstName;
  String middleName;
  String lastName;
  String fullName;
  String prescriptionCode;

  @JsonPOJOBuilder(withPrefix = "")
  public static class StaffDTOBuilder {

  }
}
