package se.inera.intyg.certificateservice.application.certificate.service;

import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.SignCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Signature;
import se.inera.intyg.certificateservice.domain.certificate.service.SignCertificateDomainService;

@Service
@RequiredArgsConstructor
public class SignCertificateService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final SignCertificateRequestValidator signCertificateRequestValidator;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;
  private final SignCertificateDomainService signCertificateDomainService;

  @Transactional
  public SignCertificateResponse sign(SignCertificateRequest signCertificateRequest,
      String certificateId, Long version) {
    signCertificateRequestValidator.validate(signCertificateRequest, certificateId, version);

    final var actionEvaluation = actionEvaluationFactory.create(
        signCertificateRequest.getUser(),
        signCertificateRequest.getUnit(),
        signCertificateRequest.getCareUnit(),
        signCertificateRequest.getCareProvider()
    );

    final var signedCertificate = signCertificateDomainService.sign(
        new CertificateId(certificateId),
        new Revision(version),
        new Signature(
            new String(
                Base64.getDecoder().decode(signCertificateRequest.getSignatureXml()),
                StandardCharsets.UTF_8
            )
        ),
        actionEvaluation
    );

    return SignCertificateResponse.builder()
        .certificate(
            certificateConverter.convert(
                signedCertificate,
                signedCertificate.actionsInclude(Optional.of(actionEvaluation)).stream()
                    .map(certificateAction ->
                        resourceLinkConverter.convert(
                            certificateAction,
                            Optional.of(signedCertificate),
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
