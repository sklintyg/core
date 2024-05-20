package se.inera.intyg.certificateservice.application.message.service.validator;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.MessageTypeDTO;

@Component
public class IncomingMessageValidator {

  public void validate(IncomingMessageRequest incomingMessageRequest) {
    if (isNullOrBlank(incomingMessageRequest.getId())) {
      throw new IllegalArgumentException("Message.id is missing");
    }
    if (isNullOrBlank(incomingMessageRequest.getCertificateId())) {
      throw new IllegalArgumentException("Message.certificateId is missing");
    }
    if (isNullOrBlank(incomingMessageRequest.getSubject())) {
      throw new IllegalArgumentException("Message.subject is missing");
    }
    if (isNullOrBlank(incomingMessageRequest.getContent())) {
      throw new IllegalArgumentException("Message.content is missing");
    }
    if (incomingMessageRequest.getSent() == null) {
      throw new IllegalArgumentException("Message.sent is missing");
    }
    if (incomingMessageRequest.getSentBy() == null) {
      throw new IllegalArgumentException("Message.sentBy is missing");
    }
    if (incomingMessageRequest.getContactInfo() == null) {
      throw new IllegalArgumentException("Message.contactInfo is missing");
    }
    if (incomingMessageRequest.getPersonId() == null) {
      throw new IllegalArgumentException("Required parameter missing: Message.personId");
    }
    if (isNullOrBlank(incomingMessageRequest.getPersonId().getId())) {
      throw new IllegalArgumentException("Required parameter missing: Message.personId.id");
    }
    if (incomingMessageRequest.getPersonId().getType() == null) {
      throw new IllegalArgumentException("Required parameter missing: Message.personId.type");
    }
    if (MessageTypeDTO.KOMPLT.equals(incomingMessageRequest.getType())
        && incomingMessageRequest.getComplements() == null) {
      throw new IllegalArgumentException("Required parameter missing: Message.complement");
    }
  }

  private static boolean isNullOrBlank(String incomingMessageRequest) {
    return incomingMessageRequest == null || incomingMessageRequest.isBlank();
  }
}
