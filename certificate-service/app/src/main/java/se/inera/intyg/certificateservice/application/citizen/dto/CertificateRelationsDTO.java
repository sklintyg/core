package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateRelationsDTO.CertificateRelationsDTOBuilder;

@Value
@Builder
@JsonDeserialize(builder = CertificateRelationsDTOBuilder.class)
public class CertificateRelationsDTO {

  CertificateRelationDTO parent;
  CertificateRelationDTO[] children;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateRelationsDTOBuilder {

  }

}