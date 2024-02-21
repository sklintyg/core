package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO.UnitDTOBuilder;

@JsonDeserialize(builder = UnitDTOBuilder.class)
@Value
@Builder
public class UnitDTO {

  String unitId;
  String unitName;
  @With
  String address;
  @With
  String zipCode;
  @With
  String city;
  @With
  String phoneNumber;
  String email;
  Boolean isInactive;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UnitDTOBuilder {

  }
}
