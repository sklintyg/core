package se.inera.intyg.certificateservice.application.message.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.message.dto.DeleteAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.DeleteAnswerResponse;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.DeleteAnswerRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.message.service.DeleteAnswerDomainService;

@Service
@RequiredArgsConstructor
public class DeleteAnswerService {

  private final DeleteAnswerRequestValidator deleteAnswerRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final DeleteAnswerDomainService deleteAnswerDomainService;
  private final QuestionConverter questionConverter;
  private final CertificateRepository certificateRepository;
  private final MessageRepository messageRepository;

  @Transactional
  public DeleteAnswerResponse delete(DeleteAnswerRequest request, String messageId) {
    deleteAnswerRequestValidator.validate(request, messageId);

    final var actionEvaluation = actionEvaluationFactory.create(
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

    final var messageWithDeletedAnswer = deleteAnswerDomainService.delete(
        message,
        certificate,
        actionEvaluation
    );

    return DeleteAnswerResponse.builder()
        .question(questionConverter.convert(messageWithDeletedAnswer,
            messageWithDeletedAnswer.actions(actionEvaluation, certificate)))
        .build();
  }
}
