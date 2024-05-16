package se.inera.intyg.certificateservice.domain.common.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CertificateLink {

  String id;
  String name;
  String url;

}
