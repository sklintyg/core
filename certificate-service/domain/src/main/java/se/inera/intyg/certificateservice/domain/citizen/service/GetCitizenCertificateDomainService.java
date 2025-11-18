package se.inera.intyg.certificateservice.domain.citizen.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CitizenCertificateForbidden;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@RequiredArgsConstructor
public class GetCitizenCertificateDomainService {

  private final CertificateRepository certificateRepository;

  public Certificate get(CertificateId certificateId, PersonId citizen) {
    final var certificate = certificateRepository.getById(certificateId);

    if (certificate.signed() != null) {
      final var updatedMetadata = certificateRepository
          .getMetadataFromSignInstance(certificate.certificateMetaData(), certificate.signed());
      certificate.updateMetadata(updatedMetadata);
    }

    if (!certificate.isCertificateIssuedOnPatient(citizen)) {
      throw new CitizenCertificateForbidden(
          "Citizen is trying to access certificate with id '%s', but it is not issued on citizen"
              .formatted(certificateId)
      );
    }
    if (Boolean.FALSE.equals(certificate.certificateModel().availableForCitizen())) {
      throw new CitizenCertificateForbidden(
          "Citizen is trying to access certificate with id '%s', but it is not available for citizen"
              .formatted(certificateId)
      );
    }

    return certificate;
  }
}
