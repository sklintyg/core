package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426;

import java.util.List;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

public class FK7426CertificateActionSpecification {

  private FK7426CertificateActionSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static List<CertificateActionSpecification> create() {
    final var allowedRoles = List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
        Role.CARE_ADMIN);

    final var rolesAllowedToSignOrRevoke = List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR);

    return List.of(
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.CREATE)
            .allowedRoles(allowedRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.READ)
            .allowedRoles(allowedRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.UPDATE)
            .allowedRoles(allowedRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.DELETE)
            .allowedRoles(allowedRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SIGN)
            .allowedRoles(rolesAllowedToSignOrRevoke)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SEND)
            .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.CARE_ADMIN, Role.NURSE,
                Role.MIDWIFE))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.PRINT)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REVOKE)
            .allowedRoles(rolesAllowedToSignOrRevoke)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REPLACE)
            .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
            .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.RENEW)
            .allowedRoles(allowedRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SEND_AFTER_SIGN)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.MESSAGES)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.MESSAGES_ADMINISTRATIVE)
            .enabled(true)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.RECEIVE_COMPLEMENT)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.RECEIVE_QUESTION)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.RECEIVE_ANSWER)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.RECEIVE_REMINDER)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.COMPLEMENT)
            .allowedRoles(allowedRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SEND_AFTER_COMPLEMENT)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.CANNOT_COMPLEMENT)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.FORWARD_MESSAGE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.HANDLE_COMPLEMENT)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.CREATE_MESSAGE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.ANSWER_MESSAGE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SAVE_MESSAGE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.DELETE_MESSAGE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SEND_MESSAGE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SAVE_ANSWER)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.DELETE_ANSWER)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SEND_ANSWER)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.HANDLE_MESSAGE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE)
            .allowedRoles(List.of(Role.CARE_ADMIN, Role.MIDWIFE, Role.NURSE))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.READY_FOR_SIGN)
            .allowedRoles(List.of(Role.CARE_ADMIN, Role.MIDWIFE, Role.NURSE))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.LIST_CERTIFICATE_TYPE)
            .allowedRoles(allowedRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.QUESTIONS_NOT_AVAILABLE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST)
            .build()
    );
  }
}