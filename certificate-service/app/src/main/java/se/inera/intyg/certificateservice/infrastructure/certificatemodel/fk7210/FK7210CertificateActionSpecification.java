package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210;

import java.util.List;
import java.util.stream.Stream;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Role;

public class FK7210CertificateActionSpecification {

  private FK7210CertificateActionSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static List<CertificateActionSpecification> create() {
    final var nonSigningRoles = List.of(Role.CARE_ADMIN);
    final var signingRoles = List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE);

    final var allowedRoles = Stream.concat(
            signingRoles.stream(),
            nonSigningRoles.stream()
        )
        .toList();

    return List.of(
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.CREATE)
            .allowedRoles(allowedRoles)
            .allowedRolesForProtectedPersons(signingRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.READ)
            .allowedRoles(allowedRoles)
            .allowedRolesForProtectedPersons(signingRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.UPDATE)
            .allowedRoles(allowedRoles)
            .allowedRolesForProtectedPersons(signingRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.DELETE)
            .allowedRoles(allowedRoles)
            .allowedRolesForProtectedPersons(signingRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SIGN)
            .allowedRoles(signingRoles)
            .allowedRolesForProtectedPersons(signingRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.SEND)
            .allowedRoles(allowedRoles)
            .allowedRolesForProtectedPersons(signingRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.PRINT)
            .allowedRolesForProtectedPersons(signingRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REVOKE)
            .allowedRoles(signingRoles)
            .allowedRolesForProtectedPersons(signingRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REPLACE)
            .allowedRoles(signingRoles)
            .allowedRolesForProtectedPersons(signingRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
            .allowedRoles(signingRoles)
            .allowedRolesForProtectedPersons(signingRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE)
            .allowedRoles(nonSigningRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.READY_FOR_SIGN)
            .allowedRoles(nonSigningRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.LIST_CERTIFICATE_TYPE)
            .allowedRoles(allowedRoles)
            .allowedRolesForProtectedPersons(signingRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST)
            .allowedRolesForProtectedPersons(signingRoles)
            .build(),
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.INACTIVE_CERTIFICATE_MODEL)
            .build()
    );
  }
}