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
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@ExtendWith(MockitoExtension.class)
class MessageActionAnswerTest {

  private MessageActionAnswer messageAction;

  private final MessageActionSpecification actionSpecification = MessageActionSpecification.builder()
      .messageActionType(MessageActionType.ANSWER)
      .build();

  @BeforeEach
  void setUp() {
    messageAction = (MessageActionAnswer) MessageActionFactory.create(actionSpecification);
  }

  @Nested
  class TypeTest {

    @Test
    void shallReturnType() {
      assertEquals(MessageActionType.ANSWER, messageAction.type());
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
    void shallReturnFalseIfAuthoredStaffIsNotNull() {
      final var message = Message.builder()
          .authoredStaff(Staff.create(AJLA_DOKTOR))
          .build();
      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.ANSWER_MESSAGE).when(certificateActions).getType();
      assertFalse(messageAction.evaluate(List.of(certificateActions), message));
    }

    @Test
    void shallReturnFalseIfAnswerIsNotNull() {
      final var message = Message.builder()
          .answer(Answer.builder().build())
          .build();
      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.ANSWER_MESSAGE).when(certificateActions).getType();
      assertFalse(messageAction.evaluate(List.of(certificateActions), message));
    }

    @Test
    void shallReturnFalseIfTypeIsComplement() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .build();
      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.ANSWER_MESSAGE).when(certificateActions).getType();
      assertFalse(messageAction.evaluate(List.of(certificateActions), message));
    }

    @Test
    void shallReturnFalseIfStatusIsHandled() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .status(MessageStatus.HANDLED)
          .build();
      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.ANSWER_MESSAGE).when(certificateActions).getType();
      assertFalse(messageAction.evaluate(List.of(certificateActions), message));
    }

    @Test
    void shallReturnTrueIfEvaluationPassed() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .status(MessageStatus.DRAFT)
          .build();
      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.ANSWER_MESSAGE).when(certificateActions).getType();
      assertTrue(messageAction.evaluate(List.of(certificateActions), message));
    }
  }

  @Nested
  class ActionLinkTests {

    @Test
    void shallReturnMessageActionLink() {
      final var expectedLink = MessageActionLink.builder()
          .type(MessageActionType.ANSWER)
          .name("Skapa")
          .description("Svara på fråga")
          .enabled(true)
          .build();

      assertEquals(expectedLink, messageAction.actionLink());
    }
  }
}
