package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

class FK7210CertificateActionSpecificationTest {

  private static List<Role> ROLES_ALLOWED =
      List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE, Role.CARE_ADMIN);

  @Test
  void shallIncludeCertificateActionCreate() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CREATE)
        .allowedRoles(ROLES_ALLOWED)
        .allowedRolesForProtectedPersons(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionRead() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.READ)
        .allowedRoles(ROLES_ALLOWED)
        .allowedRolesForProtectedPersons(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionUpdate() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.UPDATE)
        .allowedRoles(ROLES_ALLOWED)
        .allowedRolesForProtectedPersons(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionDelete() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE)
        .allowedRoles(ROLES_ALLOWED)
        .allowedRolesForProtectedPersons(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionSign() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SIGN)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
        .allowedRolesForProtectedPersons(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionSend() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND)
        .allowedRoles(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE, Role.CARE_ADMIN)
        )
        .allowedRolesForProtectedPersons(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionPrint() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.PRINT)
        .allowedRolesForProtectedPersons(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionRevoke() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REVOKE)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
        .allowedRolesForProtectedPersons(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionReplace() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REPLACE)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .allowedRolesForProtectedPersons(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionReplaceContinue() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .allowedRolesForProtectedPersons(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

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

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

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

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionAccessForRoles() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.LIST_CERTIFICATE_TYPE)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
            Role.CARE_ADMIN)
        )
        .allowedRolesForProtectedPersons(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }


  @Test
  void shallIncludeCertificateActionForwardCertificateFromList() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST)
        .allowedRolesForProtectedPersons(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
        )
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shallIncludeCertificateActionInactiveCertificateModel() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.INACTIVE_CERTIFICATE_MODEL)
        .build();

    final var actionSpecifications = FK7210CertificateActionSpecification.create();

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