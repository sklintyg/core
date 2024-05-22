package se.inera.intyg.certificateservice.application.message.service.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.AUTHOR_INCOMING_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTACT_INFO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CREATED_AFTER_SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.LAST_DATE_TO_REPLY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SUBJECT;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageAction;

@ExtendWith(MockitoExtension.class)
class QuestionConverterTest {

  @Mock
  MessageActionConverter messageActionConverter;
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

  private static final Certificate CERTIFICATE = Certificate.builder().build();
  private static final List<MessageAction> MESSAGE_ACTIONS = List.of(
      MessageAction.builder().build());

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
    assertEquals(AUTHOR_INCOMING_MESSAGE, convert.getAuthor());
  }

  @Test
  void shallIncludeSubject() {
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertEquals(SUBJECT, convert.getSubject());
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
    doReturn(ResourceLinkDTO.builder().build()).when(messageActionConverter)
        .convert(MESSAGE_ACTIONS.get(0));
    final var convert = questionConverter.convert(COMPLEMENT_MESSAGE, MESSAGE_ACTIONS);
    assertFalse(convert.getLinks().isEmpty());
  }
}
