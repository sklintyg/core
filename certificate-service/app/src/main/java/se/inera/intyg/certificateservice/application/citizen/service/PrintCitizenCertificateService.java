package se.inera.intyg.certificateservice.application.citizen.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.validation.CitizenCertificateRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.citizen.service.PrintCitizenCertificateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@Service
@RequiredArgsConstructor
public class PrintCitizenCertificateService {

  private final PrintCitizenCertificateDomainService printCitizenCertificateDomainService;
  private final CitizenCertificateRequestValidator citizenCertificateRequestValidator;

  public PrintCitizenCertificateResponse get(PrintCitizenCertificateRequest request,
      String certificateId) {
    citizenCertificateRequestValidator.validate(certificateId, request.getPersonId());
    final var pdf = printCitizenCertificateDomainService.get(
        new CertificateId(certificateId),
        PersonId.builder()
            .id(request.getPersonId().getId())
            .type(request.getPersonId().getType().toPersonIdType())
            .build(),
        request.getAdditionalInfo(),
        request.getCustomizationId() != null ? List.of(new ElementId(request.getCustomizationId()))
            : List.of()
    );

    return PrintCitizenCertificateResponse.builder()
        .filename(pdf.fileName())
        .pdfData(pdf.pdfData())
        .build();
  }

}
