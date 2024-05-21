package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateEntity.CERTIFICATE_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataMessageEntity.COMPLEMENT_MESSAGE_ENTITY;
import static se.inera.intyg.certificateservice.domain.message.model.MessageType.COMPLEMENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.AUTHOR_INCOMING_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CREATED_AFTER_SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.LAST_DATE_TO_REPLY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REFERENCE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SUBJECT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;

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
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;

@ExtendWith(MockitoExtension.class)
class MessageEntityMapperTest {

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
    void shallIncludeId() {
      assertEquals(MESSAGE_ID,
          mapper.toEntity(COMPLEMENT_MESSAGE).getId()
      );
    }

    @Test
    void shallIncludeReference() {
      assertEquals(REFERENCE_ID,
          mapper.toEntity(COMPLEMENT_MESSAGE).getReference()
      );
    }

    @Test
    void shallIncludeSubject() {
      assertEquals(SUBJECT,
          mapper.toEntity(COMPLEMENT_MESSAGE).getSubject()
      );
    }

    @Test
    void shallIncludeContent() {
      assertEquals(CONTENT,
          mapper.toEntity(COMPLEMENT_MESSAGE).getContent()
      );
    }

    @Test
    void shallIncludeAuthor() {
      assertEquals(AUTHOR_INCOMING_MESSAGE,
          mapper.toEntity(COMPLEMENT_MESSAGE).getAuthor()
      );
    }

    @Test
    void shallIncludeCreated() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toEntity(COMPLEMENT_MESSAGE).getCreated()
      );
    }

    @Test
    void shallIncludeCreatedWhenMissing() {
      final Message complementMessageWithoutCreated = complementMessageBuilder()
          .created(null)
          .build();

      assertNotNull(mapper.toEntity(complementMessageWithoutCreated).getCreated());
    }

    @Test
    void shallIncludeModified() {
      assertEquals(CREATED_AFTER_SENT,
          mapper.toEntity(COMPLEMENT_MESSAGE).getModified()
      );
    }

    @Test
    void shallIncludeModifiedWhenMissing() {
      final Message complementMessageWithoutModified = complementMessageBuilder()
          .modified(null)
          .build();

      assertNotNull(mapper.toEntity(complementMessageWithoutModified).getModified());
    }

    @Test
    void shallIncludeSent() {
      assertEquals(SENT,
          mapper.toEntity(COMPLEMENT_MESSAGE).getSent()
      );
    }

    @Test
    void shallIncludeForwarded() {
      assertFalse(mapper.toEntity(COMPLEMENT_MESSAGE).isForwarded());
    }

    @Test
    void shallIncludeLastDateToReply() {
      assertEquals(LAST_DATE_TO_REPLY,
          mapper.toEntity(COMPLEMENT_MESSAGE).getLastDateToReply()
      );
    }

    @Test
    void shallIncludeStatus() {
      final var expectedStatus = MessageStatusEntity.builder()
          .key(MessageStatusEnum.SENT.getKey())
          .status(MessageStatusEnum.SENT.name())
          .build();

      assertEquals(expectedStatus,
          mapper.toEntity(COMPLEMENT_MESSAGE).getStatus()
      );
    }

    @Test
    void shallIncludeType() {
      final var expectedType = MessageTypeEntity.builder()
          .key(MessageTypeEnum.COMPLEMENT.getKey())
          .type(MessageTypeEnum.COMPLEMENT.name())
          .build();

      assertEquals(expectedType,
          mapper.toEntity(COMPLEMENT_MESSAGE).getMessageType()
      );
    }

    @Test
    void shallIncludeCertificate() {
      assertEquals(CERTIFICATE_ENTITY,
          mapper.toEntity(COMPLEMENT_MESSAGE).getCertificate()
      );
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
}