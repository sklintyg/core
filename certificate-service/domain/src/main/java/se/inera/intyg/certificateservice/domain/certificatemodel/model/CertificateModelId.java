package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CertificateModelId {

  CertificateType type;
  CertificateVersion version;

  public boolean matches(String type, List<String> versions) {
    return this.type.type().equals(type) && versions.contains(this.version.version());
  }
}