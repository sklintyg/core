package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoResponse.GetCertificateTypeInfoResponseBuilder;

@JsonDeserialize(builder = GetCertificateTypeInfoResponseBuilder.class)
@Value
@Builder
public class GetCertificateTypeInfoResponse {

  List<CertificateTypeInfoDTO> list;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateTypeInfoResponseBuilder {

  }
}
