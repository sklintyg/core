package se.inera.intyg.certificateservice.application.message.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.INCOMING_ANSWER_MESSAGE;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.INCOMING_COMPLEMENT_MESSAGE;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.INCOMING_REMINDER_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.ANSWER_MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.AUTHOR_INCOMING_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.COMPLEMENT_QUESTION_ID_ONE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.COMPLEMENT_TEXT_ONE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTACT_INFO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.LAST_DATE_TO_REPLY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REFERENCE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REMINDER_CONTENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REMINDER_MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SUBJECT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Complement;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.MessageContactInfo;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.SenderReference;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

@ExtendWith(MockitoExtension.class)
class MessageConverterTest {

  @InjectMocks
  private MessageConverter messageConverter;

  @Nested
  class TestIncomingComplementRequest {

    @Test
    void shallIncludeMessageId() {
      assertEquals(new MessageId(MESSAGE_ID),
          messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).id()
      );
    }

    @Test
    void shallIncludeReferenceId() {
      assertEquals(new SenderReference(REFERENCE_ID),
          messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).reference()
      );
    }

    @Test
    void shallIncludePersonId() {
      final var expectedId = PersonId.builder()
          .id(ATHENA_REACT_ANDERSSON_ID)
          .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
          .build();

      assertEquals(expectedId,
          messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).personId()
      );
    }

    @Test
    void shallIncludeMessageType() {
      assertEquals(MessageType.COMPLEMENT,
          messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).type()
      );
    }

    @Test
    void shallIncludeCertificateId() {
      assertEquals(new CertificateId(CERTIFICATE_ID),
          messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).certificateId()
      );
    }

    @Test
    void shallIncludeAuthor() {
      assertEquals(new Author(AUTHOR_INCOMING_MESSAGE),
          messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).author()
      );
    }

    @Test
    void shallIncludeContactInfo() {
      assertEquals(new MessageContactInfo(CONTACT_INFO),
          messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).contactInfo()
      );
    }

    @Test
    void shallIncludeContent() {
      assertEquals(new Content(CONTENT),
          messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).content()
      );
    }

    @Test
    void shallIncludeLastDateToReply() {
      assertEquals(LAST_DATE_TO_REPLY,
          messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).lastDateToReply()
      );
    }

    @Test
    void shallIncludeSubject() {
      assertEquals(new Subject(SUBJECT),
          messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).subject()
      );
    }

    @Test
    void shallIncludeComplement() {
      final var expectedComplement = List.of(
          Complement.builder()
              .elementId(new ElementId(COMPLEMENT_QUESTION_ID_ONE))
              .content(new Content(COMPLEMENT_TEXT_ONE))
              .build()
      );

      assertEquals(expectedComplement,
          messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).complements()
      );
    }

    @Test
    void shallIncludeForwardedFalse() {
      assertFalse(messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).forwarded().value());
    }

    @Test
    void shallIncludeStatusSENT() {
      assertEquals(MessageStatus.SENT,
          messageConverter.convert(INCOMING_COMPLEMENT_MESSAGE).status()
      );
    }
  }

  @Nested
  class TestIncomingReminder {

    @Test
    void shallIncludeMessageId() {
      assertEquals(new MessageId(REMINDER_MESSAGE_ID),
          messageConverter.convertReminder(INCOMING_REMINDER_MESSAGE).id()
      );
    }

    @Test
    void shallIncludeReferenceId() {
      assertEquals(new SenderReference(REFERENCE_ID),
          messageConverter.convertReminder(INCOMING_REMINDER_MESSAGE).reference()
      );
    }

    @Test
    void shallIncludeAuthor() {
      assertEquals(new Author(AUTHOR_INCOMING_MESSAGE),
          messageConverter.convertReminder(INCOMING_REMINDER_MESSAGE).author()
      );
    }

    @Test
    void shallIncludeContactInfo() {
      assertEquals(new MessageContactInfo(CONTACT_INFO),
          messageConverter.convertReminder(INCOMING_REMINDER_MESSAGE).contactInfo()
      );
    }

    @Test
    void shallIncludeContent() {
      assertEquals(new Content(REMINDER_CONTENT),
          messageConverter.convertReminder(INCOMING_REMINDER_MESSAGE).content()
      );
    }

    @Test
    void shallIncludeSubject() {
      assertEquals(new Subject(SUBJECT),
          messageConverter.convertReminder(INCOMING_REMINDER_MESSAGE).subject()
      );
    }
  }

  @Nested
  class TestIncomingAnswer {

    @Test
    void shallIncludeMessageId() {
      assertEquals(new MessageId(ANSWER_MESSAGE_ID),
          messageConverter.convertAnswer(INCOMING_ANSWER_MESSAGE).id()
      );
    }

    @Test
    void shallIncludeReferenceId() {
      assertEquals(new SenderReference(REFERENCE_ID),
          messageConverter.convertAnswer(INCOMING_ANSWER_MESSAGE).reference()
      );
    }

    @Test
    void shallIncludeAuthor() {
      assertEquals(new Author(AUTHOR_INCOMING_MESSAGE),
          messageConverter.convertAnswer(INCOMING_ANSWER_MESSAGE).author()
      );
    }

    @Test
    void shallIncludeContactInfo() {
      assertEquals(new MessageContactInfo(CONTACT_INFO),
          messageConverter.convertAnswer(INCOMING_ANSWER_MESSAGE).contactInfo()
      );
    }

    @Test
    void shallIncludeContent() {
      assertEquals(new Content(CONTENT),
          messageConverter.convertAnswer(INCOMING_ANSWER_MESSAGE).content()
      );
    }

    @Test
    void shallIncludeSubject() {
      assertEquals(new Subject(SUBJECT),
          messageConverter.convertAnswer(INCOMING_ANSWER_MESSAGE).subject()
      );
    }

    @Test
    void shallIncludeStatus() {
      assertEquals(MessageStatus.SENT,
          messageConverter.convertAnswer(INCOMING_ANSWER_MESSAGE).status()
      );
    }
  }
}
