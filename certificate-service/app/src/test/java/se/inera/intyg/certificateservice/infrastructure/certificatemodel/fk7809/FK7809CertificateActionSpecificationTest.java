package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

class FK7809CertificateActionSpecificationTest {

  @Test
  void shallIncludeCertificateActionCreate() {
    final var expectedType = CertificateActionType.CREATE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType())
        ), "Expected type: %s".formatted(expectedType)
    );
  }

  @Test
  void shallIncludeCertificateActionRead() {
    final var expectedType = CertificateActionType.READ;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType()
            )
        ), "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionUpdate() {
    final var expectedType = CertificateActionType.UPDATE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDelete() {
    final var expectedType = CertificateActionType.DELETE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
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

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionSend() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
        .build();

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionPrint() {
    final var expectedType = CertificateActionType.PRINT;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
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

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionReplace() {
    final var expectedType = CertificateActionType.REPLACE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReplaceContinue() {
    final var expectedType = CertificateActionType.REPLACE_CONTINUE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRenew() {
    final var expectedType = CertificateActionType.RENEW;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessages() {
    final var expectedType = CertificateActionType.MESSAGES;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessagesAdministrative() {
    final var expected = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.MESSAGES_ADMINISTRATIVE)
        .enabled(true)
        .build();

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(expected::equals),
        "Expected type: %s".formatted(expected));
  }

  @Test
  void shallIncludeCertificateActionReceiveComplement() {
    final var expectedType = CertificateActionType.RECEIVE_COMPLEMENT;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveQuestion() {
    final var expectedType = CertificateActionType.RECEIVE_QUESTION;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveAnswer() {
    final var expectedType = CertificateActionType.RECEIVE_ANSWER;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionComplement() {
    final var expectedType = CertificateActionType.COMPLEMENT;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionCannotComplement() {
    final var expectedType = CertificateActionType.CANNOT_COMPLEMENT;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionForwardMessage() {
    final var expectedType = CertificateActionType.FORWARD_MESSAGE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionForwardCertificate() {
    final var expectedType = CertificateActionType.FORWARD_CERTIFICATE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionHandleComplement() {
    final var expectedType = CertificateActionType.HANDLE_COMPLEMENT;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveReminder() {
    final var expectedType = CertificateActionType.RECEIVE_REMINDER;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionCreateMessages() {
    final var expectedType = CertificateActionType.CREATE_MESSAGE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSaveAnswer() {
    final var expectedType = CertificateActionType.SAVE_ANSWER;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDeleteAnswer() {
    final var expectedType = CertificateActionType.DELETE_ANSWER;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSendAnswer() {
    final var expectedType = CertificateActionType.SEND_ANSWER;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionAnswerMessages() {
    final var expectedType = CertificateActionType.ANSWER_MESSAGE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSaveMessage() {
    final var expectedType = CertificateActionType.SAVE_MESSAGE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDeleteMessage() {
    final var expectedType = CertificateActionType.DELETE_MESSAGE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSendMessage() {
    final var expectedType = CertificateActionType.SEND_MESSAGE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionHandleMessage() {
    final var expectedType = CertificateActionType.HANDLE_MESSAGE;

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionResponsibleIssuer() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RESPONSIBLE_ISSUER)
        .allowedRoles(List.of(Role.NURSE, Role.MIDWIFE, Role.CARE_ADMIN))
        .build();

    final var actionSpecifications = FK7809CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }
}