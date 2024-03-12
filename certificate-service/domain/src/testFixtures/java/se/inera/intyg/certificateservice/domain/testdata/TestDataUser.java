package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_SPECIALITIES;

import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.user.model.User;
import se.inera.intyg.certificateservice.domain.user.model.User.UserBuilder;

public class TestDataUser {

  public static final User AJLA_DOKTOR = ajlaDoctorBuilder().build();
  public static final User ALVA_VARDADMINISTRATOR = alvaVardadministratorBuilder().build();

  public static UserBuilder ajlaDoctorBuilder() {
    return User.builder()
        .hsaId(new HsaId(AJLA_DOCTOR_HSA_ID))
        .name(
            Name.builder()
                .firstName(AJLA_DOCTOR_FIRST_NAME)
                .middleName(AJLA_DOCTOR_MIDDLE_NAME)
                .lastName(AJLA_DOCTOR_LAST_NAME)
                .build()
        )
        .blocked(AJLA_DOCTOR_BLOCKED)
        .paTitles(AJLA_DOCTOR_PA_TITLES)
        .specialities(AJLA_DOCTOR_SPECIALITIES)
        .role(AJLA_DOCTOR_ROLE);
  }

  public static UserBuilder alvaVardadministratorBuilder() {
    return User.builder()
        .hsaId(new HsaId(ALVA_VARDADMINISTRATOR_HSA_ID))
        .name(
            Name.builder()
                .firstName(ALVA_VARDADMINISTRATOR_FIRST_NAME)
                .middleName(ALVA_VARDADMINISTRATOR_MIDDLE_NAME)
                .lastName(ALVA_VARDADMINISTRATOR_LAST_NAME)
                .build()
        )
        .blocked(ALVA_VARDADMINISTRATOR_BLOCKED)
        .paTitles(ALVA_VARDADMINISTRATOR_PA_TITLES)
        .specialities(ALVA_VARDADMINISTRATOR_SPECIALITIES)
        .role(ALVA_VARDADMINISTRATOR_ROLE);
  }
}
