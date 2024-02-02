package se.inera.intyg.certificateservice.application.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO.ResourceLinkDTOBuilder;

@JsonDeserialize(builder = ResourceLinkDTOBuilder.class)
@Value
@Builder
public class ResourceLinkDTO {

  ResourceLinkTypeDTO type;
  String name;
  String description;
  String body;
  boolean enabled;
  String title;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ResourceLinkDTOBuilder {

  }
}
