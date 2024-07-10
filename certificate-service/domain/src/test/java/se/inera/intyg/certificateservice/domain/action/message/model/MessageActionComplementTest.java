package se.inera.intyg.certificateservice.domain.action.message.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageActionSpecification;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionLink;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;

@ExtendWith(MockitoExtension.class)
class MessageActionComplementTest {

  private MessageActionComplement messageAction;
  private final MessageActionSpecification actionSpecification = MessageActionSpecification.builder()
      .messageActionType(MessageActionType.COMPLEMENT)
      .build();

  @BeforeEach
  void setUp() {
    messageAction = (MessageActionComplement) MessageActionFactory.create(
        actionSpecification);
  }

  @Nested
  class TypeTest {

    @Test
    void shallReturnType() {
      assertEquals(MessageActionType.COMPLEMENT, messageAction.type());
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
    void shallReturnFalseIfTypeIsNotComplement() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .status(MessageStatus.DRAFT)
          .build();
      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.COMPLEMENT).when(certificateActions).getType();
      assertFalse(messageAction.evaluate(List.of(certificateActions), message));
    }

    @Test
    void shallReturnFalseIfStatusIsHandled() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.HANDLED)
          .build();
      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.COMPLEMENT).when(certificateActions).getType();
      assertFalse(messageAction.evaluate(List.of(certificateActions), message));
    }

    @Test
    void shallReturnTrueIfEvaluationPassed() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.DRAFT)
          .build();
      final var certificateActions = mock(CertificateAction.class);
      doReturn(CertificateActionType.COMPLEMENT).when(certificateActions).getType();
      assertTrue(messageAction.evaluate(List.of(certificateActions), message));
    }
  }

  @Nested
  class ActionLinkTests {

    @Test
    void shallReturnMessageActionLink() {
      final var expectedLink = MessageActionLink.builder()
          .type(MessageActionType.COMPLEMENT)
          .name("Komplettera")
          .description("Öppnar ett nytt intygsutkast.")
          .enabled(true)
          .build();

      assertEquals(expectedLink, messageAction.actionLink());
    }
  }

}