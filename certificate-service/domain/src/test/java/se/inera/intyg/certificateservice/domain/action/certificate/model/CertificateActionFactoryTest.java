package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionAnswerMessage;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionCannotComplement;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionComplement;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionCreate;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionCreateMessage;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionDelete;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionDeleteAnswer;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionDeleteMessage;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionForwardCertificate;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionForwardMessage;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionHandleComplement;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionHandleMessage;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionMessages;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionMessagesAdministrative;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionPrint;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionRead;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionReceiveAnswer;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionReceiveComplement;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionReceiveQuestion;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionReceiveReminder;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionRenew;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionReplace;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionReplaceContinue;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionRevoke;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionSaveAnswer;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionSaveMessage;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionSend;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionSendAfterSign;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionSendAnswer;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionSendMessage;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionSign;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionUpdate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

class CertificateActionFactoryTest {

  @Test
  void shallReturnCertificateActionCreateIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CREATE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionCreate.class);
  }

  @Test
  void shallReturnCertificateActionReadIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.READ)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionRead.class);
  }

  @Test
  void shallReturnCertificateActionUpdateIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.UPDATE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionUpdate.class);
  }

  @Test
  void shallReturnCertificateActionDeleteIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionDelete.class);
  }

  @Test
  void shallReturnCertificateActionSignIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SIGN)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSign.class);
  }

  @Test
  void shallReturnCertificateActionPrintIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.PRINT)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionPrint.class);
  }

  @Test
  void shallReturnCertificateActionSendIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSend.class);
  }

  @Test
  void shallReturnCertificateActionRevokeIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REVOKE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionRevoke.class);
  }

  @Test
  void shallReturnCertificateActionSendPostSignIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_AFTER_SIGN)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSendAfterSign.class);
  }

  @Test
  void shallReturnCertificateActionComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.COMPLEMENT)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionComplement.class);
  }

  @Test
  void shallReturnCertificateActionReplaceIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REPLACE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionReplace.class);
  }

  @Test
  void shallReturnCertificateActionReplaceContinueIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionReplaceContinue.class);
  }

  @Test
  void shallReturnCertificateActionRenewIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RENEW)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionRenew.class);
  }

  @Test
  void shallReturnCertificateActionRecieveComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RECEIVE_COMPLEMENT)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionReceiveComplement.class);
  }

  @Test
  void shallReturnCertificateActionRecieveQuestionIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RECEIVE_QUESTION)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionReceiveQuestion.class);
  }

  @Test
  void shallReturnCertificateActionCannotComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CANNOT_COMPLEMENT)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionCannotComplement.class);
  }

  @Test
  void shallReturnCertificateActionMessagesIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.MESSAGES)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionMessages.class);
  }

  @Test
  void shallReturnCertificateActionMessagesAdministrativeIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.MESSAGES_ADMINISTRATIVE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionMessagesAdministrative.class);
  }

  @Test
  void shallReturnCertificateActionForwardMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_MESSAGE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionForwardMessage.class);
  }

  @Test
  void shallReturnCertificateActionForwardIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionForwardCertificate.class);
  }

  @Test
  void shallReturnCertificateActionHandleComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.HANDLE_COMPLEMENT)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionHandleComplement.class);
  }

  @Test
  void shallReturnCertificateActionRecieveAnswerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RECEIVE_ANSWER)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionReceiveAnswer.class);
  }

  @Test
  void shallReturnCertificateActionRecieveReminderIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RECEIVE_REMINDER)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionReceiveReminder.class);
  }

  @Test
  void shallReturnCertificateActionCreateMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CREATE_MESSAGE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionCreateMessage.class);
  }

  @Test
  void shallReturnCertificateActionAnswerMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.ANSWER_MESSAGE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionAnswerMessage.class);
  }

  @Test
  void shallReturnCertificateActionSaveMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SAVE_MESSAGE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSaveMessage.class);
  }

  @Test
  void shallReturnCertificateActionDeleteMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE_MESSAGE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionDeleteMessage.class);
  }

  @Test
  void shallReturnCertificateActionSendMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_MESSAGE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSendMessage.class);
  }

  @Test
  void shallReturnCertificateActionHandleMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.HANDLE_MESSAGE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionHandleMessage.class);
  }

  @Test
  void shallReturnCertificateActionSaveAnswerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SAVE_ANSWER)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSaveAnswer.class);
  }

  @Test
  void shallReturnCertificateActionDeleteAnswerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE_ANSWER)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionDeleteAnswer.class);
  }

  @Test
  void shallReturnCertificateActionSendAnswerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_ANSWER)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSendAnswer.class);
  }
}