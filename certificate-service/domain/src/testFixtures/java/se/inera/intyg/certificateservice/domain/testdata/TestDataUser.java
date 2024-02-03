package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ROLE;

import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.user.model.User;
import se.inera.intyg.certificateservice.domain.user.model.User.UserBuilder;

public class TestDataUser {

  public static final User AJLA_DOKTOR = ajlaDoctorBuilder().build();

  public static UserBuilder ajlaDoctorBuilder() {
    return User.builder()
        .hsaId(new HsaId(AJLA_DOCTOR_HSA_ID))
        .name(
            Name.builder()
                .lastName(AJLA_DOCTOR_NAME)
                .build()
        )
        .blocked(AJLA_DOCTOR_BLOCKED)
        .role(AJLA_DOCTOR_ROLE);
  }
}
