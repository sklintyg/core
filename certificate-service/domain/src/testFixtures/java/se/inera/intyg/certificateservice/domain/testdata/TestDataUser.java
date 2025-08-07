package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ACCESS_SCOPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_AGREEMENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ALLOW_COPY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_SRS_ACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_AGREEMENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_ALLOW_COPY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_SRS_ACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_AGREEMENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_ALLOW_COPY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_SRS_ACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_AGREEMENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_ALLOW_COPY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_HEALTH_CARE_PROFESSIONAL_LICENCES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ANNA_SJUKSKOTERSKA_SRS_ACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_AGREEMENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_ALLOW_COPY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_HEALTH_CARE_PROFESSIONAL_LICENCES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_SRS_ACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.DAN_DENTIST_AGREEMENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.DAN_DENTIST_ALLOW_COPY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.DAN_DENTIST_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.DAN_DENTIST_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.DAN_DENTIST_HEALTH_CARE_PROFESSIONAL_LICENCES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.DAN_DENTIST_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.DAN_DENTIST_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.DAN_DENTIST_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.DAN_DENTIST_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.DAN_DENTIST_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.DAN_DENTIST_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.DAN_DENTIST_SRS_ACTIVE;

import java.util.Collections;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.user.model.User;
import se.inera.intyg.certificateservice.domain.user.model.User.UserBuilder;

public class TestDataUser {

  public static final User AJLA_DOKTOR = ajlaDoctorBuilder().build();
  public static final User DAN_DENTIST = danDentistBuilder().build();
  public static final User ANNA_SJUKSKOTERKSA = annaSjukskoterskaBuilder().build();
  public static final User ALF_DOKTOR = alfDoktorBuilder().build();
  public static final User ALVA_VARDADMINISTRATOR = alvaVardadministratorBuilder().build();
  public static final User BERTIL_BARNMORSKA = bertilBarnmorskaBuilder().build();

  public static UserBuilder annaSjukskoterskaBuilder() {
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
        .agreement(ANNA_SJUKSKOTERSKA_AGREEMENT)
        .allowCopy(ANNA_SJUKSKOTERSKA_ALLOW_COPY)
        .paTitles(ANNA_SJUKSKOTERSKA_PA_TITLES)
        .specialities(ANNA_SJUKSKOTERSKA_SPECIALITIES)
        .role(ANNA_SJUKSKOTERSKA_ROLE)
        .accessScope(AccessScope.WITHIN_CARE_UNIT)
        .healthCareProfessionalLicence(ANNA_SJUKSKOTERSKA_HEALTH_CARE_PROFESSIONAL_LICENCES)
        .srsActive(ANNA_SJUKSKOTERSKA_SRS_ACTIVE);
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
        .agreement(AJLA_DOCTOR_AGREEMENT)
        .allowCopy(AJLA_DOCTOR_ALLOW_COPY)
        .paTitles(AJLA_DOCTOR_PA_TITLES)
        .specialities(AJLA_DOCTOR_SPECIALITIES)
        .role(AJLA_DOCTOR_ROLE)
        .accessScope(AJLA_DOCTOR_ACCESS_SCOPE)
        .healthCareProfessionalLicence(AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES)
        .srsActive(AJLA_DOCTOR_SRS_ACTIVE);
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
        .agreement(ALF_DOKTOR_AGREEMENT)
        .allowCopy(ALF_DOKTOR_ALLOW_COPY)
        .paTitles(ALF_DOKTOR_PA_TITLES)
        .specialities(ALF_DOKTOR_SPECIALITIES)
        .role(ALF_DOKTOR_ROLE)
        .healthCareProfessionalLicence(ALF_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES)
        .srsActive(ALF_DOKTOR_SRS_ACTIVE);
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
        .agreement(ALVA_VARDADMINISTRATOR_AGREEMENT)
        .allowCopy(ALVA_VARDADMINISTRATOR_ALLOW_COPY)
        .paTitles(ALVA_VARDADMINISTRATOR_PA_TITLES)
        .specialities(ALVA_VARDADMINISTRATOR_SPECIALITIES)
        .role(ALVA_VARDADMINISTRATOR_ROLE)
        .accessScope(AccessScope.WITHIN_CARE_UNIT)
        .healthCareProfessionalLicence(Collections.emptyList())
        .srsActive(ALVA_VARDADMINISTRATOR_SRS_ACTIVE);
  }

  public static UserBuilder bertilBarnmorskaBuilder() {
    return User.builder()
        .hsaId(new HsaId(BERTIL_BARNMORSKA_HSA_ID))
        .name(
            Name.builder()
                .firstName(BERTIL_BARNMORSKA_FIRST_NAME)
                .middleName(BERTIL_BARNMORSKA_MIDDLE_NAME)
                .lastName(BERTIL_BARNMORSKA_LAST_NAME)
                .build()
        )
        .blocked(BERTIL_BARNMORSKA_BLOCKED)
        .agreement(BERTIL_BARNMORSKA_AGREEMENT)
        .allowCopy(BERTIL_BARNMORSKA_ALLOW_COPY)
        .paTitles(BERTIL_BARNMORSKA_PA_TITLES)
        .specialities(BERTIL_BARNMORSKA_SPECIALITIES)
        .role(BERTIL_BARNMORSKA_ROLE)
        .accessScope(AccessScope.WITHIN_CARE_UNIT)
        .healthCareProfessionalLicence(BERTIL_BARNMORSKA_HEALTH_CARE_PROFESSIONAL_LICENCES)
        .srsActive(BERTIL_BARNMORSKA_SRS_ACTIVE);
  }

  public static UserBuilder danDentistBuilder() {
    return User.builder()
        .hsaId(new HsaId(DAN_DENTIST_HSA_ID))
        .name(
            Name.builder()
                .firstName(DAN_DENTIST_FIRST_NAME)
                .middleName(DAN_DENTIST_MIDDLE_NAME)
                .lastName(DAN_DENTIST_LAST_NAME)
                .build()
        )
        .blocked(DAN_DENTIST_BLOCKED)
        .agreement(DAN_DENTIST_AGREEMENT)
        .allowCopy(DAN_DENTIST_ALLOW_COPY)
        .paTitles(DAN_DENTIST_PA_TITLES)
        .specialities(DAN_DENTIST_SPECIALITIES)
        .role(DAN_DENTIST_ROLE)
        .accessScope(AccessScope.WITHIN_CARE_UNIT)
        .healthCareProfessionalLicence(DAN_DENTIST_HEALTH_CARE_PROFESSIONAL_LICENCES)
        .srsActive(DAN_DENTIST_SRS_ACTIVE);
  }
}