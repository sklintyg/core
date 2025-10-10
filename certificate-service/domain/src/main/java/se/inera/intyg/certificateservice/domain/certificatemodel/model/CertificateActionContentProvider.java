package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public interface CertificateActionContentProvider {

  String body(Certificate certificate);

  default String name(Certificate certificate) {
    return null;
  }

  default String description(Certificate certificate) {
    return null;
  }
}