package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateResponse.GetCertificateResponseBuilder;
import se.inera.intyg.certificateservice.application.common.dto.AvailableFunctionDTO;

@JsonDeserialize(builder = GetCertificateResponseBuilder.class)
@Value
@Builder
public class GetCitizenCertificateResponse {

  CertificateDTO certificate;
  List<AvailableFunctionDTO> availableFunctions;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateResponseBuilder {

  }
}
