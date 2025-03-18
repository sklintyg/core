package se.inera.intyg.certificateservice.application.certificate.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;

@Slf4j
@Service
@RequiredArgsConstructor
public class EraseCertificateInternalForCareProviderService {

  private final CertificateRepository certificateRepository;

  @Transactional
  public void erase(String careProviderId) {
    final var numberOfDeletedCertificates = certificateRepository.deleteByCareProviderId(
        new HsaId(careProviderId)
    );
    log.info("Successfully completed erasure of certificates for care provider '{}' Total number of erased certificates: {}",
        careProviderId, numberOfDeletedCertificates);
  }
}