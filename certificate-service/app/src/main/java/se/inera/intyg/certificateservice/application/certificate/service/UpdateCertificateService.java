package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.validation.UpdateCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateCertificateDomainService;

@Service
@RequiredArgsConstructor
public class UpdateCertificateService {

  private final UpdateCertificateRequestValidator updateCertificateRequestValidator;
  private final UpdateCertificateDomainService updateCertificateDomainService;
  private final ElementDataConverter elementDataConverter;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final CertificateConverter certificateConverter;

  public UpdateCertificateResponse update(UpdateCertificateRequest updateCertificateRequest,
      String certificateId) {
    updateCertificateRequestValidator.validate(updateCertificateRequest, certificateId);
    
    final var actionEvaluation = actionEvaluationFactory.create(
        updateCertificateRequest.getUser(),
        updateCertificateRequest.getUnit(),
        updateCertificateRequest.getCareUnit(),
        updateCertificateRequest.getCareProvider()
    );

    final var elementDataList = updateCertificateRequest.getCertificate().getData()
        .entrySet()
        .stream()
        .map(entry -> elementDataConverter.convert(entry.getKey(), entry.getValue()))
        .toList();

    final var updatedCertificate = updateCertificateDomainService.update(
        new CertificateId(certificateId),
        elementDataList,
        actionEvaluation
    );

    return UpdateCertificateResponse.builder()
        .certificate(certificateConverter.convert(updatedCertificate))
        .build();
  }
}
