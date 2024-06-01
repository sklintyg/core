package se.inera.intyg.certificateservice.application.message.service;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageResponse;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.HandleMessageRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.message.service.HandleMessageDomainService;

@Service
@RequiredArgsConstructor
public class HandleMessageService {

  private final HandleMessageRequestValidator handleMessageRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final MessageRepository messageRepository;
  private final HandleMessageDomainService handleMessageDomainService;
  private final GetCertificateDomainService getCertificateDomainService;
  private final QuestionConverter questionConverter;

  public HandleMessageResponse handle(HandleMessageRequest request, String messageId) {
    handleMessageRequestValidator.validate(request, messageId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var message = messageRepository.getById(new MessageId(messageId));

    final var certificate = getCertificateDomainService.get(
        message.certificateId(),
        actionEvaluation
    );

    final var updatedMessage = handleMessageDomainService.handle(
        message,
        request.getHandled(),
        certificate,
        actionEvaluation
    );

    return HandleMessageResponse.builder()
        .question(questionConverter.convert(updatedMessage, Collections.emptyList()))
        .build();
  }
}
