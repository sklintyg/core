package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateListResponse.GetCitizenCertificateListResponseBuilder;

@JsonDeserialize(builder = GetCitizenCertificateListResponseBuilder.class)
@Value
@Builder
public class GetCitizenCertificateListResponse {

  List<CertificateDTO> citizenCertificates;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCitizenCertificateListResponseBuilder {

  }
}
