package se.inera.intyg.certificateservice.domain.certificate.service;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Signature;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;

public interface XmlGenerator {

  Xml generate(Certificate certificate);

  Xml generate(Certificate certificate, Signature signature);
}
