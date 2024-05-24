package se.inera.intyg.certificateservice.application.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageResponse;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.GetCertificateMessageRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;

@Service
@RequiredArgsConstructor
public class GetCertificateMessageService {

  private final GetCertificateMessageRequestValidator getCertificateMessageRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final GetCertificateDomainService getCertificateDomainService;
  private final QuestionConverter questionConverter;

  public GetCertificateMessageResponse get(
      GetCertificateMessageRequest getCertificateMessageRequest, String certificateId) {
    getCertificateMessageRequestValidator.validate(getCertificateMessageRequest, certificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        getCertificateMessageRequest.getPatient(),
        getCertificateMessageRequest.getUser(),
        getCertificateMessageRequest.getUnit(),
        getCertificateMessageRequest.getCareUnit(),
        getCertificateMessageRequest.getCareProvider()
    );

    final var certificate = getCertificateDomainService.get(
        new CertificateId(certificateId),
        actionEvaluation
    );

    return GetCertificateMessageResponse.builder()
        .questions(
            certificate.messages().stream()
                .map(message -> questionConverter.convert(
                        message,
                        message.actions(actionEvaluation, certificate)
                    )
                )
                .toList()
        )
        .build();
  }
}
