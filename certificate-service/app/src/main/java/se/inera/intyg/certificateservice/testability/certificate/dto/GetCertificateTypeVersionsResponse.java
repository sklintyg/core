package se.inera.intyg.certificateservice.testability.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.testability.certificate.dto.GetCertificateTypeVersionsResponse.GetCertificateTypeVersionsResponseBuilder;

@JsonDeserialize(builder = GetCertificateTypeVersionsResponseBuilder.class)
@Value
@Builder
public class GetCertificateTypeVersionsResponse {

  List<CertificateModelIdDTO> certificateModelIds;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateTypeVersionsResponseBuilder {

  }
}
