package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO.UnitDTOBuilder;

@JsonDeserialize(builder = UnitDTOBuilder.class)
@Value
@Builder
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
