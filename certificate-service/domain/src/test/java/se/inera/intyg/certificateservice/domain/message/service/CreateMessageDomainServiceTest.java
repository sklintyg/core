package se.inera.intyg.certificateservice.domain.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@ExtendWith(MockitoExtension.class)
class CreateMessageDomainServiceTest {


  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final Content CONTENT = new Content("content");
  @Mock
  MessageRepository messageRepository;
  @InjectMocks
  CreateMessageDomainService createMessageDomainService;

  @Test
  void shallThrowIfNotAllowedToCreateMessage() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(false).when(certificate)
        .allowTo(CertificateActionType.CREATE_MESSAGE, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class, () -> createMessageDomainService.create(
        certificate, ACTION_EVALUATION, MessageType.CONTACT, CONTENT
    ));
  }

  @Test
  void shallPersistCreatedMessage() {
    final var expectedMessage = Message.builder().build();
    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.CREATE_MESSAGE, Optional.of(ACTION_EVALUATION));
    doReturn(CERTIFICATE_ID).when(certificate).id();

    try (MockedStatic<Message> messageMockedStatic = Mockito.mockStatic(Message.class)) {
      messageMockedStatic.when(() ->
              Message.create(
                  eq(MessageType.CONTACT),
                  eq(CONTENT),
                  eq(CERTIFICATE_ID),
                  any(Staff.class)
              )
          )
          .thenReturn(expectedMessage);

      createMessageDomainService.create(
          certificate, ACTION_EVALUATION, MessageType.CONTACT, CONTENT
      );

      verify(messageRepository).save(expectedMessage);
    }
  }

  @Test
  void shallReturnCreatedMessage() {
    final var expectedMessage = Message.builder().build();
    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.CREATE_MESSAGE, Optional.of(ACTION_EVALUATION));
    doReturn(expectedMessage).when(messageRepository).save(expectedMessage);
    doReturn(CERTIFICATE_ID).when(certificate).id();

    try (MockedStatic<Message> messageMockedStatic = Mockito.mockStatic(Message.class)) {
      messageMockedStatic.when(() ->
              Message.create(
                  eq(MessageType.CONTACT),
                  eq(CONTENT),
                  eq(CERTIFICATE_ID),
                  any(Staff.class)
              )
          )
          .thenReturn(expectedMessage);

      final var actualMessage = createMessageDomainService.create(
          certificate, ACTION_EVALUATION, MessageType.CONTACT, CONTENT
      );
      assertEquals(expectedMessage, actualMessage);
    }
  }
}