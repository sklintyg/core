package se.inera.intyg.certificateservice.application.message.service.validator;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.IncomingComplementDTO;
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
    validateComplement(incomingMessageRequest);
    validateReminder(incomingMessageRequest);
  }

  private void validateReminder(IncomingMessageRequest incomingMessageRequest) {
    if (MessageTypeDTO.PAMINN.equals(incomingMessageRequest.getType())
        && (incomingMessageRequest.getReminderMessageId() == null
        || incomingMessageRequest.getReminderMessageId().isBlank())) {
      throw new IllegalArgumentException("Required parameter missing: Message.reminderMessageId");
    }
  }

  private void validateComplement(IncomingMessageRequest incomingMessageRequest) {
    if (MessageTypeDTO.KOMPLT.equals(incomingMessageRequest.getType())) {
      if (incomingMessageRequest.getComplements() == null) {
        throw new IllegalArgumentException("Required parameter missing: Message.complement");
      }
      if (complementMissingQuestionId(incomingMessageRequest.getComplements())) {
        throw new IllegalArgumentException(
            "Required parameter missing: Message.complement.questionId"
        );
      }
      if (complementMissingText(incomingMessageRequest.getComplements())) {
        throw new IllegalArgumentException(
            "Required parameter missing: Message.complement.content"
        );
      }
    }
  }

  private boolean complementMissingText(List<IncomingComplementDTO> complements) {
    return complements.stream().anyMatch(complement -> isNullOrBlank(complement.getContent()));
  }

  private boolean complementMissingQuestionId(List<IncomingComplementDTO> complements) {
    return complements.stream().anyMatch(complement -> isNullOrBlank(complement.getQuestionId()));
  }

  private static boolean isNullOrBlank(String incomingMessageRequest) {
    return incomingMessageRequest == null || incomingMessageRequest.isBlank();
  }
}
