package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElementStyleEnum;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateDataElementDTO.CertificateDataElementDTOBuilder;

@JsonDeserialize(builder = CertificateDataElementDTOBuilder.class)
@Value
@Builder
public class CertificateDataElementDTO {

  String id;
  String parent;
  int index;
  CertificateDataConfig config;
  CertificateDataValue value;
  CertificateDataElementStyleEnum style;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataElementDTOBuilder {

  }

}
