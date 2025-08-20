package se.inera.intyg.intygproxyservice.user.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.intygproxyservice.user.dto.CitizenResponse.CitizenResponseBuilder;

@JsonDeserialize(builder = CitizenResponseBuilder.class)
@Value
@Builder
public class CitizenResponse {

  CitizenDTO citizen;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CitizenResponseBuilder {

  }
}