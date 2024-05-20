package se.inera.intyg.certificateservice.application.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.application.message.service.converter.MessageConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.IncomingMessageValidator;
import se.inera.intyg.certificateservice.domain.message.service.ReceiveComplementMessageDomainService;

@Service
@RequiredArgsConstructor
public class IncomingMessageService {

  private final IncomingMessageValidator incomingMessageValidator;
  private final MessageConverter messageConverter;
  private final ReceiveComplementMessageDomainService receiveComplementMessageDomainService;

  public void receive(IncomingMessageRequest incomingMessageRequest) {
    incomingMessageValidator.validate(incomingMessageRequest);

    switch (incomingMessageRequest.getType()) {
      case KOMPLT -> receiveComplementMessageDomainService.receive(
          messageConverter.convert(incomingMessageRequest)
      );
      default -> throw new IllegalArgumentException(
          "Message type '%s' is not supported!".formatted(incomingMessageRequest.getType())
      );
    }
  }
}
