package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

class FK7472CertificateActionSpecificationTest {

  public static final List<Role> ROLES_FOR_PROTECTED_PERSON = List.of(
      Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE
  );

  public static final List<Role> ROLES_ALLOWED = List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR,
      Role.NURSE, Role.MIDWIFE, Role.CARE_ADMIN);

  @Test
  void shallIncludeCertificateActionCreate() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CREATE)
        .allowedRoles(ROLES_ALLOWED)
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionRead() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.READ)
        .allowedRoles(ROLES_ALLOWED)
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionUpdate() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.UPDATE)
        .allowedRoles(ROLES_ALLOWED)
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionDelete() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE)
        .allowedRoles(ROLES_ALLOWED)
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionSign() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SIGN)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionSend() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND)
        .allowedRoles(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE, Role.CARE_ADMIN))
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionSendAfterComplement() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND_AFTER_COMPLEMENT)
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionPrint() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.PRINT)
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionRevoke() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REVOKE)
        .allowedRoles(ROLES_FOR_PROTECTED_PERSON)
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionReplace() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REPLACE)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionReplaceContinue() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionRenew() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RENEW)
        .allowedRoles(ROLES_ALLOWED)
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionMessages() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.MESSAGES)
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionMessagesAdministrative() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.MESSAGES_ADMINISTRATIVE)
        .build();

    final var actions = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actions, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionReceiveComplement() {
    final var expectedType = CertificateActionType.RECEIVE_COMPLEMENT;

    final var actions = FK7472CertificateActionSpecification.create();

    assertTrue(actions.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveReminder() {
    final var expectedType = CertificateActionType.RECEIVE_REMINDER;

    final var actions = FK7472CertificateActionSpecification.create();

    assertTrue(actions.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionComplement() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.COMPLEMENT)
        .allowedRoles(ROLES_ALLOWED)
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actionSpecifications = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionCannotComplement() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CANNOT_COMPLEMENT)
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actionSpecifications = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionForwardMessage() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_MESSAGE)
        .build();

    final var actionSpecifications = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionForwardCertificate() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE)
        .allowedRoles(List.of(Role.CARE_ADMIN))
        .build();

    final var actionSpecifications = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionHandleComplement() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.HANDLE_COMPLEMENT)
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actionSpecifications = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionReadyForSign() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.READY_FOR_SIGN)
        .allowedRoles(List.of(Role.CARE_ADMIN))
        .build();

    final var actionSpecifications = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionAccessForRoles() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.LIST_CERTIFICATE_TYPE)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
            Role.CARE_ADMIN))
        .allowedRolesForProtectedPersons(
            ROLES_FOR_PROTECTED_PERSON
        )
        .build();

    final var actionSpecifications = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionForwardCertificateFromList() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST)
        .allowedRolesForProtectedPersons(ROLES_FOR_PROTECTED_PERSON)
        .build();

    final var actionSpecifications = FK7472CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionInactiveCertificateModel() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.INACTIVE_CERTIFICATE_MODEL)
        .build();

    final var actionSpecifications = FK7472CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  private static CertificateActionSpecification actualSpecification(
      List<CertificateActionSpecification> actionSpecifications,
      CertificateActionType certificateActionType) {
    return actionSpecifications.stream()
        .filter(spec -> spec.certificateActionType() == certificateActionType)
        .findAny().orElseThrow();
  }
}