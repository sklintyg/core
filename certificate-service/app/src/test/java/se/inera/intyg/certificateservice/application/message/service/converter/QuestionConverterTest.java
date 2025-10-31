package se.inera.intyg.certificateservice.application.message.service.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.CONTACT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.contactMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.AUTHOR_INCOMING_MESSAGE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTACT_INFO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CREATED_AFTER_SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.LAST_DATE_TO_REPLY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SUBJECT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.message.dto.ComplementDTO;
import se.inera.intyg.certificateservice.application.message.dto.QuestionTypeDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionLink;
import se.inera.intyg.certificateservice.domain.message.model.MessageContactInfo;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;

@ExtendWith(MockitoExtension.class)
class QuestionConverterTest {

  private static final List<MessageActionLink> MESSAGE_ACTIONS = List.of(
      MessageActionLink.builder().build());
  @Mock
  MessageActionLinkConverter messageActionLinkConverter;
  @Mock
  CertificateRepository certificateRepository;
  @Mock
  CertificateRelationConverter certificateRelationConverter;
  @Mock
  ReminderConverter reminderConverter;
  @Mock
  ComplementConverter complementConverter;
  @InjectMocks
  QuestionConverter questionConverter;

  private static final Certificate CERTIFICATE = MedicalCertificate.builder().build();

  @BeforeEach
  void setUp() {
    doReturn(CERTIFICATE).when(certificateRepository).getById(CERTIFICATE_ID);
  }

