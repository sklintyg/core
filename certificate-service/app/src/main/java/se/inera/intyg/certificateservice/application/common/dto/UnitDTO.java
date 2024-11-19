package se.inera.intyg.certificateservice.application.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO.UnitDTOBuilder;

@JsonDeserialize(builder = UnitDTOBuilder.class)
@Value
@Builder
public class UnitDTO {

  String id;
  String name;
  String address;
  String zipCode;
  String city;
  String phoneNumber;
  String email;
  String workplaceCode;
  Boolean inactive;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UnitDTOBuilder {

  }
}
