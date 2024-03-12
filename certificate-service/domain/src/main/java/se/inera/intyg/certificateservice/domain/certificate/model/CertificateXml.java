package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CertificateXml {

  CertificateId certificateId;
  Revision revision;
  Xml xml;
}
