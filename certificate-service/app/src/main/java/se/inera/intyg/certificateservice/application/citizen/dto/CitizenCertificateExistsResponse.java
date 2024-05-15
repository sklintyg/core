package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.CitizenCertificateExistsResponse.CitizenCertificateExistsResponseBuilder;

@JsonDeserialize(builder = CitizenCertificateExistsResponseBuilder.class)
@Value
@Builder
public class CitizenCertificateExistsResponse {

  boolean exists;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CitizenCertificateExistsResponseBuilder {

  }
}
