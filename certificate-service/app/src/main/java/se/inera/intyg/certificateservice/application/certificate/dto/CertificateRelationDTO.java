package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationDTO.CertificateRelationBuilder;

@JsonDeserialize(builder = CertificateRelationBuilder.class)
@Value
@Builder
public class CertificateRelationDTO {

  String certificateId;
  CertificateRelationTypeDTO type;
  CertificateStatusTypeDTO status;
  LocalDateTime created;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateRelationBuilder {

  }
}
