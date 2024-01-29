package se.inera.intyg.certificateservice.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceLinkDTO {

  private ResourceLinkTypeDTO type;
  private String name;
  private String description;
  private String body;
  private boolean enabled;
  private String title;
}
