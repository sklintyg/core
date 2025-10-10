package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import java.util.List;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

public class AG7804CertificateActionSpecification {

  private AG7804CertificateActionSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static List<CertificateActionSpecification> create() {
    return List.of(
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.CREATE)
            .allowedRoles(
                List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE,
                    Role.CARE_ADMIN))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.READ)
            .allowedRoles(
                List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE,
                    Role.CARE_ADMIN))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.UPDATE)
            .allowedRoles(
                List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE,
                    Role.CARE_ADMIN))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.DELETE)
            .allowedRoles(
                List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE,
                    Role.CARE_ADMIN))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SIGN)
            .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.PRINT)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REVOKE)
            .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REPLACE)
            .allowedRoles(
                List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
            .allowedRoles(
                List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.RENEW)
            .allowedRoles(
                List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE,
                    Role.CARE_ADMIN))
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
            .allowedRoles(
                List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE, Role.MIDWIFE,
                    Role.CARE_ADMIN))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST)
            .build()
    );
  }
}