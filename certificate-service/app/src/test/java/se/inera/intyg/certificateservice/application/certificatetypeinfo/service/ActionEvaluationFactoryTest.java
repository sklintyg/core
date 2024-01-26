package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.UserDTO;

class ActionEvaluationFactoryTest {

  private ActionEvaluationFactory actionEvaluationFactory;

  private static final UserDTO DEFAULT_USER = UserDTO.builder()
      .blocked(false)
      .build();

  private static final PatientDTO DEFAULT_PATIENT_DTO = PatientDTO.builder()
      .deceased(false)
      .build();

  @BeforeEach
  void setUp() {
    actionEvaluationFactory = new ActionEvaluationFactory();
  }

  @Test
  void shallIncludePatientDeceasedTrue() {
    final var patient = PatientDTO.builder()
        .deceased(true)
        .build();

    final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER);

    assertTrue(actionEvaluation.getPatient().isDeceased(), "Expected patient.deceased to be true");
  }

  @Test
  void shallIncludePatientDeceasedFalse() {
    final var patient = PatientDTO.builder()
        .deceased(false)
        .build();

    final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER);

    assertFalse(actionEvaluation.getPatient().isDeceased(),
        "Expected patient.deceased to be false");
  }

  @Test
  void shallIncludeUserBlockedFalse() {
    final var user = UserDTO.builder()
        .blocked(false)
        .build();

    final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO, user);

    assertFalse(actionEvaluation.getUser().isBlocked(),
        "Expected user.blocked to be false");
  }

  @Test
  void shallIncludeUserBlockedTrue() {
    final var user = UserDTO.builder()
        .blocked(true)
        .build();

    final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO, user);

    assertTrue(actionEvaluation.getUser().isBlocked(),
        "Expected user.blocked to be true");
  }
}
