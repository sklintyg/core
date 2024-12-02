package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateEntity.CERTIFICATE_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataMessageEntity.ANSWER_MESSAGE_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataMessageEntity.COMPLEMENT_MESSAGE_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataMessageEntity.CONTACT_MESSAGE_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataMessageEntity.MESSAGE_KEY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataMessageEntity.REMINDER_MESSAGE_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataMessageEntity.complementMessageEntityBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.AJLA_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.domain.message.model.MessageType.ANSWER;
import static se.inera.intyg.certificateservice.domain.message.model.MessageType.COMPLEMENT;
import static se.inera.intyg.certificateservice.domain.message.model.MessageType.CONTACT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.CONTACT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.ANSWER_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.AUTHOR_INCOMING_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CREATED_AFTER_SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.LAST_DATE_TO_REPLY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REFERENCE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REMINDER_MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SUBJECT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;

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
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.SenderReference;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.StaffRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageRelationEntityRepository;

@ExtendWith(MockitoExtension.class)
class MessageEntityMapperTest {

  @Mock
  StaffRepository staffRepository;
  @Mock
  private MessageRelationEntityRepository messageRelationEntityRepository;
  @Mock
  private CertificateEntityRepository certificateEntityRepository;

  @InjectMocks
  private MessageEntityMapper mapper;

  @Nested
  class ComplementMessageToEntity {

    @BeforeEach
    void setUp() {
      doReturn(Optional.of(CERTIFICATE_ENTITY)).when(certificateEntityRepository)
          .findByCertificateId(CERTIFICATE_ID);
    }

    @Test
    void shallIncludeKey() {
      assertEquals(MESSAGE_KEY,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getKey()
      );
    }

    @Test
    void shallIncludeId() {
      assertEquals(MESSAGE_ID,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getId()
      );
    }

    @Test
    void shallIncludeReference() {
      assertEquals(REFERENCE_ID,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getReference()
      );
    }

    @Test
    void shallExcludeReference() {
      final var message = complementMessageBuilder()
          .reference(null)
          .build();

      assertNull(mapper.toEntity(message, MESSAGE_KEY).getReference());
    }

    @Test
    void shallIncludeSubject() {
      assertEquals(SUBJECT,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getSubject()
      );
    }

    @Test
    void shallExcludeSubject() {
      final var message = complementMessageBuilder()
          .subject(null)
          .build();
      assertNull(mapper.toEntity(message, MESSAGE_KEY).getSubject());
    }

    @Test
    void shallIncludeContent() {
      assertEquals(CONTENT,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getContent()
      );
    }

    @Test
    void shallIncludeContactInfo() {
      assertNotNull(mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getContactInfo());
    }

    @Test
    void shallExcludeContactInfo() {
      final var message = complementMessageBuilder()
          .contactInfo(null)
          .build();
      assertNull(mapper.toEntity(message, MESSAGE_KEY).getContactInfo());
    }

    @Test
    void shallIncludeAuthor() {
      assertEquals(AUTHOR_INCOMING_MESSAGE,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getAuthor()
      );
    }

