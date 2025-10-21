package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.CreateCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.CreateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;

@Service
@RequiredArgsConstructor
public class CreateCertificateService {

  private final CreateCertificateRequestValidator createCertificateRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final CreateCertificateDomainService createCertificateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;


  @Transactional
  public CreateCertificateResponse create(CreateCertificateRequest createCertificateRequest) {
    createCertificateRequestValidator.validate(createCertificateRequest);
    final var actionEvaluation = actionEvaluationFactory.create(
        createCertificateRequest.getPatient(),
        createCertificateRequest.getUser(),
        createCertificateRequest.getUnit(),
        createCertificateRequest.getCareUnit(),
        createCertificateRequest.getCareProvider()
    );

    final var certificate = createCertificateDomainService.create(
        certificateModelId(createCertificateRequest),
        actionEvaluation,
        createCertificateRequest.getExternalReference() != null
            ? new ExternalReference(createCertificateRequest.getExternalReference())
            : null,
        createCertificateRequest.getPrefillXml() != null ?
            new Xml(createCertificateRequest.getPrefillXml().value()) : null
    );

    return CreateCertificateResponse.builder()
        .certificate(certificateConverter.convert(
            certificate,
            certificate.actionsInclude(Optional.of(actionEvaluation)).stream()
                .map(certificateAction ->
                    resourceLinkConverter.convert(
                        certificateAction,
                        Optional.of(certificate),
                        actionEvaluation
                    )
                )
                .toList(),
            actionEvaluation)
        )
        .build();
  }

  private static CertificateModelId certificateModelId(
      CreateCertificateRequest createCertificateRequest) {
    return CertificateModelId.builder()
        .type(
            new CertificateType(
                createCertificateRequest.getCertificateModelId().getType()
            )
        )
        .version(
            new CertificateVersion(
                createCertificateRequest.getCertificateModelId().getVersion()
            )
        )
        .build();
  }
}
