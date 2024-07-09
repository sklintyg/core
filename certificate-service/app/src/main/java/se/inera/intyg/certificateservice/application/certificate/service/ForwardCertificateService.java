package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.service.ForwardCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.ForwardMessagesDomainService;

@Service
@RequiredArgsConstructor
public class ForwardCertificateService {

  private final ForwardCertificateDomainService forwardCertificateDomainService;
  private final ForwardMessagesDomainService forwardMessagesDomainService;
}
