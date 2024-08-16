package se.inera.intyg.certificateservice.application.certificate.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateWithoutSignatureRequest;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.SignCertificateWithoutSignatureRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.service.SignCertificateWithoutSignatureDomainService;

@Service
@RequiredArgsConstructor
public class SignCertificateWithoutSignatureService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final SignCertificateWithoutSignatureRequestValidator signCertificateWithoutSignatureRequestValidator;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;
  private final SignCertificateWithoutSignatureDomainService signCertificateWithoutSignatureDomainService;

  @Transactional
  public SignCertificateResponse sign(SignCertificateWithoutSignatureRequest signCertificateRequest,
      String certificateId, Long version) {
    signCertificateWithoutSignatureRequestValidator.validate(
        signCertificateRequest, certificateId, version
    );

    final var actionEvaluation = actionEvaluationFactory.create(
        signCertificateRequest.getUser(),
        signCertificateRequest.getUnit(),
        signCertificateRequest.getCareUnit(),
        signCertificateRequest.getCareProvider()
    );

    final var signedCertificate = signCertificateWithoutSignatureDomainService.sign(
        new CertificateId(certificateId),
        new Revision(version),
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
