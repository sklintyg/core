package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.AUTHOR_INCOMING_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.COMPLEMENT_QUESTION_ID_ONE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.COMPLEMENT_TEXT_ONE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTACT_INFO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CREATED_AFTER_SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.LAST_DATE_TO_ANSWER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REFERENCE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SUBJECT;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Complement;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageContactInfo;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.SenderReference;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

public class TestDataMessage {

  public static Message COMPLEMENT_MESSAGE = complementMessageBuilder().build();

  public static Message.MessageBuilder complementMessageBuilder() {
    return Message.builder()
        .id(new MessageId(MESSAGE_ID))
        .reference(new SenderReference(REFERENCE_ID))
        .type(MessageType.COMPLEMENT)
        .certificateId(CERTIFICATE_ID)
        .subject(new Subject(SUBJECT))
        .content(new Content(CONTENT))
        .author(new Author(AUTHOR_INCOMING_MESSAGE))
        .contactInfo(new MessageContactInfo(CONTACT_INFO))
        .sent(SENT)
        .created(CREATED_AFTER_SENT)
        .lastDateToReply(LAST_DATE_TO_ANSWER)
        .forwarded(new Forwarded(false))
        .complements(
            List.of(
                Complement.builder()
                    .elementId(new ElementId(COMPLEMENT_QUESTION_ID_ONE))
                    .content(new Content(COMPLEMENT_TEXT_ONE))
                    .build()
            )
        );
  }
}
