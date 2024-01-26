package se.inera.intyg.certificateservice.application.common.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResourceLinkDTO {

  ResourceLinkTypeDTO type;
  String name;
  String description;
  String body;
  boolean enabled;
  String title;
}
