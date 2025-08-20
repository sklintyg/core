package se.inera.intyg.intygproxyservice.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenResponse.CitizenResponseBuilder;
import se.inera.intyg.intygproxyservice.integration.api.pu.Status;

@JsonDeserialize(builder = CitizenResponseBuilder.class)
@Value
@Builder
public class CitizenResponse {

  CitizenDTO citizen;
  Status status;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CitizenResponseBuilder {

  }
}