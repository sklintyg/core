package se.inera.intyg.certificateservice.application.common;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.CareProvider;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
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

  public ActionEvaluation create(PatientDTO patient, UserDTO user, UnitDTO unit, UnitDTO careUnit,
      UnitDTO careProvider) {
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
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(unit.getId()))
                .name(unit.getName())
                .address(unit.getAddress())
                .zipCode(unit.getZipCode())
                .city(unit.getCity())
                .phoneNumber(unit.getPhoneNumber())
                .email(unit.getEmail())
                .inactive(unit.getInactive())
                .build()
        )
        .careUnit(
            CareUnit.builder()
                .hsaId(new HsaId(careUnit.getId()))
                .name(careUnit.getName())
                .address(careUnit.getAddress())
                .zipCode(careUnit.getZipCode())
                .city(careUnit.getCity())
                .phoneNumber(careUnit.getPhoneNumber())
                .email(careUnit.getEmail())
                .inactive(careUnit.getInactive())
                .build()
        )
        .careProvider(
            CareProvider.builder()
                .hsaId(new HsaId(careProvider.getId()))
                .name(careProvider.getName())
                .address(careProvider.getAddress())
                .zipCode(careProvider.getZipCode())
                .city(careProvider.getCity())
                .phoneNumber(careProvider.getPhoneNumber())
                .email(careProvider.getEmail())
                .build()
        )
        .build();

  }
}
