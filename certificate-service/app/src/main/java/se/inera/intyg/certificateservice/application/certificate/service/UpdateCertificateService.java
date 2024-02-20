package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Map.Entry;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigTypes;
import se.inera.intyg.certificateservice.application.certificate.service.validation.UpdateCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateCertificateDomainService;

@Service
@RequiredArgsConstructor
public class UpdateCertificateService {

  private final UpdateCertificateRequestValidator updateCertificateRequestValidator;
  private final UpdateCertificateDomainService updateCertificateDomainService;
  private final ElementCertificateConverter elementCertificateConverter;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  @Transactional
  public UpdateCertificateResponse update(UpdateCertificateRequest updateCertificateRequest,
      String certificateId) {
    updateCertificateRequestValidator.validate(updateCertificateRequest, certificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        updateCertificateRequest.getPatient(),
        updateCertificateRequest.getUser(),
        updateCertificateRequest.getUnit(),
        updateCertificateRequest.getCareUnit(),
        updateCertificateRequest.getCareProvider()
    );

    final var elementData = elementCertificateConverter.convert(
        updateCertificateRequest.getCertificate()
    );

    final var updatedCertificate = updateCertificateDomainService.update(
        new CertificateId(certificateId),
        elementData,
        actionEvaluation,
        new Revision(updateCertificateRequest.getCertificate().getMetadata().getVersion()));

    return UpdateCertificateResponse.builder()
        .certificate(certificateConverter.convert(
            updatedCertificate,
            updatedCertificate.actions(actionEvaluation).stream()
                .map(resourceLinkConverter::convert)
                .toList())
        )
        .build();
  }

  private static Predicate<Entry<String, CertificateDataElement>> removeCategories() {
    return entry -> !CertificateDataConfigTypes.CATEGORY.equals(
        entry.getValue().getConfig().getType());
  }
}
