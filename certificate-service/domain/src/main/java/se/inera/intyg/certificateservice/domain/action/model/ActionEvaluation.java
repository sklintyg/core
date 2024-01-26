package se.inera.intyg.certificateservice.domain.action.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.user.model.User;

@Value
@Builder
public class ActionEvaluation {

  Patient patient;
  User user;
}
