package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427;

import java.util.List;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

public class FK7427CertificateActionSpecification {

  private FK7427CertificateActionSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static List<CertificateActionSpecification> create() {
    return List.of(
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.CREATE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.READ)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.UPDATE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.DELETE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SIGN)
            .allowedRoles(
                List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR)
            )
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SEND)
            .allowedRoles(
                List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.CARE_ADMIN)
            )
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.PRINT)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REVOKE)
            .allowedRoles(
                List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR)
            )
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REPLACE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.RENEW)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SEND_AFTER_SIGN)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.MESSAGES)
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
            .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE)
            .allowedRoles(List.of(Role.CARE_ADMIN))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.READY_FOR_SIGN)
            .allowedRoles(List.of(Role.CARE_ADMIN))
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.LIST_CERTIFICATE_TYPE)
            .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.CARE_ADMIN))
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