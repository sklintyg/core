package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;

@ExtendWith(MockitoExtension.class)
class certificateActionFactoryTest {

  @Mock
  CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @Test
  void shallReturnCertificateActionCreateIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CREATE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionCreate.class);
  }

  @Test
  void shallReturnCertificateActionReadIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.READ)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionRead.class);
  }

  @Test
  void shallReturnCertificateActionUpdateIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.UPDATE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionUpdate.class);
  }

  @Test
  void shallReturnCertificateActionDeleteIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionDelete.class);
  }

  @Test
  void shallReturnCertificateActionSignIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SIGN)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSign.class);
  }

  @Test
  void shallReturnCertificateActionPrintIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.PRINT)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionPrint.class);
  }

  @Test
  void shallReturnCertificateActionSendIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSend.class);
  }

  @Test
  void shallReturnCertificateActionRevokeIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REVOKE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionRevoke.class);
  }

  @Test
  void shallReturnCertificateActionSendPostSignIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_AFTER_SIGN)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSendAfterSign.class);
  }

  @Test
  void shallReturnCertificateActionComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.COMPLEMENT)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionComplement.class);
  }

  @Test
  void shallReturnCertificateActionReplaceIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REPLACE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionReplace.class);
  }

  @Test
  void shallReturnCertificateActionReplaceContinueIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionReplaceContinue.class);
  }

  @Test
  void shallReturnCertificateActionRenewIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RENEW)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionRenew.class);
  }

  @Test
  void shallReturnCertificateActionRecieveComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RECEIVE_COMPLEMENT)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionReceiveComplement.class);
  }

  @Test
  void shallReturnCertificateActionRecieveQuestionIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RECEIVE_QUESTION)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionReceiveQuestion.class);
  }

  @Test
  void shallReturnCertificateActionCannotComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CANNOT_COMPLEMENT)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionCannotComplement.class);
  }

  @Test
  void shallReturnCertificateActionMessagesIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.MESSAGES)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionMessages.class);
  }

  @Test
  void shallReturnCertificateActionMessagesAdministrativeIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.MESSAGES_ADMINISTRATIVE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionMessagesAdministrative.class);
  }

  @Test
  void shallReturnCertificateActionForwardMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionForwardMessage.class);
  }

  @Test
  void shallReturnCertificateActionForwardIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionForwardCertificate.class);
  }

  @Test
  void shallReturnCertificateActionHandleComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.HANDLE_COMPLEMENT)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionHandleComplement.class);
  }

  @Test
  void shallReturnCertificateActionRecieveAnswerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RECEIVE_ANSWER)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionReceiveAnswer.class);
  }

  @Test
  void shallReturnCertificateActionRecieveReminderIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RECEIVE_REMINDER)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionReceiveReminder.class);
  }

  @Test
  void shallReturnCertificateActionCreateMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CREATE_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionCreateMessage.class);
  }

  @Test
  void shallReturnCertificateActionAnswerMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.ANSWER_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionAnswerMessage.class);
  }

  @Test
  void shallReturnCertificateActionSaveMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SAVE_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSaveMessage.class);
  }

  @Test
  void shallReturnCertificateActionDeleteMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionDeleteMessage.class);
  }

  @Test
  void shallReturnCertificateActionSendMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSendMessage.class);
  }

  @Test
  void shallReturnCertificateActionHandleMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.HANDLE_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionHandleMessage.class);
  }

  @Test
  void shallReturnCertificateActionSaveAnswerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SAVE_ANSWER)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSaveAnswer.class);
  }

  @Test
  void shallReturnCertificateActionDeleteAnswerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE_ANSWER)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionDeleteAnswer.class);
  }

  @Test
  void shallReturnCertificateActionSendAnswerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_ANSWER)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSendAnswer.class);
  }

  @Test
  void shallReturnCertificateActionResponsibleIssuerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RESPONSIBLE_ISSUER)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionResponsibleIssuer.class);
  }


  @Test
  void shallReturnCertificateActionAccessForRolesIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.LIST_CERTIFICATE_TYPE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionListCertificateType.class);
  }
}
