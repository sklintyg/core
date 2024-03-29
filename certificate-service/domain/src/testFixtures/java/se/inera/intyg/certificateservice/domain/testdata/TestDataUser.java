package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_TITLES;

import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.user.model.User;
import se.inera.intyg.certificateservice.domain.user.model.User.UserBuilder;

public class TestDataUser {

  public static final User AJLA_DOKTOR = ajlaDoctorBuilder().build();
  public static final User ANNA_SJUKSKOTERKSA = annaSjukskoterska().build();


  public static final User ALF_DOKTOR = alfDoktorBuilder().build();
  public static final User ALVA_VARDADMINISTRATOR = alvaVardadministratorBuilder().build();

  private static UserBuilder annaSjukskoterska() {
    return User.builder()
        .hsaId(new HsaId(ANNA_SJUKSKOTERSKA_HSA_ID))
        .name(
            Name.builder()
                .firstName(ANNA_SJUKSKOTERSKA_FIRST_NAME)
                .middleName(ANNA_SJUKSKOTERSKA_MIDDLE_NAME)
                .lastName(ANNA_SJUKSKOTERSKA_LAST_NAME)
                .build()
        )
        .blocked(ANNA_SJUKSKOTERSKA_BLOCKED)
        .paTitles(ANNA_SJUKSKOTERSKA_TITLES)
        .specialities(ANNA_SJUKSKOTERSKA_SPECIALITIES)
        .role(ANNA_SJUKSKOTERSKA_ROLE);
  }

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

  public static UserBuilder alfDoktorBuilder() {
    return User.builder()
        .hsaId(new HsaId(ALF_DOKTOR_HSA_ID))
        .name(
            Name.builder()
                .firstName(ALF_DOKTOR_FIRST_NAME)
                .middleName(ALF_DOKTOR_MIDDLE_NAME)
                .lastName(ALF_DOKTOR_LAST_NAME)
                .build()
        )
        .blocked(ALF_DOKTOR_BLOCKED)
        .paTitles(ALF_DOKTOR_PA_TITLES)
        .specialities(ALF_DOKTOR_SPECIALITIES)
        .role(ALF_DOKTOR_ROLE);
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
