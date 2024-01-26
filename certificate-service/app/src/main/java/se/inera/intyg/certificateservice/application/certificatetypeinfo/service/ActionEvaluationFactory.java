package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.UserDTO;
import se.inera.intyg.certificateservice.model.ActionEvaluation;
import se.inera.intyg.certificateservice.model.Patient;
import se.inera.intyg.certificateservice.model.User;

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
