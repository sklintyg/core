package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateRequest.PrintCitizenCertificateRequestBuilder;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;

@JsonDeserialize(builder = PrintCitizenCertificateRequestBuilder.class)
@Value
@Builder
public class PrintCitizenCertificateRequest {

  PersonIdDTO personId;
  String additionalInfo;
  String customizationId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PrintCitizenCertificateRequestBuilder {

  }
}
