package se.inera.intyg.certificateservice.application.patient.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesResponse.GetPatientCertificatesResponseBuilder;

@JsonDeserialize(builder = GetPatientCertificatesResponseBuilder.class)
@Value
@Builder
public class GetPatientCertificatesResponse {

  List<CertificateDTO> certificates;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetPatientCertificatesResponseBuilder {

  }
}
