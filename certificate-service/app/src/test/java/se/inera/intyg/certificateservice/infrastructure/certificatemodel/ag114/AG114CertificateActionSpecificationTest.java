package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

class AG114CertificateActionSpecificationTest {

  @Test
  void shouldIncludeCertificateActionCreate() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CREATE)
        .allowedRoles(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE,
                Role.CARE_ADMIN))
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shouldIncludeCertificateActionRead() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.READ)
        .allowedRoles(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE,
                Role.CARE_ADMIN))
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shouldIncludeCertificateActionUpdate() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.UPDATE)
        .allowedRoles(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE,
                Role.CARE_ADMIN))
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shouldIncludeCertificateActionDelete() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE)
        .allowedRoles(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE,
                Role.CARE_ADMIN))
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shouldIncludeCertificateActionSign() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SIGN)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST))
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shouldIncludeCertificateActionPrint() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.PRINT)
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shouldIncludeCertificateActionRevoke() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REVOKE)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST))
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shouldIncludeCertificateActionReplace() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REPLACE)
        .allowedRoles(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST))
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shouldIncludeCertificateActionReplaceContinue() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
        .allowedRoles(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST))
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shouldNotIncludeCertificateActionRenew() {
    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertThrows(NoSuchElementException.class, () ->
        actualSpecification(actionSpecifications, CertificateActionType.RENEW)
    );
  }

  @Test
  void shouldIncludeCertificateActionForwardCertificate() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE)
        .allowedRoles(List.of(Role.NURSE, Role.MIDWIFE, Role.CARE_ADMIN))
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shouldIncludeCertificateActionReadyForSign() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.READY_FOR_SIGN)
        .allowedRoles(List.of(Role.NURSE, Role.MIDWIFE, Role.CARE_ADMIN))
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shouldIncludeCertificateActionListCertificateType() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.LIST_CERTIFICATE_TYPE)
        .allowedRoles(
            List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE,
                Role.CARE_ADMIN))
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  @Test
  void shouldIncludeCertificateActionForwardCertificateFromList() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST)
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertEquals(expectedSpecification,
        actualSpecification(actionSpecifications, expectedSpecification.certificateActionType())
    );
  }

  private static CertificateActionSpecification actualSpecification(
      List<CertificateActionSpecification> actionSpecifications,
      CertificateActionType certificateActionType) {
    return actionSpecifications.stream()
        .filter(spec -> spec.certificateActionType() == certificateActionType)
        .findAny().orElseThrow();
  }

  @Test
  void shallIncludeCertificateActionInactiveCertificateModel() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.INACTIVE_CERTIFICATE_MODEL)
        .build();

    final var actionSpecifications = AG114CertificateActionSpecification.create();

    assertTrue(actionSpecifications.stream().anyMatch(expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }
}
