package se.inera.intyg.certificateservice.domain.action.message.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageActionSpecification;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionLink;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@ExtendWith(MockitoExtension.class)
class MessageActionHandleMessageTest {


  private MessageActionHandleMessage messageAction;
  private final MessageActionSpecification actionSpecification = MessageActionSpecification.builder()
      .messageActionType(MessageActionType.HANDLE_MESSAGE)
      .build();

  @BeforeEach
  void setUp() {
    messageAction = (MessageActionHandleMessage) MessageActionFactory.create(
        actionSpecification);
  }

  @Nested
  class TypeTest {

    @Test
    void shallReturnType() {
      assertEquals(MessageActionType.HANDLE_MESSAGE, messageAction.type());
    }
  }

  @Nested
  class EvaluationTests {

    @Test
    void shallReturnFalseIfActionMissing() {
      final var message = Message.builder().build();
      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.UPDATE).when(certificateActions).getType();
      assertFalse(messageAction.evaluate(List.of(certificateActions), message));
    }

    @Test
    void shallReturnFalseIfNotAdministrativeQuestion() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .build();
      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.HANDLE_MESSAGE).when(certificateActions).getType();
      assertFalse(messageAction.evaluate(List.of(certificateActions), message));
    }

    @Test
    void shallReturnFalseIfAdministrativeQuestionFromRecipientThatIsAnswered() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .answer(Answer.builder().build())
          .build();

      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.HANDLE_MESSAGE).when(certificateActions).getType();
      assertFalse(messageAction.evaluate(List.of(certificateActions), message));
    }

    @Test
    void shallReturnTrueIfAdministrativeQuestionFromCareThatIsAnswered() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .authoredStaff(Staff.create(AJLA_DOKTOR))
          .answer(Answer.builder().build())
          .build();

      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.HANDLE_MESSAGE).when(certificateActions).getType();
      assertTrue(messageAction.evaluate(List.of(certificateActions), message));
    }

    @Test
    void shallReturnTrueIfAdministrativeQuestionFromCareThatIsNotAnswered() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .authoredStaff(Staff.create(AJLA_DOKTOR))
          .build();

      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.HANDLE_MESSAGE).when(certificateActions).getType();
      assertTrue(messageAction.evaluate(List.of(certificateActions), message));
    }

    @Test
    void shallReturnTrueIfAdministrativeQuestionFromRecipientThatIsNotAnswered() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .build();

      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.HANDLE_MESSAGE).when(certificateActions).getType();
      assertTrue(messageAction.evaluate(List.of(certificateActions), message));
    }
  }

  @Nested
  class ActionLinkTests {

    @Test
    void shallReturnMessageActionLink() {
      final var expectedLink = MessageActionLink.builder()
          .type(MessageActionType.HANDLE_MESSAGE)
          .name("Hantera")
          .description("Hantera fråga")
          .enabled(true)
          .build();

      assertEquals(expectedLink, messageAction.actionLink());
    }
  }
}
