package se.inera.intyg.certificateservice.domain.patient.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGeneratorCertificatesForCareWithQA;
import se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest;

@RequiredArgsConstructor
public class GetPatientCertificatesWithQADomainService {

  private final CertificateRepository certificateRepository;
  private final XmlGeneratorCertificatesForCareWithQA xmlGeneratorCertificatesForCareWithQA;

  public Xml get(CertificatesWithQARequest request) {
    final var certificates = certificateRepository.findByCertificatesWithQARequest(
        request
    );

    return xmlGeneratorCertificatesForCareWithQA.generate(certificates);
  }
}

