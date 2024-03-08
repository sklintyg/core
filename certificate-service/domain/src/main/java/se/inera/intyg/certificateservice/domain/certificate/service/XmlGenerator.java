package se.inera.intyg.certificateservice.domain.certificate.service;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public interface XmlGenerator {

  String generate(Certificate certificate);
}
