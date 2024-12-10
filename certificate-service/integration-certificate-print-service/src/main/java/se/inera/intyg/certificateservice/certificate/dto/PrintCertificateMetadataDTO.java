package se.inera.intyg.certificateservice.certificate.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintCertificateMetadataDTO {

  String name;
  String signingDate;
}
