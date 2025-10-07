package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateFromTemplateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateFromTemplateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.CreateCertificateFromTemplateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.CreateCertificateFromTemplateDomainService;

@Service
@RequiredArgsConstructor
public class CreateCertificateFromTemplateService {

  private final CreateCertificateFromTemplateDomainService createCertificateFromTemplateDomainService;
  private final CreateCertificateFromTemplateRequestValidator createCertificateFromTemplateRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  public CreateCertificateFromTemplateResponse create(CreateCertificateFromTemplateRequest request,
      String certificateId) {
    createCertificateFromTemplateRequestValidator.validate(request, certificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var certificate = createCertificateFromTemplateDomainService.create(
        new CertificateId(certificateId),
        actionEvaluation
    );

    return CreateCertificateFromTemplateResponse.builder()
        .certificate(
            certificateConverter.convert(
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
                actionEvaluation
            )
        )
        .build();
  }
}