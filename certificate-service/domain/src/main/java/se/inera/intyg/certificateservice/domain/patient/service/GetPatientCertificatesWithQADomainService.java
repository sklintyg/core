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
    //TODO: Once we introduce events, we might wanna update so that we go towards that repository instead.
    final var certificates = certificateRepository.findByCertificatesWithQARequest(
        request
    );

    return xmlGeneratorCertificatesForCareWithQA.generate(certificates);
  }
}

