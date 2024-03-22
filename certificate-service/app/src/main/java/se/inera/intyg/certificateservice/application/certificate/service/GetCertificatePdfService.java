package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificatePdfDomainService;

@Service
@RequiredArgsConstructor
public class GetCertificatePdfService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final GetCertificatePdfDomainService getCertificatePdfDomainService;

  public GetCertificatePdfResponse get(GetCertificatePdfRequest getCertificatePdfRequest,
      String certificateId) {
    //TODO evaluera pdf?

    final var actionEvaluation = actionEvaluationFactory.create(
        getCertificatePdfRequest.getUser(),
        getCertificatePdfRequest.getUnit(),
        getCertificatePdfRequest.getCareUnit(),
        getCertificatePdfRequest.getCareProvider()
    );

    final var certificatePdf = getCertificatePdfDomainService.get(
        new CertificateId(certificateId),
        actionEvaluation
    );

    return GetCertificatePdfResponse.builder()
        .pdfData(certificatePdf.pdfData())
        .fileName(certificatePdf.fileName())
        .build();
  }

}
