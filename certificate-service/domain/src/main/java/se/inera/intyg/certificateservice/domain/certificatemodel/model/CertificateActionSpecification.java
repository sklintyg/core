package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.common.model.Role;

@Value
@Builder
public class CertificateActionSpecification {

  CertificateActionType certificateActionType;
  Boolean enabled;
  @Default
  List<Role> allowedRoles = Collections.emptyList();
  @Default
  List<Role> allowedRolesForProtectedPersons = List.of(
      Role.DOCTOR,
      Role.PRIVATE_DOCTOR,
      Role.DENTIST
  );
  CertificateActionContentProvider contentProvider;
}