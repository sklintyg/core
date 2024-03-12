package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.service.validation.GetCertificateXmlRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateXmlDomainService;

@Service
@RequiredArgsConstructor
public class GetCertificateXmlService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final GetCertificateXmlRequestValidator getCertificateXmlRequestValidator;
  private final GetCertificateXmlDomainService getCertificateXmlDomainService;

  public GetCertificateXmlResponse get(GetCertificateXmlRequest request, String certificateId) {
    getCertificateXmlRequestValidator.validate(request, certificateId);
    final var actionEvaluation = actionEvaluationFactory.create(
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var certificateXml = getCertificateXmlDomainService.get(
        new CertificateId(certificateId),
        actionEvaluation
    );

    return GetCertificateXmlResponse.builder()
        .certificateId(certificateXml.certificateId().id())
        .xml(certificateXml.xml().base64())
        .version(certificateXml.revision().value())
        .build();
  }
}
