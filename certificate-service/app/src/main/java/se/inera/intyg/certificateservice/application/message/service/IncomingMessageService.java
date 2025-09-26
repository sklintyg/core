package se.inera.intyg.certificateservice.application.message.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.application.message.service.converter.MessageConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.IncomingMessageValidator;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.service.ReceiveAnswerMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.ReceiveComplementMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.ReceiveQuestionMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.ReceiveReminderMessageDomainService;

@Service
@RequiredArgsConstructor
public class IncomingMessageService {

  private final IncomingMessageValidator incomingMessageValidator;
  private final MessageConverter messageConverter;
  private final ReceiveComplementMessageDomainService receiveComplementMessageDomainService;
  private final ReceiveReminderMessageDomainService receiveReminderMessageDomainService;
  private final ReceiveQuestionMessageDomainService receiveQuestionMessageDomainService;
  private final ReceiveAnswerMessageDomainService receiveAnswerMessageDomainService;

  @Transactional
  public void receive(IncomingMessageRequest incomingMessageRequest) {
    incomingMessageValidator.validate(incomingMessageRequest);

    switch (incomingMessageRequest.getType()) {
      case KOMPLT -> receiveComplementMessageDomainService.receive(
          messageConverter.convert(incomingMessageRequest)
      );
      case PAMINN -> receiveReminderMessageDomainService.receive(
          new MessageId(incomingMessageRequest.getReminderMessageId()),
          messageConverter.convertReminder(incomingMessageRequest)
      );
      default -> {
        if (isAnswer(incomingMessageRequest)) {
          receiveAnswerMessageDomainService.receive(
              new MessageId(incomingMessageRequest.getAnswerMessageId()),
              messageConverter.convertAnswer(incomingMessageRequest)
          );
        } else {
          receiveQuestionMessageDomainService.receive(
              messageConverter.convert(incomingMessageRequest)
          );
        }
      }
    }
  }

  private static boolean isAnswer(IncomingMessageRequest incomingMessageRequest) {
    return incomingMessageRequest.getAnswerMessageId() != null;
  }
}