  @Test
  void shallIncludeId() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(MESSAGE_ID, convert.getId());
  }

  @Test
  void shallIncludeCertificateId() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(CERTIFICATE_ID.id(), convert.getCertificateId());
  }

  @Test
  void shallIncludeType() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(QuestionTypeDTO.COMPLEMENT, convert.getType());
  }

  @Test
  void shallIncludeAuthor() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(AUTHOR_INCOMING_MESSAGE_NAME, convert.getAuthor());
  }

  @Test
  void shallIncludeAuthorFromAuthoredByStaffIfIncluded() {
    final var convert = questionConverter.convert(CONTACT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(AJLA_DOKTOR.name().fullName(), convert.getAuthor());
  }

  @Test
  void shallIncludeSubject() {
    final var expected = MessageType.COMPLEMENT.displayName() + " - " + SUBJECT;

    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(expected, convert.getSubject());
  }

  @Test
  void shallIncludeSubjectPrefixedWithTypeWhenQuestionToCare() {
    final var expected = MessageType.OTHER.displayName() + " - " + SUBJECT;

    final var otherMessage = contactMessageBuilder()
        .type(MessageType.OTHER)
        .authoredStaff(null)
        .build();

    final var convert = questionConverter.convert(otherMessage, MESSAGE_ACTIONS);

    assertEquals(expected, convert.getSubject());
  }

  @Test
  void shallIncludeSent() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(SENT, convert.getSent());
  }

  @Test
  void shallIncludeIsHandled() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertFalse(convert.isHandled());
  }

  @Test
  void shallIncludeIsForwarded() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertFalse(convert.isForwarded());
  }

  @Test
  void shallIncludeMessage() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(CONTENT, convert.getMessage());
  }

  @Test
  void shallIncludeLastUpdate() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(CREATED_AFTER_SENT, convert.getLastUpdate());
  }

  @Test
  void shallIncludeLastDateToReply() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(LAST_DATE_TO_REPLY, convert.getLastDateToReply());
  }

  @Test
  void shallIncludeContactInfo() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertAll(
        () -> assertEquals(CONTACT_INFO.get(0), convert.getContactInfo().get(0)),
        () -> assertEquals(CONTACT_INFO.get(1), convert.getContactInfo().get(1)),
        () -> assertEquals(CONTACT_INFO.get(2), convert.getContactInfo().get(2))
    );
  }

  @Test
  void shallIncludeAnsweredByCertificate() {
    final var expectedRelation = CertificateRelationDTO.builder().build();

    doReturn(expectedRelation).when(certificateRelationConverter)
        .convert(Optional.empty());

    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(expectedRelation, convert.getAnsweredByCertificate());
  }

  @Test
  void shallExcludeAnsweredByCertificate() {
    final var messageWithoutAnsweredByCertificate = complementMessageBuilder()
        .type(MessageType.CONTACT)
        .build();
    final var convert = questionConverter.convert(messageWithoutAnsweredByCertificate,
        MESSAGE_ACTIONS);
    assertNull(convert.getAnsweredByCertificate());
  }

  @Test
  void shallIncludeReminders() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertNotNull(convert.getReminders());
  }

  @Test
  void shallIncludeComplements() {
    final var expectedComplement = ComplementDTO.builder().build();

    doReturn(expectedComplement).when(complementConverter)
        .convert(COMPLEMENT_MESSAGE.complements().get(0), CERTIFICATE);

    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(expectedComplement, convert.getComplements().get(0));
  }

  @Test
  void shallIncludeLinks() {
    final var messageAction = MessageActionLink.builder().build();
    final var message = complementMessageBuilder()
        .build();
    doReturn(ResourceLinkDTO.builder().build()).when(messageActionLinkConverter)
        .convert(messageAction);
    final var convert = questionConverter.convert(message, List.of(messageAction));
    assertFalse(convert.getLinks().isEmpty());
  }

  @Nested
  class AnswerTests {

    private final Message message = complementMessageBuilder()
        .answer(
            Answer.builder()
                .id(new MessageId("id"))
                .authoredStaff(AJLA_DOKTOR)
                .sent(LocalDateTime.now())
                .content(new Content("content"))
                .contactInfo(new MessageContactInfo(List.of("info")))

                .build()
        )
        .build();

    @Test
    void shallIncludeId() {
      final var expectedId = "id";
      final var convert = questionConverter.convert(message, MESSAGE_ACTIONS);
      assertEquals(expectedId, convert.getAnswer().getId());
    }


    @Test
    void shallIncludeAuthor() {
      final var convert = questionConverter.convert(message, MESSAGE_ACTIONS);
      assertEquals(AJLA_DOKTOR.name().fullName(), convert.getAnswer().getAuthor());
    }


    @Test
    void shallIncludeSent() {
      final var convert = questionConverter.convert(message, MESSAGE_ACTIONS);
      assertNotNull(convert.getAnswer().getSent());
    }

    @Test
    void shallIncludeMessage() {
      final var expectedMessage = "content";
      final var convert = questionConverter.convert(message, MESSAGE_ACTIONS);
      assertEquals(expectedMessage, convert.getAnswer().getMessage());
    }

    @Test
    void shallIncludeContactInfo() {
      final var expectedContactInfo = List.of("info");
      final var convert = questionConverter.convert(message, MESSAGE_ACTIONS);
      assertEquals(expectedContactInfo, convert.getAnswer().getContactInfo());
    }

    @Test
    void shallExcludeContactInfoIfNull() {
      final var messageWithAnswerWithoutContactInfo = complementMessageBuilder()
          .answer(
              Answer.builder()
                  .id(new MessageId("id"))
                  .authoredStaff(AJLA_DOKTOR)
                  .sent(LocalDateTime.now())
                  .content(new Content("content"))
                  .build()
          )
          .build();
      final var convert = questionConverter.convert(messageWithAnswerWithoutContactInfo,
          MESSAGE_ACTIONS);
      assertNull(convert.getAnswer().getContactInfo());
    }

    @Test
    void shallExcludeContactInfoIfEmpty() {
      final var messageWithAnswerWithoutContactInfo = complementMessageBuilder()
          .answer(
              Answer.builder()
                  .id(new MessageId("id"))
                  .authoredStaff(AJLA_DOKTOR)
                  .sent(LocalDateTime.now())
                  .content(new Content("content"))
                  .contactInfo(new MessageContactInfo(Collections.emptyList()))
                  .build()
          )
          .build();
      final var convert = questionConverter.convert(messageWithAnswerWithoutContactInfo,
          MESSAGE_ACTIONS);
      assertNull(convert.getAnswer().getContactInfo());
    }

    @Test
    void shallExcludeAnswer() {
      final var messageWithoutAnswer = complementMessageBuilder().build();
      final var convert = questionConverter.convert(messageWithoutAnswer, MESSAGE_ACTIONS);
      assertNull(convert.getAnswer());
    }

    @Test
    void shallSetAuthorToAuthorIfAuthoredByStaffIsNull() {
      final var messageWithoutAuthoredStaff = complementMessageBuilder()
          .answer(
              Answer.builder()
                  .id(new MessageId("id"))
                  .author(new Author("Försäkringskassan"))
                  .sent(LocalDateTime.now())
                  .content(new Content("content"))
                  .contactInfo(new MessageContactInfo(List.of("info")))
                  .build()
          )
          .build();
      final var convert = questionConverter.convert(messageWithoutAuthoredStaff, MESSAGE_ACTIONS);
      assertEquals("Försäkringskassan", convert.getAnswer().getAuthor());
    }
  }
}