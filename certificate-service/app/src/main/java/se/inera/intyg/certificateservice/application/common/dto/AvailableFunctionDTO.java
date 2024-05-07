package se.inera.intyg.certificateservice.application.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.AvailableFunctionDTO.AvailableFunctionDTOBuilder;

@JsonDeserialize(builder = AvailableFunctionDTOBuilder.class)
@Value
@Builder
public class AvailableFunctionDTO {

  AvailableFunctionType type;
  String name;
  String description;
  String title;
  String body;
  List<InformationDTO> information;
  boolean enabled;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AvailableFunctionDTOBuilder {

  }
}
