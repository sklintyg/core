package se.inera.intyg.certificateservice.domain.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
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

  @Nested
  class HandleComplementMessage {

    @BeforeEach
    void setup() {
      message = mock(Message.class);
      when(message.type())
          .thenReturn(MessageType.COMPLEMENT);
    }

    @Test
    void shouldThrowErrorIfNotAllowedToHandle() {
      certificate = mock(MedicalCertificate.class);

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
        savedMessage = mock(Message.class);
        certificate = mock(MedicalCertificate.class);
        when(certificate.allowTo(CertificateActionType.HANDLE_COMPLEMENT,
            Optional.of(ACTION_EVALUATION)))
            .thenReturn(true);
      }

      @Nested
      class HandledTrue {

        @BeforeEach
        void setup() {

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

  @Nested
  class HandleAdministrativeMessage {

    private final Certificate certificate = mock(MedicalCertificate.class);
    private final Message savedMessage = Message.builder().build();
    private final Message.MessageBuilder administrativeMessage = Message.builder()
        .type(MessageType.CONTACT);

    @Test
    void shouldThrowExceptionIfNotAllowedToHandle() {
      final var message = administrativeMessage.build();
      when(certificate.id()).thenReturn(new CertificateId("ID"));
      when(certificate.allowTo(CertificateActionType.HANDLE_MESSAGE,
          Optional.of(ACTION_EVALUATION))).thenReturn(false);

      assertThrows(CertificateActionForbidden.class, () -> handleMessageDomainService
          .handle(message, true, certificate, ACTION_EVALUATION));
    }

    @Nested
    class HandleAdministrativeMessageAllowed {

      @BeforeEach
      void setup() {
        when(messageRepository.save(any(Message.class))).thenReturn(savedMessage);
        when(certificate.allowTo(CertificateActionType.HANDLE_MESSAGE,
            Optional.of(ACTION_EVALUATION))).thenReturn(true);
      }

      @Nested
      class Handle {

        @Test
        void shouldReturnSavedMessageWhenHandle() {
          final var message = administrativeMessage.status(MessageStatus.SENT).build();
          final var response = handleMessageDomainService
              .handle(message, true, certificate, ACTION_EVALUATION);

          assertEquals(savedMessage, response);
        }

        @Test
        void shouldSetMessageStatusHandledWhenHandle() {
          final var message = administrativeMessage.status(MessageStatus.SENT).build();
          handleMessageDomainService.handle(message, true, certificate, ACTION_EVALUATION);

          assertEquals(MessageStatus.HANDLED, message.status());
        }

        @Test
        void shouldSetTimeModifiedWhenHandle() {
          final var message = administrativeMessage.status(MessageStatus.SENT).build();
          handleMessageDomainService.handle(message, true, certificate, ACTION_EVALUATION);

          assertNotNull(message.modified());
        }

        @Test
        void shouldRemoveDraftAnswerWhenHandle() {
          final var message = administrativeMessage
              .status(MessageStatus.SENT)
              .answer(Answer.builder()
                  .status(MessageStatus.DRAFT)
                  .build())
              .build();
          handleMessageDomainService.handle(message, true, certificate, ACTION_EVALUATION);

          assertEquals(MessageStatus.DELETED_DRAFT, message.answer().status());
        }

        @Test
        void shouldNotRemoveSentAnswerWhenHandle() {
          final var message = administrativeMessage
              .status(MessageStatus.SENT)
              .answer(Answer.builder()
                  .status(MessageStatus.SENT)
                  .build())
              .build();
          handleMessageDomainService.handle(message, true, certificate, ACTION_EVALUATION);

          assertEquals(MessageStatus.SENT, message.answer().status());
        }
      }

      @Nested
      class Unhandle {

        @Test
        void shouldReturnSavedMessageWhenNotHandle() {
          final var message = administrativeMessage.status(MessageStatus.SENT).build();
          final var response = handleMessageDomainService
              .handle(message, false, certificate, ACTION_EVALUATION);

          assertEquals(savedMessage, response);
        }

        @Test
        void shouldSetMessageStatusSentWhenNotHandle() {
          final var message = administrativeMessage.status(MessageStatus.HANDLED).build();
          handleMessageDomainService.handle(message, false, certificate, ACTION_EVALUATION);

          assertEquals(MessageStatus.SENT, message.status());
        }

        @Test
        void shouldSetTimeModifiedWhenNotHandle() {
          final var message = administrativeMessage.status(MessageStatus.HANDLED).build();
          handleMessageDomainService.handle(message, false, certificate, ACTION_EVALUATION);

          assertNotNull(message.modified());
        }
      }
    }
  }
}