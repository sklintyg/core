package se.inera.intyg.certificateservice.domain.action.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
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
  PersonId citizen;


  public boolean isIssuingUnitCareUnit() {
    return subUnit.hsaId().equals(careUnit.hsaId());
  }

  public boolean isIssuingUnitSubUnit() {
    return !isIssuingUnitCareUnit();
  }
}
