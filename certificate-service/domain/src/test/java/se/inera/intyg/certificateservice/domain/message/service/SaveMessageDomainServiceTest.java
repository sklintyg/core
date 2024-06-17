package se.inera.intyg.certificateservice.domain.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class SaveMessageDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final Content CONTENT = new Content("content");
  private static final MessageId MESSAGE_ID = new MessageId("messageId");
  @Mock
  MessageRepository messageRepository;
  @Mock
  CertificateRepository certificateRepository;
  @InjectMocks
  SaveMessageDomainService createMessageDomainService;

  @Test
  void shallThrowIfNotAllowedToSaveMessage() {
    final var certificate = mock(Certificate.class);
    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate)
        .allowTo(CertificateActionType.SAVE_MESSAGE, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class, () -> createMessageDomainService.save(
        CERTIFICATE_ID, MESSAGE_ID, CONTENT, ACTION_EVALUATION, MessageType.CONTACT
    ));
  }

  @Test
  void shallUpdateMessage() {
    final var message = mock(Message.class);
    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.SAVE_MESSAGE, Optional.of(ACTION_EVALUATION));
    doReturn(message).when(messageRepository).getById(MESSAGE_ID);

    createMessageDomainService.save(
        CERTIFICATE_ID, MESSAGE_ID, CONTENT, ACTION_EVALUATION, MessageType.CONTACT
    );
    
    verify(message).update(CONTENT, MessageType.CONTACT);
  }

  @Test
  void shallPersistUpdatedMessage() {
    final var expectedMessage = Message.builder().build();
    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.SAVE_MESSAGE, Optional.of(ACTION_EVALUATION));
    doReturn(expectedMessage).when(messageRepository).getById(MESSAGE_ID);

    createMessageDomainService.save(
        CERTIFICATE_ID, MESSAGE_ID, CONTENT, ACTION_EVALUATION, MessageType.CONTACT
    );

    verify(messageRepository).save(expectedMessage);
  }

  @Test
  void shallReturnSavedMessage() {
    final var expectedMessage = Message.builder().build();
    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.SAVE_MESSAGE, Optional.of(ACTION_EVALUATION));
    doReturn(expectedMessage).when(messageRepository).getById(MESSAGE_ID);
    doReturn(expectedMessage).when(messageRepository).save(expectedMessage);

    final var actualMessage = createMessageDomainService.save(
        CERTIFICATE_ID, MESSAGE_ID, CONTENT, ACTION_EVALUATION, MessageType.CONTACT
    );

    assertEquals(expectedMessage, actualMessage);
  }
}
