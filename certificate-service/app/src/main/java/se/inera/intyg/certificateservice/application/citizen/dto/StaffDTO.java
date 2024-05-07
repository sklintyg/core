package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.StaffDTO.StaffDTOBuilder;

@Value
@Builder
@JsonDeserialize(builder = StaffDTOBuilder.class)
public class StaffDTO {

  String personId;
  String fullName;
  String prescriptionCode;

  @JsonPOJOBuilder(withPrefix = "")
  public static class StaffDTOBuilder {

  }
}
