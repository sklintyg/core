package se.inera.intyg.certificateservice.domain.common.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants;

class PersonIdTest {

  @Test
  void shallReturnPatientIdWithoutDashWhenWithDash() {
    final var patientIdWithDash = PersonId.builder()
        .id(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID)
        .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
        .build();

    assertEquals(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH,
        patientIdWithDash.idWithoutDash()
    );
  }

  @Test
  void shallReturnPatientIdWithoutDashWhenWithoutDash() {
    final var patientIdWithDash = PersonId.builder()
        .id(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)
        .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
        .build();

    assertEquals(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH,
        patientIdWithDash.idWithoutDash()
    );
  }

  @Test
  void shallReturnPatientIdWithDashWhenWithDash() {
    final var patientIdWithoutDash = PersonId.builder()
        .id(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID)
        .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
        .build();

    assertEquals(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID,
        patientIdWithoutDash.idWithDash()
    );
  }

  @Test
  void shallReturnPatientIdWithDashWhenWithoutDash() {
    final var patientIdWithoutDash = PersonId.builder()
        .id(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)
        .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
        .build();

    assertEquals(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID,
        patientIdWithoutDash.idWithDash()
    );
  }
}