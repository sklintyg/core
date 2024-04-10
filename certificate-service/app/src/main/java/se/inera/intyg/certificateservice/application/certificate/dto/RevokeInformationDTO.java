package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeInformationDTO.RevokeInformationDTOBuilder;

@JsonDeserialize(builder = RevokeInformationDTOBuilder.class)
@Value
@Builder
public class RevokeInformationDTO {

  String reason;
  String message;

  @JsonPOJOBuilder(withPrefix = "")
  public static class RevokeInformationDTOBuilder {

  }
}
