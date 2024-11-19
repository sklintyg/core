package se.inera.intyg.certificateservice.domain.citizen.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;
import se.inera.intyg.certificateservice.domain.common.exception.CitizenCertificateForbidden;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@RequiredArgsConstructor
public class PrintCitizenCertificateDomainService {

  private final CertificateRepository certificateRepository;
  private final PdfGenerator pdfGenerator;

  public Pdf get(CertificateId certificateId, PersonId personId, String additionalInfoText) {

    final var certificate = certificateRepository.getById(certificateId);

    if (!certificate.isCertificateIssuedOnPatient(personId)) {
      throw new CitizenCertificateForbidden(
          "Citizen is trying to print certificate with id '%s', but it is not issued on citizen"
              .formatted(certificateId)
      );
    }
    if (Boolean.FALSE.equals(certificate.certificateModel().availableForCitizen())) {
      throw new CitizenCertificateForbidden(
          "Citizen is trying to print certificate with id '%s', but it is not available for citizen"
              .formatted(certificateId)
      );
    }

    return pdfGenerator.generate(certificate, additionalInfoText, true);
  }
}
