package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokedDTO.RevokedDTOBuilder;

@JsonDeserialize(builder = RevokedDTOBuilder.class)
@Value
@Builder
public class RevokedDTO {

  String reason;
  String message;
  LocalDateTime revokedAt;
  StaffDTO revokedBy;

  @JsonPOJOBuilder(withPrefix = "")
  public static class RevokedDTOBuilder {

  }
}
