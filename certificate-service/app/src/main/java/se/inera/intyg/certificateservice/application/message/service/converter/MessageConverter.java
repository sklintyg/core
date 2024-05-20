package se.inera.intyg.certificateservice.application.message.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Complement;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageContactInfo;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.SenderReference;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

@Component
public class MessageConverter {

  public Message convert(IncomingMessageRequest incomingMessageRequest) {
    return Message.builder()
        .id(new MessageId(incomingMessageRequest.getId()))
        .reference(new SenderReference(incomingMessageRequest.getReferenceId()))
        .certificateId(new CertificateId(incomingMessageRequest.getCertificateId()))
        .subject(new Subject(incomingMessageRequest.getSubject()))
        .content(new Content(incomingMessageRequest.getContent()))
        .author(new Author(incomingMessageRequest.getSentBy().name()))
        .sent(incomingMessageRequest.getSent())
        .type(MessageType.COMPLEMENT)
        .contactInfo(new MessageContactInfo(incomingMessageRequest.getContactInfo()))
        .lastDateToReply(incomingMessageRequest.getLastDateToAnswer())
        .complements(
            incomingMessageRequest.getComplements().stream()
                .map(complement ->
                    Complement.builder()
                        .elementId(new ElementId(complement.getQuestionId()))
                        .content(new Content(complement.getText()))
                        .build()
                )
                .toList()
        )
        .build();
  }
}
