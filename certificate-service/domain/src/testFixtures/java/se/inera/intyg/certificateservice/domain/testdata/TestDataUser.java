package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BLOCKED_TRUE;

import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.user.model.User;

public class TestDataUser {
  
  public static final User AJLA_DOKTOR = User.builder()
      .hsaId(new HsaId(AJLA_DOCTOR_HSA_ID))
      .name(
          Name.builder()
              .lastName(AJLA_DOCTOR_NAME)
              .build()
      )
      .blocked(AJLA_DOCTOR_BLOCKED)
      .role(AJLA_DOCTOR_ROLE)
      .build();

  public static final User AJLA_DOKTOR_IS_BLOCKED = User.builder()
      .hsaId(new HsaId(AJLA_DOCTOR_HSA_ID))
      .name(
          Name.builder()
              .lastName(AJLA_DOCTOR_NAME)
              .build()
      )
      .blocked(BLOCKED_TRUE)
      .role(AJLA_DOCTOR_ROLE)
      .build();
}
