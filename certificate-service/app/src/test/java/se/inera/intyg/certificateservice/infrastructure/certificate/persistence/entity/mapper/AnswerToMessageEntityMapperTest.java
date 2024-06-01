package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.SenderReference;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEnum;

class AnswerToMessageEntityMapperTest {

  private static final String REFERENCE = "Ref123";
  private static final String SUBJECT = "Subject";
  private static final String CONTENT = "Content";
  private static final String AUTHOR = "Author";
  private static final LocalDateTime CREATED = LocalDateTime.now();
  private static final LocalDateTime MODIFIED = LocalDateTime.now();
  private static final LocalDateTime SENT = LocalDateTime.now();
  private static final MessageStatus STATUS = MessageStatus.HANDLED;
  private static final MessageType TYPE = MessageType.COMPLEMENT;
  private static final boolean FORWARDED = true;
  private static final CertificateEntity CERTIFICATE = CertificateEntity.builder().build();
  private AnswerToMessageEntityMapper answerToMessageEntityMapper;

  @BeforeEach
  void setUp() {
    answerToMessageEntityMapper = new AnswerToMessageEntityMapper();
  }

  @Test
  void shallIncludeId() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswer();
    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertNotNull(result.getId());
  }

  @Test
  void shallIncludeReference() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswer();
    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertEquals(REFERENCE, result.getReference());
  }

  @Test
  void shallIncludeSubject() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswer();
    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertEquals(SUBJECT, result.getSubject());
  }

  @Test
  void shallIncludeContent() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswer();
    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertEquals(CONTENT, result.getContent());
  }

  @Test
  void shallIncludeAuthor() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswer();
    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertEquals(AUTHOR, result.getAuthor());
  }

  @Test
  void shallIncludeCreated() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswer();
    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertEquals(CREATED, result.getCreated());
  }

  @Test
  void shallIncludeModified() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswer();
    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertEquals(MODIFIED, result.getModified());
  }

  @Test
  void shallIncludeSent() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswer();
    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertEquals(SENT, result.getSent());
  }

  @Test
  void shallIncludeStatus() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswer();
    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertEquals(MessageStatusEnum.HANDLED.name(), result.getStatus().getStatus());
    assertEquals(MessageStatusEnum.HANDLED.getKey(), result.getStatus().getKey());
  }

  @Test
  void shallIncludeMessageType() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswer();
    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertEquals(MessageTypeEnum.COMPLEMENT.name(), result.getMessageType().getType());
    assertEquals(MessageTypeEnum.COMPLEMENT.getKey(), result.getMessageType().getKey());
  }

  @Test
  void shallIncludeCertificate() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswer();
    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertEquals(CERTIFICATE, result.getCertificate());
  }

  @Test
  void shallIncludeForwarded() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswer();
    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertEquals(FORWARDED, result.isForwarded());
  }

  private MessageEntity createOriginalEntity() {
    return MessageEntity.builder()
        .certificate(CERTIFICATE)
        .forwarded(FORWARDED)
        .build();
  }

  private Answer createAnswer() {
    return Answer.builder()
        .reference(new SenderReference(REFERENCE))
        .subject(new Subject(SUBJECT))
        .content(new Content(CONTENT))
        .author(new Author(AUTHOR))
        .created(CREATED)
        .modified(MODIFIED)
        .sent(SENT)
        .status(STATUS)
        .type(TYPE)
        .build();
  }
}
