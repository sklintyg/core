package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateRequest.SendCitizenCertificateRequestBuilder;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;

@JsonDeserialize(builder = SendCitizenCertificateRequestBuilder.class)
@Value
@Builder
public class SendCitizenCertificateRequest {

  PersonIdDTO personId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class SendCitizenCertificateRequestBuilder {

  }
}