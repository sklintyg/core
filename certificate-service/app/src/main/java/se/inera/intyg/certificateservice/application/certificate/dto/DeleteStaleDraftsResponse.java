package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteStaleDraftsResponse.DeleteDraftsResponseBuilder;

@JsonDeserialize(builder = DeleteDraftsResponseBuilder.class)
@Value
@Builder
public class DeleteStaleDraftsResponse {

  List<CertificateDTO> certificates;

  @JsonPOJOBuilder(withPrefix = "")
  public static class DeleteDraftsResponseBuilder {

  }
}
