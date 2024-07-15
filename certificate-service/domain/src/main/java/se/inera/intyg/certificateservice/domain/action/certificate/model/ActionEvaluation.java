package se.inera.intyg.certificateservice.domain.action.certificate.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.domain.user.model.User;

@Value
@Builder
public class ActionEvaluation {

  @With
  Patient patient;
  User user;
  SubUnit subUnit;
  CareUnit careUnit;
  CareProvider careProvider;

  public boolean isIssuingUnitCareUnit() {
    return subUnit.hsaId().equals(careUnit.hsaId());
  }

  public boolean isIssuingUnitSubUnit() {
    return !isIssuingUnitCareUnit();
  }

  public boolean hasPatient() {
    return patient != null;
  }

  public boolean hasUser() {
    return user != null;
  }
}
