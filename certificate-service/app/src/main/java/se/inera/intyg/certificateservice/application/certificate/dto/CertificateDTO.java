package se.inera.intyg.certificateservice.application.certificate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDTO {

  private CertificateMetadataDTO metadata;
}
