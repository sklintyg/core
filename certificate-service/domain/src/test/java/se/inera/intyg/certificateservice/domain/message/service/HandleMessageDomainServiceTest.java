package se.inera.intyg.certificateservice.domain.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class HandleMessageDomainServiceTest {

  private static Message message;
  private static Message savedMessage;
  private static Certificate certificate;
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();

  @Mock
  private MessageRepository messageRepository;

  @InjectMocks
  private HandleMessageDomainService handleMessageDomainService;

  @Test
  void shouldThrowErrorIfNotAllowedToHandle() {
    certificate = mock(Certificate.class);

    when(certificate.id())
        .thenReturn(new CertificateId("ID"));
    when(certificate.allowTo(CertificateActionType.HANDLE_COMPLEMENT,
        Optional.of(ACTION_EVALUATION)))
        .thenReturn(false);

    assertThrows(
        CertificateActionForbidden.class,
        () -> handleMessageDomainService.handle(message, true, certificate, ACTION_EVALUATION)
    );
  }

  @Nested
  class ActionAllowed {

    @BeforeEach
    void setup() {
      message = mock(Message.class);
      savedMessage = mock(Message.class);
      certificate = mock(Certificate.class);
      when(certificate.allowTo(CertificateActionType.HANDLE_COMPLEMENT,
          Optional.of(ACTION_EVALUATION)))
          .thenReturn(true);
    }

    @Nested
    class HandledTrue {

      @BeforeEach
      void setup() {
        when(message.type())
            .thenReturn(MessageType.COMPLEMENT);
        when(messageRepository.save(message))
            .thenReturn(savedMessage);
      }

      @Test
      void shouldSetHandleMessageAsTrue() {
        handleMessageDomainService.handle(message, true, certificate, ACTION_EVALUATION);
        verify(message).handle();
      }

      @Test
      void shouldReturnSavedCertificateIfTrue() {
        final var response = handleMessageDomainService.handle(message, true, certificate,
            ACTION_EVALUATION);
        assertEquals(savedMessage, response);
      }
    }

    @Nested
    class HandledFalse {

      @Test
      void shouldNotUpdateMessageIfFalse() {
        handleMessageDomainService.handle(message, false, certificate, ACTION_EVALUATION);
        verify(message, times(0)).handle();
        verify(messageRepository, times(0)).save(message);
      }

      @Test
      void shouldReturnOriginalCertificateIfFalse() {
        final var response = handleMessageDomainService.handle(message, false, certificate,
            ACTION_EVALUATION);
        assertEquals(message, response);
      }
    }
  }
}