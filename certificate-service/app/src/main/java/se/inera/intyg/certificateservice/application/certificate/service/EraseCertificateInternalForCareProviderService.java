package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.EraseCertificateInternalRequest;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@Service
@RequiredArgsConstructor
public class EraseCertificateInternalForCareProviderService {

  private final CertificateRepository certificateRepository;

  public void erase(EraseCertificateInternalRequest request, String careProviderId) {

  }
}