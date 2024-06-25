package se.inera.intyg.certificateservice.application.message.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.message.dto.SendAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.SendAnswerResponse;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.SendAnswerRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.message.service.SendAnswerDomainService;

@Service
@RequiredArgsConstructor
public class SendAnswerService {

  private final SendAnswerRequestValidator sendAnswerRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final SendAnswerDomainService sendAnswerDomainService;
  private final QuestionConverter questionConverter;
  private final CertificateRepository certificateRepository;
  private final MessageRepository messageRepository;

  @Transactional
  public SendAnswerResponse send(SendAnswerRequest request, String messageId) {
    sendAnswerRequestValidator.validate(request, messageId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getPatient(),
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var message = messageRepository.getById(
        new MessageId(messageId)
    );

    final var certificate = certificateRepository.getById(
        message.certificateId()
    );

    final var messageWithSentAnswer = sendAnswerDomainService.send(
        message,
        certificate,
        actionEvaluation,
        new Content(request.getContent())
    );

    return SendAnswerResponse.builder()
        .question(questionConverter.convert(messageWithSentAnswer,
            messageWithSentAnswer.actions(actionEvaluation, certificate)))
        .build();
  }
}
