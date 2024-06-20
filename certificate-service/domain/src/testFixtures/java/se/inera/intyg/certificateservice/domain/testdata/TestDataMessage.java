package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.ANSWER_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.ANSWER_REFERENCE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.AUTHOR_INCOMING_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.COMPLEMENT_QUESTION_ID_ONE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.COMPLEMENT_TEXT_ONE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTACT_INFO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CREATED_AFTER_SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.LAST_DATE_TO_REPLY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REFERENCE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REMINDER_CONTENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REMINDER_MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REMINDER_REFERENCE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SUBJECT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Answer.AnswerBuilder;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Complement;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.Message.MessageBuilder;
import se.inera.intyg.certificateservice.domain.message.model.MessageContactInfo;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Reminder;
import se.inera.intyg.certificateservice.domain.message.model.Reminder.ReminderBuilder;
import se.inera.intyg.certificateservice.domain.message.model.SenderReference;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

public class TestDataMessage {

  public static final Message COMPLEMENT_MESSAGE = complementMessageBuilder().build();
  public static final Message CONTACT_MESSAGE = contactMessageBuilder().build();
  public static final Answer ANSWER = answerBuilder().build();
  public static final Reminder REMINDER = reminderBuilder().build();

  public static MessageBuilder complementMessageBuilder() {
    return Message.builder()
        .id(new MessageId(MESSAGE_ID))
        .reference(new SenderReference(REFERENCE_ID))
        .type(MessageType.COMPLEMENT)
        .status(MessageStatus.SENT)
        .certificateId(CERTIFICATE_ID)
        .personId(ATHENA_REACT_ANDERSSON.id())
        .subject(new Subject(SUBJECT))
        .content(new Content(CONTENT))
        .author(new Author(AUTHOR_INCOMING_MESSAGE))
        .contactInfo(new MessageContactInfo(CONTACT_INFO))
        .sent(SENT)
        .created(CREATED_AFTER_SENT)
        .modified(CREATED_AFTER_SENT)
        .lastDateToReply(LAST_DATE_TO_REPLY)
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

  public static MessageBuilder contactMessageBuilder() {
    return Message.builder()
        .id(new MessageId(MESSAGE_ID))
        .reference(new SenderReference(REFERENCE_ID))
        .type(MessageType.CONTACT)
        .status(MessageStatus.SENT)
        .certificateId(CERTIFICATE_ID)
        .personId(ATHENA_REACT_ANDERSSON.id())
        .subject(new Subject(SUBJECT))
        .content(new Content(CONTENT))
        .author(new Author(AUTHOR_INCOMING_MESSAGE))
        .contactInfo(new MessageContactInfo(CONTACT_INFO))
        .sent(SENT)
        .created(CREATED_AFTER_SENT)
        .modified(CREATED_AFTER_SENT)
        .lastDateToReply(LAST_DATE_TO_REPLY)
        .authoredStaff(AJLA_DOKTOR)
        .forwarded(new Forwarded(false));
  }

  public static AnswerBuilder answerBuilder() {
    return Answer.builder()
        .id(new MessageId(ANSWER_ID))
        .reference(new SenderReference(ANSWER_REFERENCE_ID))
        .type(MessageType.COMPLEMENT)
        .status(MessageStatus.SENT)
        .subject(new Subject(SUBJECT))
        .content(new Content(CONTENT))
        .author(new Author(AUTHOR_INCOMING_MESSAGE))
        .contactInfo(new MessageContactInfo(CONTACT_INFO))
        .sent(SENT)
        .created(CREATED_AFTER_SENT)
        .modified(CREATED_AFTER_SENT)
        .authoredStaff(AJLA_DOKTOR);
  }

  public static ReminderBuilder reminderBuilder() {
    return Reminder.builder()
        .id(new MessageId(REMINDER_MESSAGE_ID))
        .reference(new SenderReference(REMINDER_REFERENCE_ID))
        .subject(new Subject(SUBJECT))
        .content(new Content(REMINDER_CONTENT))
        .author(new Author(AUTHOR_INCOMING_MESSAGE))
        .contactInfo(new MessageContactInfo(CONTACT_INFO))
        .sent(SENT)
        .created(CREATED_AFTER_SENT);
  }
}
