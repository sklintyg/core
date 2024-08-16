package se.inera.intyg.certificateservice.application.certificate.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.AnswerComplementRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.AnswerComplementDomainService;
import se.inera.intyg.certificateservice.domain.message.model.Content;

@Service
@RequiredArgsConstructor
public class AnswerComplementService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final AnswerComplementRequestValidator answerComplementRequestValidator;
  private final AnswerComplementDomainService answerComplementDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  @Transactional
  public AnswerComplementResponse answer(AnswerComplementRequest answerComplementRequest,
      String certificateId) {
    answerComplementRequestValidator.validate(answerComplementRequest, certificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        answerComplementRequest.getUser(),
        answerComplementRequest.getUnit(),
        answerComplementRequest.getCareUnit(),
        answerComplementRequest.getCareProvider()
    );

    final var certificate = answerComplementDomainService.answer(
        new CertificateId(certificateId),
        actionEvaluation,
        new Content(answerComplementRequest.getMessage())
    );

    return AnswerComplementResponse.builder()
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
}
