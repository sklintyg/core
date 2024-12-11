package se.inera.intyg.certificateprintservice.application.print.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateRequest.PrintCertificateRequestBuilder;

@Value
@Builder
@JsonDeserialize(builder = PrintCertificateRequestBuilder.class)
public class PrintCertificateRequest {

  List<PrintCertificateCategory> categories;
  PrintCertificateMetadata metadata;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PrintCertificateRequestBuilder {

  }
}