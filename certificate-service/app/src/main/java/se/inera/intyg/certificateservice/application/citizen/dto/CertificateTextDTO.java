package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateTextDTO.CertificateTextDTOBuilder;

@JsonDeserialize(builder = CertificateTextDTOBuilder.class)
@Value
@Builder
public class CertificateTextDTO {

  String text;
  CertificateTextTypeDTO type;
  List<CertificateLinkDTO> links;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateTextDTOBuilder {

  }

}
