package se.inera.intyg.certificateservice.application.citizen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.citizen.dto.CitizenCertificateExistsResponse;
import se.inera.intyg.certificateservice.application.citizen.validation.CitizenCertificateRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@Service
@RequiredArgsConstructor
public class CitizenCertificateExistsService {

  private final CertificateRepository certificateRepository;
  private final CitizenCertificateRequestValidator citizenCertificateRequestValidator;

  public CitizenCertificateExistsResponse exist(String certificateId) {
    citizenCertificateRequestValidator.validate(certificateId);

    return CitizenCertificateExistsResponse.builder()
        .exists(certificateRepository.exists(new CertificateId(certificateId)))
        .build();
  }
}
