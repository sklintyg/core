package se.inera.intyg.certificateservice.application.message.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageResponse;
import se.inera.intyg.certificateservice.application.message.service.validator.GetCertificateFromMessageRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@Service
@RequiredArgsConstructor
public class GetCertificateFromMessageService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final GetCertificateFromMessageRequestValidator certificateFromMessageRequestValidator;
  private final GetCertificateDomainService getCertificateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;
  private final MessageRepository messageRepository;

  public GetCertificateFromMessageResponse get(
      GetCertificateFromMessageRequest getCertificateRequest, String messageId) {
    certificateFromMessageRequestValidator.validate(getCertificateRequest, messageId);

    final var actionEvaluation = actionEvaluationFactory.create(
        getCertificateRequest.getUser(),
        getCertificateRequest.getUnit(),
        getCertificateRequest.getCareUnit(),
        getCertificateRequest.getCareProvider()
    );

    final var message = messageRepository.getById(
        new MessageId(messageId)
    );

    final var certificate = getCertificateDomainService.get(
        new CertificateId(message.certificateId().id()),
        actionEvaluation
    );

    return GetCertificateFromMessageResponse.builder()
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
                actionEvaluation
            )
        )
        .build();
  }
}
