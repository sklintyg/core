package se.inera.intyg.certificateservice.domain.user.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.common.model.Agreement;
import se.inera.intyg.certificateservice.domain.common.model.AllowCopy;
import se.inera.intyg.certificateservice.domain.common.model.Blocked;
import se.inera.intyg.certificateservice.domain.common.model.HealthCareProfessionalLicence;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;
import se.inera.intyg.certificateservice.domain.patient.model.Name;

@Value
@Builder
public class User {

  HsaId hsaId;
  Name name;
  Role role;
  List<PaTitle> paTitles;
  List<Speciality> specialities;
  Blocked blocked;
  Agreement agreement;
  AllowCopy allowCopy;
  AccessScope accessScope;
  List<HealthCareProfessionalLicence> healthCareProfessionalLicence;
  ResponsibleIssuer responsibleIssuer;
  SrsActive srsActive;
}