package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.common.model.Role;

@Value
@Builder
public class CertificateActionSpecification {

  CertificateActionType certificateActionType;
  List<Role> allowedRoles;
}
