package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse;
import se.inera.intyg.certificateservice.application.certificate.service.validation.GetCertificatePdfRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificatePdfDomainService;

@Service
@RequiredArgsConstructor
public class GetCertificatePdfService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final GetCertificatePdfRequestValidator getCertificatePdfRequestValidator;
  private final GetCertificatePdfDomainService getCertificatePdfDomainService;

  public GetCertificatePdfResponse get(GetCertificatePdfRequest request,
      String certificateId) {
    getCertificatePdfRequestValidator.validate(request, certificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getPatient(),
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var certificatePdf = getCertificatePdfDomainService.get(
        new CertificateId(certificateId),
        actionEvaluation,
        request.getAdditionalInfoText()
    );

    return GetCertificatePdfResponse.builder()
        .pdfData(certificatePdf.pdfData())
        .fileName(certificatePdf.fileName())
        .build();
  }
}
