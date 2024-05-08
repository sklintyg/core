package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateRequest.GetCitizenCertificateRequestBuilder;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;

@JsonDeserialize(builder = GetCitizenCertificateRequestBuilder.class)
@Value
@Builder
public class GetCitizenCertificateRequest {

  PersonIdDTO personId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCitizenCertificateRequestBuilder {

  }
}
