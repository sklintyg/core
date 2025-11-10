package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

class FK7427CertificateActionSpecificationTest {

  @Test
  void shallIncludeCertificateActionCreate() {
    final var expectedType = CertificateActionType.CREATE;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType())
        ), "Expected type: %s".formatted(expectedType)
    );
  }

  @Test
  void shallIncludeCertificateActionRead() {
    final var expectedType = CertificateActionType.READ;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType()
            )
        ), "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionUpdate() {
    final var expectedType = CertificateActionType.UPDATE;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDelete() {
    final var expectedType = CertificateActionType.DELETE;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

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

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionSend() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND)
        .allowedRoles(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.CARE_ADMIN, Role.MIDWIFE,
                Role.NURSE))
        .build();

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionSendAfterComplement() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_AFTER_COMPLEMENT)
        .build();

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionPrint() {
    final var expectedType = CertificateActionType.PRINT;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

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

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionReplace() {
    final var expectedType = CertificateActionType.REPLACE;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReplaceContinue() {
    final var expectedType = CertificateActionType.REPLACE_CONTINUE;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRenew() {
    final var expectedType = CertificateActionType.RENEW;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessages() {
    final var expectedType = CertificateActionType.MESSAGES;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveComplement() {
    final var expectedType = CertificateActionType.RECEIVE_COMPLEMENT;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionComplement() {
    final var expectedType = CertificateActionType.COMPLEMENT;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionCannotComplement() {
    final var expectedType = CertificateActionType.CANNOT_COMPLEMENT;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
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

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionHandleComplement() {
    final var expectedType = CertificateActionType.HANDLE_COMPLEMENT;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReadyForSign() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.READY_FOR_SIGN)
        .allowedRoles(List.of(Role.CARE_ADMIN, Role.MIDWIFE, Role.NURSE))
        .build();

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

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

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionForwardCertificateFromList() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST)
        .build();

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shouldIncludeCertificateActionForwardMessage() {
    final var expectedType = CertificateActionType.FORWARD_MESSAGE;

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionInactiveCertificateModel() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.INACTIVE_CERTIFICATE_MODEL)
        .build();

    final var actionSpecifications = FK7427CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }
}