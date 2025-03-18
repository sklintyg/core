package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.EraseCertificateInternalRequest.EraseCertificateInternalRequestBuilder;

@JsonDeserialize(builder = EraseCertificateInternalRequestBuilder.class)
@Value
@Builder
public class EraseCertificateInternalRequest {

  int batchSize;

  @JsonPOJOBuilder(withPrefix = "")
  public static class EraseCertificateInternalRequestBuilder {

  }
}