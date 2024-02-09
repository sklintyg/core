package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Builder
@Accessors(fluent = true)
public class CertificateModelId {

  CertificateType type;
  CertificateVersion version;
}
