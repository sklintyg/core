package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

class FK3226CertificateActionSpecificationTest {

  @Test
  void shallIncludeCertificateActionCreate() {
    final var expectedType = CertificateActionType.CREATE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType())
        ), "Expected type: %s".formatted(expectedType)
    );
  }

  @Test
  void shallIncludeCertificateActionRead() {
    final var expectedType = CertificateActionType.READ;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType()
            )
        ), "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionUpdate() {
    final var expectedType = CertificateActionType.UPDATE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDelete() {
    final var expectedType = CertificateActionType.DELETE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSign() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SIGN)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
        .build();

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionSend() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND)
        .allowedRoles(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.CARE_ADMIN, Role.MIDWIFE, Role.NURSE))
        .build();

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionSendAfterComplement() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_AFTER_COMPLEMENT)
        .build();

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionPrint() {
    final var expectedType = CertificateActionType.PRINT;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRevoke() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REVOKE)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
        .build();

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionReplace() {
    final var expectedType = CertificateActionType.REPLACE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReplaceContinue() {
    final var expectedType = CertificateActionType.REPLACE_CONTINUE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRenew() {
    final var expectedType = CertificateActionType.RENEW;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessages() {
    final var expectedType = CertificateActionType.MESSAGES;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessagesAdministrative() {
    final var expectedType = CertificateActionType.MESSAGES_ADMINISTRATIVE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessagesAdministrativeWithEnabledTrue() {
    final var expectedType = CertificateActionType.MESSAGES_ADMINISTRATIVE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream()
            .filter(
                actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
            )
            .findFirst()
            .orElseThrow()
            .enabled(),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveComplement() {
    final var expectedType = CertificateActionType.RECEIVE_COMPLEMENT;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveQuestion() {
    final var expectedType = CertificateActionType.RECEIVE_QUESTION;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveAnswer() {
    final var expectedType = CertificateActionType.RECEIVE_ANSWER;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionAnswerMessages() {
    final var expectedType = CertificateActionType.ANSWER_MESSAGE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSaveMessage() {
    final var expectedType = CertificateActionType.SAVE_MESSAGE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDeleteMessage() {
    final var expectedType = CertificateActionType.DELETE_MESSAGE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSendMessage() {
    final var expectedType = CertificateActionType.SEND_MESSAGE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionHandleMessage() {
    final var expectedType = CertificateActionType.HANDLE_MESSAGE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSaveAnswer() {
    final var expectedType = CertificateActionType.SAVE_ANSWER;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDeleteAnswer() {
    final var expectedType = CertificateActionType.DELETE_ANSWER;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSendAnswer() {
    final var expectedType = CertificateActionType.SEND_ANSWER;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionComplement() {
    final var expectedType = CertificateActionType.COMPLEMENT;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionCannotComplement() {
    final var expectedType = CertificateActionType.CANNOT_COMPLEMENT;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionForwardMessage() {
    final var expectedType = CertificateActionType.FORWARD_MESSAGE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionHandleComplement() {
    final var expectedType = CertificateActionType.HANDLE_COMPLEMENT;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionCreateMessages() {
    final var expectedType = CertificateActionType.CREATE_MESSAGE;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRecieveReminder() {
    final var expectedType = CertificateActionType.RECEIVE_REMINDER;

    final var certificateModel = FK3226CertificateActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionForwardCertificate() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE)
        .allowedRoles(List.of(Role.CARE_ADMIN, Role.MIDWIFE, Role.NURSE))
        .build();

    final var actionSpecifications = FK3226CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionReadyForSign() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.READY_FOR_SIGN)
        .allowedRoles(List.of(Role.CARE_ADMIN, Role.MIDWIFE, Role.NURSE))
        .build();

    final var actionSpecifications = FK3226CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionAccessForRoles() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.LIST_CERTIFICATE_TYPE)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.CARE_ADMIN, Role.MIDWIFE,
            Role.NURSE))
        .build();

    final var actionSpecifications = FK3226CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionForwardCertificateFromList() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST)
        .build();

    final var actionSpecifications = FK3226CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionInactiveCertificateModel() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.INACTIVE_CERTIFICATE_MODEL)
        .build();

    final var actionSpecifications = FK3226CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }
}