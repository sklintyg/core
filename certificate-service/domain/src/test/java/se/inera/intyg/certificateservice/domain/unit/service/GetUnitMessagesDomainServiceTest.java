package se.inera.intyg.certificateservice.domain.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.MessagesRequest;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class GetUnitMessagesDomainServiceTest {

  private static final CertificateId C1 = new CertificateId("C1");
  private static final CertificateId C2 = new CertificateId("C2");
  private static final CertificateId C3 = new CertificateId("C3");
  private static Certificate certificateWithReadPermission;
  private static Certificate certificateWithoutReadPermission;
  private static Certificate certificateWithReminder;
  private static Message messageForC1;
  private static Message secondMessageForC1;
  private static Message messageForC2;
  private static Message messageWithReminder;
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();

  private static final MessagesRequest MESSAGES_REQUEST = MessagesRequest.builder().build();

  @Mock
  MessageRepository messageRepository;

  @Mock
  CertificateRepository certificateRepository;

  @InjectMocks
  GetUnitMessagesDomainService getUnitMessagesDomainService;

  @BeforeEach
  void setup() {
    certificateWithReadPermission = mock(Certificate.class);
    certificateWithoutReadPermission = mock(Certificate.class);
    certificateWithReminder = mock(Certificate.class);
    messageForC1 = mock(Message.class);
    secondMessageForC1 = mock(Message.class);
    messageForC2 = mock(Message.class);
    messageWithReminder = mock(Message.class);

    when(certificateWithReadPermission.id())
        .thenReturn(C1);
    when(certificateWithReminder.id())
        .thenReturn(C3);

    when(messageForC1.certificateId())
        .thenReturn(C1);
    when(secondMessageForC1.certificateId())
        .thenReturn(C1);
    when(messageForC2.certificateId())
        .thenReturn(C2);
    when(messageWithReminder.certificateId())
        .thenReturn(C3);

    when(certificateWithReadPermission.allowTo(CertificateActionType.READ,
        Optional.of(ACTION_EVALUATION)))
        .thenReturn(true);
    when(certificateWithReminder.allowTo(CertificateActionType.READ,
        Optional.of(ACTION_EVALUATION)))
        .thenReturn(true);
    when(certificateWithoutReadPermission.allowTo(CertificateActionType.READ,
        Optional.of(ACTION_EVALUATION)))
        .thenReturn(false);

    when(messageRepository.findByMessagesRequest(MESSAGES_REQUEST))
        .thenReturn(
            List.of(messageForC1, messageForC2, secondMessageForC1, messageWithReminder));
    when(certificateRepository.getById(C1))
        .thenReturn(certificateWithReadPermission);
    when(certificateRepository.getById(C2))
        .thenReturn(certificateWithoutReadPermission);
    when(certificateRepository.getById(C3))
        .thenReturn(certificateWithReminder);
  }

  @Test
  void shouldReturnMessagesBelongingToCertificateWithReadPermissionExcludingRemindersAnswersAndMissing() {
    final var result = getUnitMessagesDomainService.get(MESSAGES_REQUEST, ACTION_EVALUATION);

    assertEquals(List.of(messageForC1, secondMessageForC1, messageWithReminder),
        result.messages());
  }

  @Test
  void shouldReturnCertificatesWithReadPermission() {
    final var result = getUnitMessagesDomainService.get(MESSAGES_REQUEST, ACTION_EVALUATION);

    assertEquals(List.of(certificateWithReadPermission, certificateWithReminder),
        result.certificates());
  }
}
