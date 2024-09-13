package se.inera.intyg.certificateservice.domain.patient.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGeneratorCertificatesForCareWithQA;

@RequiredArgsConstructor
public class GetCertificatesWithQAInternalDomainService {

  private final CertificateRepository certificateRepository;
  private final XmlGeneratorCertificatesForCareWithQA xmlGeneratorCertificatesForCareWithQA;

  public Xml get(List<CertificateId> certificateIds) {
    final var certificates = certificateRepository.findByIds(certificateIds);
    return xmlGeneratorCertificatesForCareWithQA.generate(certificates);
  }
}

