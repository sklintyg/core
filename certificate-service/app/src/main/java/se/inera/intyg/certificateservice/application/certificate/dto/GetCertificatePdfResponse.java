package se.inera.intyg.certificateservice.application.certificate.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse.GetCertificatePdfResponseBuilder;

@JsonDeserialize(builder = GetCertificatePdfResponseBuilder.class)
@Value
@Builder
public class GetCertificatePdfResponse {

  byte[] pdfData;
  String fileName;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificatePdfResponseBuilder {

  }

}
