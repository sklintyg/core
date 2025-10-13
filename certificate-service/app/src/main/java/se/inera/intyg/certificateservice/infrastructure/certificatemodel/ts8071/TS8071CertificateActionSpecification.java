package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071;

import java.util.List;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

public class TS8071CertificateActionSpecification {

  private TS8071CertificateActionSpecification() {
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
            .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.PRINT)
            .build()
    );
  }
}
