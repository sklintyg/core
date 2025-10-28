package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalRequest.GetSickLeaveCertificatesInternalRequestBuilder;

@JsonDeserialize(builder = GetSickLeaveCertificatesInternalRequestBuilder.class)
@Value
@Builder
public class GetSickLeaveCertificatesInternalRequest {

  PersonIdDTO personId;
  @Builder.Default
  List<String> certificateTypes = List.of();
  LocalDate signedFrom;
  LocalDate signedTo;
  @Builder.Default
  List<String> issuedByUnitIds = List.of();
  @Builder.Default
  List<String> issuedByStaffIds = List.of();

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetSickLeaveCertificatesInternalRequestBuilder {

  }
}

