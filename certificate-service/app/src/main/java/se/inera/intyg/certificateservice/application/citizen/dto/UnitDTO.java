package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.UnitDTO.UnitDTOBuilder;

@Value
@Builder
@JsonDeserialize(builder = UnitDTOBuilder.class)
public class UnitDTO {

  String unitId;
  String unitName;
  String address;
  String zipCode;
  String city;
  String phoneNumber;
  String email;
  Boolean isInactive;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UnitDTOBuilder {

  }
}