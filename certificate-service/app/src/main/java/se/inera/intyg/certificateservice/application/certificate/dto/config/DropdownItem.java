package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.DropdownItem.DropdownItemBuilder;

@JsonDeserialize(builder = DropdownItemBuilder.class)
@Value
@Builder
public class DropdownItem {

  String id;
  String label;

  @JsonPOJOBuilder(withPrefix = "")
  public static class DropdownItemBuilder {

  }
}

