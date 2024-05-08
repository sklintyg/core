package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateLinkDTO.CertificateLinkDTOBuilder;

@JsonDeserialize(builder = CertificateLinkDTOBuilder.class)
@Value
@Builder
public class CertificateLinkDTO {

  String id;
  String name;
  String url;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateLinkDTOBuilder {

  }

}
