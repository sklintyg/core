package se.inera.intyg.certificateservice.application.unit.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesResponse.GetUnitCertificatesResponseBuilder;

@JsonDeserialize(builder = GetUnitCertificatesResponseBuilder.class)
@Value
@Builder
public class GetUnitCertificatesResponse {

  List<CertificateDTO> certificates;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetUnitCertificatesResponseBuilder {

  }
}
