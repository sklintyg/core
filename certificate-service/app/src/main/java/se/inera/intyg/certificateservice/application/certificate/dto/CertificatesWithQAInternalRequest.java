package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesWithQAInternalRequest.CertificatesWithQAInternalRequestBuilder;

@Value
@Builder
@JsonDeserialize(builder = CertificatesWithQAInternalRequestBuilder.class)
public class CertificatesWithQAInternalRequest {

  List<String> certificateIds;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificatesWithQAInternalRequestBuilder {

  }
}
