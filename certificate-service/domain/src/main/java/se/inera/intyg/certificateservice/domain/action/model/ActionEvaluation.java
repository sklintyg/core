package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Objects;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificate.model.CareProvider;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
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


  public boolean isIssuingUnitCareUnit(SubUnit subUnit, CareUnit careUnit) {
    return Objects.equals(subUnit.hsaId().id(), careUnit.hsaId().id());
  }
}
