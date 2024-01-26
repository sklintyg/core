package se.inera.intyg.certificateservice.application.common;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.user.model.User;

@Component
public class ActionEvaluationFactory {

  public ActionEvaluation create(PatientDTO patient, UserDTO user) {

    return ActionEvaluation.builder()
        .patient(
            Patient.builder()
                .deceased(patient.getDeceased())
                .build()
        )
        .user(
            User.builder()
                .blocked(user.getBlocked())
                .build()
        )
        .build();
  }
}
