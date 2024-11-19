package se.inera.intyg.certificateservice.domain.unit.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
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
  @Mock
  private static Certificate certificateWithReadPermission;
  @Mock
  private static Certificate certificateWithoutReadPermission;
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();

  private static final MessagesRequest MESSAGES_REQUEST = MessagesRequest.builder().build();

  @Mock
  MessageRepository messageRepository;

  @Mock
  CertificateRepository certificateRepository;

  @InjectMocks
  GetUnitMessagesDomainService getUnitMessagesDomainService;

  @Test
  void shouldReturnMessagesBelongingToCertificateWithReadPermission() {
    final var expectedMessage = Message.builder()
        .certificateId(C1)
        .build();
    final var messageWithoutReadPermission = Message.builder()
        .certificateId(C2)
        .build();

    when(messageRepository.findByMessagesRequest(MESSAGES_REQUEST))
        .thenReturn(List.of(expectedMessage, messageWithoutReadPermission));
    when(certificateRepository.getByIds(List.of(C1, C2)))
        .thenReturn(List.of(certificateWithReadPermission, certificateWithoutReadPermission));
    when(certificateWithReadPermission.allowTo(CertificateActionType.READ,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(certificateWithoutReadPermission.allowTo(CertificateActionType.READ,
        Optional.of(ACTION_EVALUATION))).thenReturn(false);
    when(certificateWithReadPermission.id()).thenReturn(C1);

    final var result = getUnitMessagesDomainService.get(MESSAGES_REQUEST, ACTION_EVALUATION);

    assertEquals(List.of(expectedMessage),
        result.messages());
  }

  @Test
  void shouldReturnCertificatesWithReadPermission() {
    final var expectedMessage = Message.builder()
        .certificateId(C1)
        .build();
    final var messageWithoutReadPermission = Message.builder()
        .certificateId(C2)
        .build();

    when(messageRepository.findByMessagesRequest(MESSAGES_REQUEST))
        .thenReturn(List.of(expectedMessage, messageWithoutReadPermission));
    when(certificateRepository.getByIds(List.of(C1, C2)))
        .thenReturn(List.of(certificateWithReadPermission, certificateWithoutReadPermission));
    when(certificateWithReadPermission.allowTo(CertificateActionType.READ,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(certificateWithoutReadPermission.allowTo(CertificateActionType.READ,
        Optional.of(ACTION_EVALUATION))).thenReturn(false);
    when(certificateWithReadPermission.id()).thenReturn(C1);

    final var result = getUnitMessagesDomainService.get(MESSAGES_REQUEST, ACTION_EVALUATION);

    assertEquals(List.of(certificateWithReadPermission),
        result.certificates());
  }

  @Test
  void shouldReturnEmptyResponseIfNoMessagesAreFound() {
    when(messageRepository.findByMessagesRequest(MESSAGES_REQUEST))
        .thenReturn(Collections.emptyList());

    final var result = getUnitMessagesDomainService.get(MESSAGES_REQUEST, ACTION_EVALUATION);
    assertAll(
        () -> assertTrue(result.messages().isEmpty()),
        () -> assertTrue(result.certificates().isEmpty())
    );
  }
}
