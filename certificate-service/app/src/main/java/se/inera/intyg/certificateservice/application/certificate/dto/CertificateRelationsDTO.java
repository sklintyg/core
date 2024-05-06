package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationsDTO.CertificateRelationsDTOBuilder;

@JsonDeserialize(builder = CertificateRelationsDTOBuilder.class)
@Value
@Builder
public class CertificateRelationsDTO {

  CertificateRelationDTO parent;
  List<CertificateRelationDTO> children;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateRelationsDTOBuilder {

  }
}
