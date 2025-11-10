package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

@ExtendWith(MockitoExtension.class)
class CertificateActionFactoryTest {

  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @Test
  void shallReturnCertificateActionCreateIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CREATE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionCreate.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionReadIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.READ)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionRead.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionUpdateIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.UPDATE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionUpdate.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionDeleteIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionDelete.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionSignIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SIGN)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionSign.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionPrintIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.PRINT)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionPrint.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionSendIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionSend.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionRevokeIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REVOKE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionRevoke.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionSendPostSignIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_AFTER_SIGN)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionSendAfterSign.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionSendAfterComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_AFTER_COMPLEMENT)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionSendAfterComplement.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.COMPLEMENT)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionComplement.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionReplaceIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REPLACE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionReplace.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionReplaceContinueIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionReplaceContinue.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionRenewIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RENEW)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionRenew.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionRecieveComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RECEIVE_COMPLEMENT)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionReceiveComplement.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionRecieveQuestionIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RECEIVE_QUESTION)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionReceiveQuestion.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionCannotComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CANNOT_COMPLEMENT)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionCannotComplement.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionMessagesIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.MESSAGES)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionMessages.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionMessagesAdministrativeIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.MESSAGES_ADMINISTRATIVE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionMessagesAdministrative.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionForwardMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionForwardMessage.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionForwardIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionForwardCertificate.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionHandleComplementIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.HANDLE_COMPLEMENT)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionHandleComplement.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionRecieveAnswerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RECEIVE_ANSWER)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionReceiveAnswer.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionRecieveReminderIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RECEIVE_REMINDER)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionReceiveReminder.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionCreateMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CREATE_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionCreateMessage.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionAnswerMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.ANSWER_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionAnswerMessage.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionSaveMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SAVE_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionSaveMessage.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionDeleteMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionDeleteMessage.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionSendMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionSendMessage.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionHandleMessageIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.HANDLE_MESSAGE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionHandleMessage.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionSaveAnswerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SAVE_ANSWER)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionSaveAnswer.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionDeleteAnswerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE_ANSWER)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionDeleteAnswer.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionSendAnswerIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_ANSWER)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionSendAnswer.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionAccessForRolesIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.LIST_CERTIFICATE_TYPE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionListCertificateType.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionForwardCertificateFromListIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionForwardCertificateFromList.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionFMBFromListIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FMB)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionFMB.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionSrsDraftFromListIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SRS_DRAFT)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionSrsDraft.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionSrsSignedFromListIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SRS_SIGNED)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionSrsSigned.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionTemplateFromListIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionCreateDraftFromCertificate.class, certificateAction.getClass());
  }


  @Test
  void shallReturnCertificateActionUpdateDraftFromCertificateIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.UPDATE_DRAFT_FROM_CERTIFICATE)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionUpdateDraftFromCertificate.class, certificateAction.getClass());
  }

  @Test
  void shallReturnCertificateActionInactiveCertificateModelIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.INACTIVE_CERTIFICATE_MODEL)
        .build();

    final var certificateAction = certificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(CertificateActionInactiveCertificateModel.class, certificateAction.getClass());
  }
}