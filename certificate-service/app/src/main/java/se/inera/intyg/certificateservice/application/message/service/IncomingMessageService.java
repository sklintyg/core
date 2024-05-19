package se.inera.intyg.certificateservice.application.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.domain.message.service.ReceiveMessageDomainService;

@Service
@RequiredArgsConstructor
public class IncomingMessageService {

  private final ReceiveMessageDomainService receiveMessageDomainService;
  private final MessageConverter messageConverter;

  public void receive(IncomingMessageRequest incomingMessageRequest) {
    // validate correct incoming message

    // depending on type of incoming message we use different services
    switch (incomingMessageRequest.getType()) {
      case KOMPLT -> receiveMessageDomainService.receive(
          messageConverter.convert(incomingMessageRequest),
          null
      );
    }

    // Receive new message

    // Receive new reminder

    // Receive new answer
  }
}
