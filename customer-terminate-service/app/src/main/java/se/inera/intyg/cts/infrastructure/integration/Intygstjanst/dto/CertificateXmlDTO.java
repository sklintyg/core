package se.inera.intyg.cts.infrastructure.integration.Intygstjanst.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateXmlDTO {

  private String id;
  private boolean revoked;
  private String xml;

}
