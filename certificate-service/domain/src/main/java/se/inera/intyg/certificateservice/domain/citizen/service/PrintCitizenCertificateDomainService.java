package se.inera.intyg.certificateservice.domain.citizen.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.exception.CitizenCertificateForbidden;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@RequiredArgsConstructor
public class PrintCitizenCertificateDomainService {

  private final CertificateRepository certificateRepository;
  private final PdfGeneratorProvider pdfGeneratorProvider;

  public Pdf get(CertificateId certificateId, PersonId personId, String additionalInfoText,
      List<ElementId> hiddenElements) {

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

    final var options = PdfGeneratorOptions.builder()
        .additionalInfoText(additionalInfoText)
        .citizenFormat(true)
        .hiddenElements(hiddenElements)
        .build();

    return pdfGeneratorProvider.provider(certificate)
        .generate(certificate, options);
  }
}

