package se.inera.intyg.certificateservice.domain.certificate.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@RequiredArgsConstructor
public class GenerateXMLRepresentationOfCertificate {

  private final XmlGenerator xmlGenerator;

  public String generate(Certificate certificate) {
    return xmlGenerator.generate(certificate);
  }
}
