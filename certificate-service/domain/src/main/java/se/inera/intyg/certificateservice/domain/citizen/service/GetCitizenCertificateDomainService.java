package se.inera.intyg.certificateservice.domain.citizen.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@RequiredArgsConstructor
public class GetCitizenCertificateDomainService {

  private final CertificateRepository certificateRepository;

  public Certificate get(CertificateId certificateId, PersonId citizen) {
    final var certificate = certificateRepository.getById(certificateId);

    if (!isCertificateIssuedOnPatient(citizen, certificate)) {
      throw new IllegalStateException(
          "Citizen is trying to access certificate with id '%s', but it is not issued on citizen"
              .formatted(certificateId)
      );
    }

    return certificate;
  }

  private static boolean isCertificateIssuedOnPatient(PersonId citizen,
      Certificate certificate) {
    return certificate.certificateMetaData().patient().id().id().equals(citizen.id());
  }
}
