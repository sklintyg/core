package se.inera.intyg.certificateservice.application.message.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageResponse;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.SendMessageRequestValidator;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.service.SendMessageDomainService;

@Service
@RequiredArgsConstructor
public class SendMessageService {

  private final SendMessageRequestValidator sendMessageRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final SendMessageDomainService sendMessageDomainService;
  private final QuestionConverter questionConverter;

  @Transactional
  public SendMessageResponse send(SendMessageRequest request, String messageId) {
    sendMessageRequestValidator.validate(request, messageId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getPatient(),
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var sentMessage = sendMessageDomainService.send(
        new MessageId(messageId),
        actionEvaluation
    );

    return SendMessageResponse.builder()
        .question(questionConverter.convert(sentMessage, Collections.emptyList()))
        .build();
  }
}
