package se.inera.intyg.certificateprintservice.print.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateResponse.PrintCertificateResponseBuilder;

@Value
@Builder
@JsonDeserialize(builder = PrintCertificateResponseBuilder.class)
public class PrintCertificateResponse {

  String filename;
  byte[] pdfData;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PrintCertificateResponseBuilder {

  }
}