package se.inera.intyg.certificateservice.domain.testdata;

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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_ALLOW_COPY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_ALLOW_COPY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_SPECIALITIES;
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

import java.util.Collections;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

public class TestDataStaff {

  public static final Staff AJLA_DOKTOR = ajlaDoctorBuilder().build();
  public static final Staff ALF_DOKTOR = alfDoktorBuilder().build();
  public static final Staff ALVA_VARDADMINISTRATOR = alvaVardadmininstratorBuilder().build();
  public static final Staff ANNA_SJUKSKOTERSKA = annaSjukskoterskaBuilder().build();
  public static final Staff BERTIL_BARNMORSKA = bertilBarnmorskaBuilder().build();

  public static Staff.StaffBuilder ajlaDoctorBuilder() {
    return Staff.builder()
        .hsaId(new HsaId(AJLA_DOCTOR_HSA_ID))
        .name(
            Name.builder()
                .firstName(AJLA_DOCTOR_FIRST_NAME)
                .middleName(AJLA_DOCTOR_MIDDLE_NAME)
                .lastName(AJLA_DOCTOR_LAST_NAME)
                .build()
        )
        .paTitles(AJLA_DOCTOR_PA_TITLES)
        .specialities(AJLA_DOCTOR_SPECIALITIES)
        .blocked(AJLA_DOCTOR_BLOCKED)
        .allowCopy(AJLA_DOCTOR_ALLOW_COPY)
        .role(AJLA_DOCTOR_ROLE)
        .healthCareProfessionalLicence(AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES);
  }

  public static Staff.StaffBuilder alfDoktorBuilder() {
    return Staff.builder()
        .hsaId(new HsaId(ALF_DOKTOR_HSA_ID))
        .name(
            Name.builder()
                .firstName(ALF_DOKTOR_FIRST_NAME)
                .middleName(ALF_DOKTOR_MIDDLE_NAME)
                .lastName(ALF_DOKTOR_LAST_NAME)
                .build()
        )
        .blocked(ALF_DOKTOR_BLOCKED)
        .allowCopy(ALF_DOKTOR_ALLOW_COPY)
        .paTitles(ALF_DOKTOR_PA_TITLES)
        .specialities(ALF_DOKTOR_SPECIALITIES)
        .role(ALF_DOKTOR_ROLE)
        .healthCareProfessionalLicence(ALF_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES);
  }

  public static Staff.StaffBuilder alvaVardadmininstratorBuilder() {
    return Staff.builder()
        .hsaId(new HsaId(ALVA_VARDADMINISTRATOR_HSA_ID))
        .name(
            Name.builder()
                .firstName(ALVA_VARDADMINISTRATOR_FIRST_NAME)
                .middleName(ALVA_VARDADMINISTRATOR_MIDDLE_NAME)
                .lastName(ALVA_VARDADMINISTRATOR_LAST_NAME)
                .build()
        )
        .blocked(ALVA_VARDADMINISTRATOR_BLOCKED)
        .allowCopy(ALVA_VARDADMINISTRATOR_ALLOW_COPY)
        .paTitles(ALVA_VARDADMINISTRATOR_PA_TITLES)
        .specialities(ALVA_VARDADMINISTRATOR_SPECIALITIES)
        .role(ALVA_VARDADMINISTRATOR_ROLE)
        .healthCareProfessionalLicence(Collections.emptyList());
  }

  public static Staff.StaffBuilder annaSjukskoterskaBuilder() {
    return Staff.builder()
        .hsaId(new HsaId(ANNA_SJUKSKOTERSKA_HSA_ID))
        .name(
            Name.builder()
                .firstName(ANNA_SJUKSKOTERSKA_FIRST_NAME)
                .middleName(ANNA_SJUKSKOTERSKA_MIDDLE_NAME)
                .lastName(ANNA_SJUKSKOTERSKA_LAST_NAME)
                .build()
        )
        .blocked(ANNA_SJUKSKOTERSKA_BLOCKED)
        .allowCopy(ANNA_SJUKSKOTERSKA_ALLOW_COPY)
        .paTitles(ANNA_SJUKSKOTERSKA_PA_TITLES)
        .specialities(ANNA_SJUKSKOTERSKA_SPECIALITIES)
        .role(ANNA_SJUKSKOTERSKA_ROLE)
        .healthCareProfessionalLicence(ANNA_SJUKSKOTERSKA_HEALTH_CARE_PROFESSIONAL_LICENCES);
  }

  public static Staff.StaffBuilder bertilBarnmorskaBuilder() {
    return Staff.builder()
        .hsaId(new HsaId(BERTIL_BARNMORSKA_HSA_ID))
        .name(
            Name.builder()
                .firstName(BERTIL_BARNMORSKA_FIRST_NAME)
                .middleName(BERTIL_BARNMORSKA_MIDDLE_NAME)
                .lastName(BERTIL_BARNMORSKA_LAST_NAME)
                .build()
        )
        .blocked(BERTIL_BARNMORSKA_BLOCKED)
        .allowCopy(BERTIL_BARNMORSKA_ALLOW_COPY)
        .paTitles(BERTIL_BARNMORSKA_PA_TITLES)
        .specialities(BERTIL_BARNMORSKA_SPECIALITIES)
        .role(BERTIL_BARNMORSKA_ROLE)
        .healthCareProfessionalLicence(BERTIL_BARNMORSKA_HEALTH_CARE_PROFESSIONAL_LICENCES);
  }
}