    @Test
    void shallIncludeCreated() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getCreated()
      );
    }

    @Test
    void shallIncludeCreatedWhenMissing() {
      final Message complementMessageWithoutCreated = complementMessageBuilder()
          .created(null)
          .build();

      assertNotNull(mapper.toEntity(complementMessageWithoutCreated, 0).getCreated());
    }

    @Test
    void shallIncludeModified() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getModified()
      );
    }

    @Test
    void shallIncludeModifiedWhenMissing() {
      final Message complementMessageWithoutModified = complementMessageBuilder()
          .modified(null)
          .build();

      assertNotNull(mapper.toEntity(complementMessageWithoutModified, 0).getModified());
    }

    @Test
    void shallIncludeSent() {
      assertEquals(SENT,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getSent()
      );
    }


    @Test
    void shallIncludeForwarded() {
      assertFalse(mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).isForwarded());
    }

    @Test
    void shallIncludeLastDateToReply() {
      assertEquals(LAST_DATE_TO_REPLY,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getLastDateToReply()
      );
    }

    @Test
    void shallIncludeStatusSENT() {
      final var expectedStatus = MessageStatusEntity.builder()
          .key(MessageStatusEnum.SENT.getKey())
          .status(MessageStatusEnum.SENT.name())
          .build();

      assertEquals(expectedStatus,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getStatus()
      );
    }

    @Test
    void shallIncludeStatusHANDLED() {
      final var expectedStatus = MessageStatusEntity.builder()
          .key(MessageStatusEnum.HANDLED.getKey())
          .status(MessageStatusEnum.HANDLED.name())
          .build();

      final Message complementMessageHandled = complementMessageBuilder()
          .status(MessageStatus.HANDLED)
          .build();

      assertEquals(expectedStatus,
          mapper.toEntity(complementMessageHandled, 0).getStatus()
      );
    }

    @Test
    void shallIncludeType() {
      final var expectedType = MessageTypeEntity.builder()
          .key(MessageTypeEnum.COMPLEMENT.getKey())
          .type(MessageTypeEnum.COMPLEMENT.name())
          .build();

      assertEquals(expectedType,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getMessageType()
      );
    }

    @Test
    void shallIncludeCertificate() {
      assertEquals(CERTIFICATE_ENTITY,
          mapper.toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY).getCertificate()
      );
    }
  }

  @Nested
  class ContactMessageToEntity {

    @BeforeEach
    void setUp() {
      doReturn(Optional.of(CERTIFICATE_ENTITY)).when(certificateEntityRepository)
          .findByCertificateId(CERTIFICATE_ID);
    }

    @Test
    void shallIncludeKey() {
      assertEquals(MESSAGE_KEY,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getKey()
      );
    }

    @Test
    void shallIncludeId() {
      assertEquals(MESSAGE_ID,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getId()
      );
    }

    @Test
    void shallIncludeReference() {
      assertEquals(REFERENCE_ID,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getReference()
      );
    }

    @Test
    void shallExcludeReference() {
      final var message = complementMessageBuilder()
          .reference(null)
          .build();

      assertNull(mapper.toEntity(message, MESSAGE_KEY).getReference());
    }

    @Test
    void shallIncludeSubject() {
      assertEquals(SUBJECT,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getSubject()
      );
    }

    @Test
    void shallExcludeSubject() {
      final var message = complementMessageBuilder()
          .subject(null)
          .build();
      assertNull(mapper.toEntity(message, MESSAGE_KEY).getSubject());
    }

    @Test
    void shallIncludeContent() {
      assertEquals(CONTENT,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getContent()
      );
    }

    @Test
    void shallIncludeContactInfo() {
      assertNotNull(mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getContactInfo());
    }

    @Test
    void shallExcludeContactInfo() {
      final var message = complementMessageBuilder()
          .contactInfo(null)
          .build();
      assertNull(mapper.toEntity(message, MESSAGE_KEY).getContactInfo());
    }

    @Test
    void shallIncludeAuthor() {
      assertEquals(AUTHOR_INCOMING_MESSAGE,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getAuthor()
      );
    }

    @Test
    void shallIncludeCreated() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getCreated()
      );
    }

    @Test
    void shallIncludeCreatedWhenMissing() {
      final Message complementMessageWithoutCreated = complementMessageBuilder()
          .created(null)
          .build();

      assertNotNull(mapper.toEntity(complementMessageWithoutCreated, 0).getCreated());
    }

    @Test
    void shallIncludeModified() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getModified()
      );
    }

    @Test
    void shallIncludeModifiedWhenMissing() {
      final Message complementMessageWithoutModified = complementMessageBuilder()
          .modified(null)
          .build();

      assertNotNull(mapper.toEntity(complementMessageWithoutModified, 0).getModified());
    }

    @Test
    void shallIncludeSent() {
      assertEquals(SENT,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getSent()
      );
    }


    @Test
    void shallIncludeForwarded() {
      assertFalse(mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).isForwarded());
    }

    @Test
    void shallIncludeLastDateToReply() {
      assertEquals(LAST_DATE_TO_REPLY,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getLastDateToReply()
      );
    }

    @Test
    void shallIncludeStatusSENT() {
      final var expectedStatus = MessageStatusEntity.builder()
          .key(MessageStatusEnum.SENT.getKey())
          .status(MessageStatusEnum.SENT.name())
          .build();

      assertEquals(expectedStatus,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getStatus()
      );
    }

    @Test
    void shallIncludeStatusHANDLED() {
      final var expectedStatus = MessageStatusEntity.builder()
          .key(MessageStatusEnum.HANDLED.getKey())
          .status(MessageStatusEnum.HANDLED.name())
          .build();

      final Message complementMessageHandled = complementMessageBuilder()
          .status(MessageStatus.HANDLED)
          .build();

      assertEquals(expectedStatus,
          mapper.toEntity(complementMessageHandled, 0).getStatus()
      );
    }

    @Test
    void shallIncludeType() {
      final var expectedType = MessageTypeEntity.builder()
          .key(MessageTypeEnum.CONTACT.getKey())
          .type(MessageTypeEnum.CONTACT.name())
          .build();

      assertEquals(expectedType,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getMessageType()
      );
    }

    @Test
    void shallIncludeCertificate() {
      assertEquals(CERTIFICATE_ENTITY,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getCertificate()
      );
    }

    @Test
    void shallIncludeAuthoredByStaff() {
      doReturn(AJLA_DOKTOR_ENTITY).when(staffRepository).staff(AJLA_DOKTOR);
      assertEquals(AJLA_DOKTOR_ENTITY,
          mapper.toEntity(CONTACT_MESSAGE, MESSAGE_KEY).getAuthoredByStaff());
    }
  }

  @Nested
  class ComplementMessageToDomain {

    @Test
    void shallIncludeId() {
      assertEquals(new MessageId(MESSAGE_ID),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).id()
      );
    }

    @Test
    void shallIncludeReference() {
      assertEquals(new SenderReference(REFERENCE_ID),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).reference()
      );
    }

    @Test
    void shallIncludeSubject() {
      assertEquals(new Subject(SUBJECT),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).subject()
      );
    }

    @Test
    void shallIncludeContent() {
      assertEquals(new Content(CONTENT),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).content()
      );
    }

    @Test
    void shallIncludeAuthor() {
      assertEquals(new Author(AUTHOR_INCOMING_MESSAGE),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).author()
      );
    }

    @Test
    void shallIncludeCreated() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).created()
      );
    }

    @Test
    void shallIncludeModified() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).modified()
      );
    }

    @Test
    void shallIncludeSent() {
      assertEquals(SENT,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).sent()
      );
    }

    @Test
    void shallIncludeForwarded() {
      assertEquals(new Forwarded(false),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).forwarded()
      );
    }

    @Test
    void shallIncludeLastDateToReply() {
      assertEquals(LAST_DATE_TO_REPLY,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).lastDateToReply()
      );
    }

    @Test
    void shallIncludeStatus() {
      assertEquals(MessageStatus.SENT,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).status()
      );
    }

    @Test
    void shallIncludeType() {
      assertEquals(COMPLEMENT,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).type()
      );
    }

    @Test
    void shallIncludeCertificateId() {
      assertEquals(new CertificateId(CERTIFICATE_ID),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).certificateId()
      );
    }

    @Test
    void shallIncludePersonId() {
      assertEquals(ATHENA_REACT_ANDERSSON.id(),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).personId()
      );
    }
  }

  @Nested
  class ContactMessageToDomain {

    @Test
    void shallIncludeId() {
      assertEquals(new MessageId(MESSAGE_ID),
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).id()
      );
    }

    @Test
    void shallIncludeReference() {
      assertEquals(new SenderReference(REFERENCE_ID),
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).reference()
      );
    }

    @Test
    void shallIncludeSubject() {
      assertEquals(new Subject(SUBJECT),
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).subject()
      );
    }

    @Test
    void shallIncludeContent() {
      assertEquals(new Content(CONTENT),
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).content()
      );
    }

    @Test
    void shallIncludeAuthor() {
      assertEquals(new Author(AUTHOR_INCOMING_MESSAGE),
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).author()
      );
    }

    @Test
    void shallIncludeCreated() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).created()
      );
    }

    @Test
    void shallIncludeModified() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).modified()
      );
    }

    @Test
    void shallIncludeSent() {
      assertEquals(SENT,
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).sent()
      );
    }

    @Test
    void shallIncludeForwarded() {
      assertEquals(new Forwarded(false),
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).forwarded()
      );
    }

    @Test
    void shallIncludeLastDateToReply() {
      assertEquals(LAST_DATE_TO_REPLY,
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).lastDateToReply()
      );
    }

    @Test
    void shallIncludeStatus() {
      assertEquals(MessageStatus.SENT,
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).status()
      );
    }

    @Test
    void shallIncludeType() {
      assertEquals(CONTACT,
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).type()
      );
    }

    @Test
    void shallIncludeCertificateId() {
      assertEquals(new CertificateId(CERTIFICATE_ID),
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).certificateId()
      );
    }

    @Test
    void shallIncludePersonId() {
      assertEquals(ATHENA_REACT_ANDERSSON.id(),
          mapper.toDomain(CONTACT_MESSAGE_ENTITY).personId()
      );
    }

    @Test
    void shallIncludeAuthoredByStaff() {
      assertNotNull(mapper.toDomain(CONTACT_MESSAGE_ENTITY).authoredStaff());
    }
  }

  @Nested
  class MessageWithAnswerTests {

    @BeforeEach
    void setUp() {
      doReturn(
          List.of(
              MessageRelationEntity.builder()
                  .messageRelationType(
                      MessageRelationTypeEntity.builder()
                          .type(MessageRelationType.ANSWER.name())
                          .build()
                  )
                  .childMessage(ANSWER_MESSAGE_ENTITY)
                  .build()
          )
      ).when(messageRelationEntityRepository).findByParentMessage(COMPLEMENT_MESSAGE_ENTITY);
    }

    @Test
    void shallIncludeId() {
      assertEquals(new MessageId(ANSWER_ID),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).answer().id()
      );
    }

    @Test
    void shallIncludeReference() {
      assertEquals(new SenderReference(REFERENCE_ID),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).answer().reference()
      );
    }

    @Test
    void shallIncludeSubject() {
      assertEquals(new Subject(SUBJECT),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).answer().subject()
      );
    }

    @Test
    void shallIncludeContent() {
      assertEquals(new Content(CONTENT),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).answer().content()
      );
    }

    @Test
    void shallIncludeAuthor() {
      assertEquals(new Author(AUTHOR_INCOMING_MESSAGE),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).answer().author()
      );
    }

    @Test
    void shallIncludeCreated() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).answer().created()
      );
    }

    @Test
    void shallIncludeModified() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).answer().modified()
      );
    }

    @Test
    void shallIncludeSent() {
      assertEquals(SENT,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).answer().sent()
      );
    }

    @Test
    void shallIncludeStatus() {
      assertEquals(MessageStatus.SENT,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).answer().status()
      );
    }

    @Test
    void shallIncludeContactInfo() {
      assertNotNull(
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).answer().contactInfo()
      );
    }

    @Test
    void shallIncludeType() {
      assertEquals(ANSWER,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).answer().type()
      );
    }
  }

  @Nested
  class MessageWithRemindersTests {

    @BeforeEach
    void setUp() {
      doReturn(
          List.of(
              MessageRelationEntity.builder()
                  .messageRelationType(
                      MessageRelationTypeEntity.builder()
                          .type(MessageRelationType.REMINDER.name())
                          .build()
                  )
                  .childMessage(REMINDER_MESSAGE_ENTITY)
                  .build()
          )
      ).when(messageRelationEntityRepository).findByParentMessage(COMPLEMENT_MESSAGE_ENTITY);
    }

    @Test
    void shallIncludeId() {
      assertEquals(new MessageId(REMINDER_MESSAGE_ID),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).reminders().getFirst().id()
      );
    }

    @Test
    void shallIncludeReference() {
      assertEquals(new SenderReference(REFERENCE_ID),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).reminders().getFirst().reference()
      );
    }

    @Test
    void shallIncludeSubject() {
      assertEquals(new Subject(SUBJECT),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).reminders().getFirst().subject()
      );
    }

    @Test
    void shallIncludeContent() {
      assertEquals(new Content(CONTENT),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).reminders().getFirst().content()
      );
    }

    @Test
    void shallIncludeAuthor() {
      assertEquals(new Author(AUTHOR_INCOMING_MESSAGE),
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).reminders().getFirst().author()
      );
    }

    @Test
    void shallIncludeCreated() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).reminders().getFirst().created()
      );
    }

    @Test
    void shallIncludeSent() {
      assertEquals(SENT,
          mapper.toDomain(COMPLEMENT_MESSAGE_ENTITY).reminders().getFirst().sent()
      );
    }
  }

  @Test
  void shallSetContactInfoToEmptyListIfMissing() {
    final var messageEntity = complementMessageEntityBuilder()
        .contactInfo(null)
        .build();

    assertEquals(Collections.emptyList(),
        mapper.toDomain(messageEntity).contactInfo().lines()
    );
  }
}
