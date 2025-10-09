package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateDraftFromCertificateRequest.CreateCertificateFromTemplateRequestBuilder;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

@JsonDeserialize(builder = CreateCertificateFromTemplateRequestBuilder.class)
@Value
@Builder
public class CreateDraftFromCertificateRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CreateCertificateFromTemplateRequestBuilder {

  }
}