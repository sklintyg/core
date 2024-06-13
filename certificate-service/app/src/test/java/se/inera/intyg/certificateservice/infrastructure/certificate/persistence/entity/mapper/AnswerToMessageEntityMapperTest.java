package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.SenderReference;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.StaffRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;

@ExtendWith(MockitoExtension.class)
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
  @Mock
  private StaffRepository staffRepository;

  @BeforeEach
  void setUp() {
    answerToMessageEntityMapper = new AnswerToMessageEntityMapper(staffRepository);
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

  @Test
  void shallIncludeAuthoredByStaff() {
    final var expectedStaff = StaffEntity.builder().build();

    final var originalEntity = createOriginalEntity();
    final var authoredStaff = Staff.create(AJLA_DOKTOR);
    final var answer = createAnswerBuilder()
        .authoredStaff(authoredStaff)
        .build();

    doReturn(expectedStaff).when(staffRepository).staff(authoredStaff);

    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertEquals(expectedStaff, result.getAuthoredByStaff());
  }

  @Test
  void shallExcludeAuthoredByStaff() {
    final var originalEntity = createOriginalEntity();
    final var answer = createAnswerBuilder()
        .authoredStaff(null)
        .build();

    final var result = answerToMessageEntityMapper.toEntity(originalEntity, answer);
    assertNull(result.getAuthoredByStaff());
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

  private Answer.AnswerBuilder createAnswerBuilder() {
    return Answer.builder()
        .reference(new SenderReference(REFERENCE))
        .subject(new Subject(SUBJECT))
        .content(new Content(CONTENT))
        .author(new Author(AUTHOR))
        .created(CREATED)
        .modified(MODIFIED)
        .sent(SENT)
        .status(STATUS)
        .type(TYPE);
  }
}
