package se.inera.intyg.certificateservice.domain.staff.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.common.model.AllowCopy;
import se.inera.intyg.certificateservice.domain.common.model.Blocked;
import se.inera.intyg.certificateservice.domain.common.model.HealthCareProfessionalLicence;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.user.model.User;

@Value
@Builder
public class Staff {

  HsaId hsaId;
  Name name;
  Role role;
  List<PaTitle> paTitles;
  List<Speciality> specialities;
  Blocked blocked;
  AllowCopy allowCopy;
  List<HealthCareProfessionalLicence> healthCareProfessionalLicence;

  public static Staff create(User user) {
    return Staff.builder()
        .hsaId(user.hsaId())
        .name(user.name())
        .role(user.role())
        .paTitles(user.paTitles().stream().toList())
        .specialities(user.specialities().stream().toList())
        .blocked(user.blocked())
        .allowCopy(user.allowCopy())
        .healthCareProfessionalLicence(user.healthCareProfessionalLicence().stream().toList())
        .build();
  }
}
