package se.inera.intyg.certificateservice.application.message.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Complement;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageContactInfo;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.Reminder;
import se.inera.intyg.certificateservice.domain.message.model.SenderReference;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

@Component
public class MessageConverter {

  public Message convert(IncomingMessageRequest incomingMessageRequest) {
    return Message.builder()
        .id(new MessageId(incomingMessageRequest.getId()))
        .reference(new SenderReference(incomingMessageRequest.getReferenceId()))
        .certificateId(new CertificateId(incomingMessageRequest.getCertificateId()))
        .personId(
            PersonId.builder()
                .type(incomingMessageRequest.getPersonId().getType().toPersonIdType())
                .id(incomingMessageRequest.getPersonId().getId())
                .build()
        )
        .subject(new Subject(incomingMessageRequest.getSubject()))
        .content(new Content(incomingMessageRequest.getContent()))
        .author(new Author(incomingMessageRequest.getSentBy().name()))
        .sent(incomingMessageRequest.getSent())
        .type(incomingMessageRequest.getType().toMessageType())
        .status(MessageStatus.SENT)
        .contactInfo(new MessageContactInfo(incomingMessageRequest.getContactInfo()))
        .lastDateToReply(incomingMessageRequest.getLastDateToAnswer())
        .complements(
            incomingMessageRequest.getComplements().stream()
                .map(complement ->
                    Complement.builder()
                        .elementId(new ElementId(complement.getQuestionId()))
                        .content(new Content(complement.getContent()))
                        .build()
                )
                .toList()
        )
        .forwarded(new Forwarded(false))
        .build();
  }

  public Reminder convertReminder(IncomingMessageRequest incomingMessageRequest) {
    return Reminder.builder()
        .id(new MessageId(incomingMessageRequest.getId()))
        .reference(new SenderReference(incomingMessageRequest.getReferenceId()))
        .subject(new Subject(incomingMessageRequest.getSubject()))
        .content(new Content(incomingMessageRequest.getContent()))
        .author(new Author(incomingMessageRequest.getSentBy().name()))
        .sent(incomingMessageRequest.getSent())
        .contactInfo(new MessageContactInfo(incomingMessageRequest.getContactInfo()))
        .build();
  }

  public Answer convertAnswer(IncomingMessageRequest incomingMessageRequest) {
    return Answer.builder()
        .id(new MessageId(incomingMessageRequest.getId()))
        .reference(new SenderReference(incomingMessageRequest.getReferenceId()))
        .subject(new Subject(incomingMessageRequest.getSubject()))
        .content(new Content(incomingMessageRequest.getContent()))
        .author(new Author(incomingMessageRequest.getSentBy().name()))
        .sent(incomingMessageRequest.getSent())
        .contactInfo(new MessageContactInfo(incomingMessageRequest.getContactInfo()))
        .status(MessageStatus.SENT)
        .build();
  }
}
