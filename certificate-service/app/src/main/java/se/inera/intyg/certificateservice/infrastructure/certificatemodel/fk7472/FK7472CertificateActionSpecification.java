package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import java.util.List;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

public class FK7472CertificateActionSpecification {

  private FK7472CertificateActionSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static List<CertificateActionSpecification> create() {
    final var allowedRolesForProtectedPersons = List.of(
        Role.DOCTOR,
        Role.PRIVATE_DOCTOR,
        Role.NURSE,
        Role.MIDWIFE
    );

    final var allowedRoles = List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
        Role.CARE_ADMIN);

    final var rolesAllowedToSignOrRevoke = List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE,
        Role.MIDWIFE);

    return List.of(
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.CREATE)
            .allowedRoles(
                allowedRoles
            )
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.READ)
            .allowedRoles(
                allowedRoles
            )
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.UPDATE)
            .allowedRoles(
                allowedRoles
            )
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.DELETE)
            .allowedRoles(
                allowedRoles
            )
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SIGN)
            .allowedRoles(
                rolesAllowedToSignOrRevoke
            )
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SEND)
            .allowedRoles(
                allowedRoles
            )
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.PRINT)
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REVOKE)
            .allowedRoles(
                rolesAllowedToSignOrRevoke
            )
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REPLACE)
            .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
            )
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
            .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE)
            )
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.RENEW)
            .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
                Role.CARE_ADMIN))
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SEND_AFTER_SIGN)
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.MESSAGES)
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.MESSAGES_ADMINISTRATIVE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.RECEIVE_COMPLEMENT)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.RECEIVE_REMINDER)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.COMPLEMENT)
            .allowedRoles(allowedRoles)
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SEND_AFTER_COMPLEMENT)
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.CANNOT_COMPLEMENT)
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.FORWARD_MESSAGE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.HANDLE_COMPLEMENT)
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE)
            .allowedRoles(List.of(Role.CARE_ADMIN))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.READY_FOR_SIGN)
            .allowedRoles(List.of(Role.CARE_ADMIN))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.LIST_CERTIFICATE_TYPE)
            .allowedRoles(allowedRoles)
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.QUESTIONS_NOT_AVAILABLE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST)
            .allowedRolesForProtectedPersons(allowedRolesForProtectedPersons)
            .build()
    );
  }
}