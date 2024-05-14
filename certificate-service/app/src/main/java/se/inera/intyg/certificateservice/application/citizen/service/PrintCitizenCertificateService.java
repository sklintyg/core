package se.inera.intyg.certificateservice.application.citizen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.citizen.service.PrintCitizenCertificateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@Service
@RequiredArgsConstructor
public class PrintCitizenCertificateService {

  private final PrintCitizenCertificateDomainService printCitizenCertificateDomainService;

  public PrintCitizenCertificateResponse get(PrintCitizenCertificateRequest request,
      String certificateId) {

    final var pdf = printCitizenCertificateDomainService.get(
        new CertificateId(certificateId),
        PersonId.builder()
            .id(request.getCitizen().getId())
            .type(request.getCitizen().getType().toPersonIdType())
            .build(),
        request.getAdditionalInfo()
    );

    return PrintCitizenCertificateResponse.builder()
        .fileName(pdf.fileName())
        .pdfData(pdf.pdfData())
        .build();
  }

}
