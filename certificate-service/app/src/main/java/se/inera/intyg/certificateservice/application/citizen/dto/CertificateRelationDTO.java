package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateRelationDTO.CertificateRelationDTOBuilder;

@Value
@Builder
@JsonDeserialize(builder = CertificateRelationDTOBuilder.class)
public class CertificateRelationDTO {

  String certificateId;
  CertificateRelationTypeDTO type;
  CertificateStatusTypeDTO status;
  LocalDateTime created;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateRelationDTOBuilder {

  }

}
