package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateResponse.PrintCitizenCertificateResponseBuilder;

@JsonDeserialize(builder = PrintCitizenCertificateResponseBuilder.class)
@Value
@Builder
public class PrintCitizenCertificateResponse {

  String filename;
  byte[] pdfData;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PrintCitizenCertificateResponseBuilder {

  }
}
