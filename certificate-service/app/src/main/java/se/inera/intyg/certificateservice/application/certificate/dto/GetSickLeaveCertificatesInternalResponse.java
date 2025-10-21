package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalResponse.GetSickLeaveCertificatesInternalResponseBuilder;

@JsonDeserialize(builder = GetSickLeaveCertificatesInternalResponseBuilder.class)
@Value
@Builder
public class GetSickLeaveCertificatesInternalResponse {

  List<SickLeaveCertificateItemDTO> certificates;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetSickLeaveCertificatesInternalResponseBuilder {

  }

}

