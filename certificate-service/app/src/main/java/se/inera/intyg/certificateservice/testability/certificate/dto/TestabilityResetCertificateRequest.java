package se.inera.intyg.certificateservice.testability.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityResetCertificateRequest.TestabilityResetCertificateRequestBuilder;

@JsonDeserialize(builder = TestabilityResetCertificateRequestBuilder.class)
@Value
@Builder
public class TestabilityResetCertificateRequest {

  @Builder.Default
  List<String> certificateIds = Collections.emptyList();
  @Builder.Default
  List<String> messageIds = Collections.emptyList();

  @JsonPOJOBuilder(withPrefix = "")
  public static class TestabilityResetCertificateRequestBuilder {

  }
}
