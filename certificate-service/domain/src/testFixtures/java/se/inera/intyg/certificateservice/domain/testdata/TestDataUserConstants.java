package se.inera.intyg.certificateservice.domain.testdata;

import java.util.Collections;
import java.util.List;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.common.model.Agreement;
import se.inera.intyg.certificateservice.domain.common.model.AllowCopy;
import se.inera.intyg.certificateservice.domain.common.model.Blocked;
import se.inera.intyg.certificateservice.domain.common.model.HealthCareProfessionalLicence;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;
import se.inera.intyg.certificateservice.domain.user.model.ResponsibleIssuer;
import se.inera.intyg.certificateservice.domain.user.model.SrsActive;

public class TestDataUserConstants {

  public static final Blocked BLOCKED_TRUE = new Blocked(true);
  public static final Blocked BLOCKED_FALSE = new Blocked(false);
  public static final AllowCopy ALLOW_COPY_TRUE = new AllowCopy(true);
  public static final AllowCopy ALLOW_COPY_FALSE = new AllowCopy(false);
  public static final Agreement AGREEMENT_TRUE = new Agreement(true);
  public static final Agreement AGREEMENT_FALSE = new Agreement(false);
  public static final String AJLA_DOCTOR_HSA_ID = "TSTNMT2321000156-DRAA";
  public static final String AJLA_DOCTOR_FIRST_NAME = "Ajla";
  public static final String AJLA_DOCTOR_MIDDLE_NAME = "";
  public static final String AJLA_DOCTOR_LAST_NAME = "Doktor";
  public static final String AJLA_DOCTOR_FULLNAME = "Ajla Doktor";
  public static final Role AJLA_DOCTOR_ROLE = Role.DOCTOR;
  public static final List<PaTitle> AJLA_DOCTOR_PA_TITLES = List.of(
      new PaTitle("203090", "Läkare legitimerad, annan"),
      new PaTitle("601010", "Kock")
  );
  public static final List<Speciality> AJLA_DOCTOR_SPECIALITIES = List.of(
      new Speciality("Allmänmedicin"),
      new Speciality("Psykiatri")
  );
  public static final Blocked AJLA_DOCTOR_BLOCKED = BLOCKED_FALSE;
  public static final AllowCopy AJLA_DOCTOR_ALLOW_COPY = ALLOW_COPY_TRUE;
  public static final Agreement AJLA_DOCTOR_AGREEMENT = AGREEMENT_TRUE;
  public static final AccessScope AJLA_DOCTOR_ACCESS_SCOPE = AccessScope.WITHIN_CARE_UNIT;
  public static final List<HealthCareProfessionalLicence> AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES = List.of(
      new HealthCareProfessionalLicence("Läkare"));
  public static final List<HealthCareProfessionalLicence> ALF_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES = List.of(
      new HealthCareProfessionalLicence("Läkare"));
  public static final ResponsibleIssuer AJLA_DOCTOR_RESPONSIBLE_ISSUER = new ResponsibleIssuer(
      "Ajla Doktor");
  public static final SrsActive AJLA_DOCTOR_SRS_ACTIVE = new SrsActive(true);

  public static final String ALF_DOKTOR_HSA_ID = "TSTNMT2321000156-DRAF";
  public static final String ALF_DOKTOR_FIRST_NAME = "Alf";
  public static final String ALF_DOKTOR_MIDDLE_NAME = "";
  public static final String ALF_DOKTOR_LAST_NAME = "Doktor";
  public static final String ALF_DOKTOR_FULLNAME = "Alf Doktor";
  public static final Role ALF_DOKTOR_ROLE = Role.DOCTOR;
  public static final List<PaTitle> ALF_DOKTOR_PA_TITLES = List.of(
      new PaTitle("203090", "Läkare legitimerad, annan")
  );
  public static final List<Speciality> ALF_DOKTOR_SPECIALITIES = List.of(
      new Speciality("Allmänmedicin")
  );
  public static final Blocked ALF_DOKTOR_BLOCKED = BLOCKED_FALSE;
  public static final Agreement ALF_DOKTOR_AGREEMENT = AGREEMENT_TRUE;
  public static final AllowCopy ALF_DOKTOR_ALLOW_COPY = ALLOW_COPY_TRUE;
  public static final SrsActive ALF_DOKTOR_SRS_ACTIVE = new SrsActive(false);


  public static final String ALVA_VARDADMINISTRATOR_HSA_ID = "TSTNMT2321000156-VAAA";
  public static final String ALVA_VARDADMINISTRATOR_FIRST_NAME = "Alva";
  public static final String ALVA_VARDADMINISTRATOR_MIDDLE_NAME = "";
  public static final String ALVA_VARDADMINISTRATOR_LAST_NAME = "Vardadministrator";
  public static final String ALVA_VARDADMINISTRATOR_FULL_NAME = "Alva Vardadministrator";
  public static final Role ALVA_VARDADMINISTRATOR_ROLE = Role.CARE_ADMIN;
  public static final List<PaTitle> ALVA_VARDADMINISTRATOR_PA_TITLES = Collections.emptyList();
  public static final List<Speciality> ALVA_VARDADMINISTRATOR_SPECIALITIES = Collections.emptyList();
  public static final Blocked ALVA_VARDADMINISTRATOR_BLOCKED = BLOCKED_FALSE;
  public static final Agreement ALVA_VARDADMINISTRATOR_AGREEMENT = AGREEMENT_TRUE;
  public static final AllowCopy ALVA_VARDADMINISTRATOR_ALLOW_COPY = ALLOW_COPY_TRUE;
  public static final SrsActive ALVA_VARDADMINISTRATOR_SRS_ACTIVE = new SrsActive(false);

  public static final String ANNA_SJUKSKOTERSKA_HSA_ID = "TSTNMT2321000156-SKAA";
  public static final String ANNA_SJUKSKOTERSKA_FIRST_NAME = "Anna";
  public static final String ANNA_SJUKSKOTERSKA_MIDDLE_NAME = "";
  public static final String ANNA_SJUKSKOTERSKA_LAST_NAME = "Sjuksköterska";
  public static final String ANNA_SJUKSKOTERSKA_FULL_NAME = "Anna Sjuksköterska";
  public static final Role ANNA_SJUKSKOTERSKA_ROLE = Role.NURSE;
  public static final List<HealthCareProfessionalLicence> ANNA_SJUKSKOTERSKA_HEALTH_CARE_PROFESSIONAL_LICENCES = List.of(
      new HealthCareProfessionalLicence("Sjuksköterska")
  );
  public static final List<PaTitle> ANNA_SJUKSKOTERSKA_PA_TITLES = Collections.emptyList();
  public static final List<Speciality> ANNA_SJUKSKOTERSKA_SPECIALITIES = Collections.emptyList();
  public static final Blocked ANNA_SJUKSKOTERSKA_BLOCKED = BLOCKED_FALSE;
  public static final Agreement ANNA_SJUKSKOTERSKA_AGREEMENT = AGREEMENT_TRUE;
  public static final AllowCopy ANNA_SJUKSKOTERSKA_ALLOW_COPY = ALLOW_COPY_TRUE;
  public static final SrsActive ANNA_SJUKSKOTERSKA_SRS_ACTIVE = new SrsActive(false);


  public static final String BERTIL_BARNMORSKA_HSA_ID = "TSTNMT2321000156-BMBL";
  public static final String BERTIL_BARNMORSKA_FIRST_NAME = "Bertil";
  public static final String BERTIL_BARNMORSKA_MIDDLE_NAME = "";
  public static final String BERTIL_BARNMORSKA_LAST_NAME = "Barnmorska";
  public static final String BERTIL_BARNMORSKA_FULL_NAME = "Bertil Barnmorska";
  public static final Role BERTIL_BARNMORSKA_ROLE = Role.MIDWIFE;
  public static final List<PaTitle> BERTIL_BARNMORSKA_PA_TITLES = Collections.emptyList();
  public static final List<Speciality> BERTIL_BARNMORSKA_SPECIALITIES = Collections.emptyList();
  public static final Blocked BERTIL_BARNMORSKA_BLOCKED = BLOCKED_FALSE;
  public static final Agreement BERTIL_BARNMORSKA_AGREEMENT = AGREEMENT_TRUE;
  public static final AllowCopy BERTIL_BARNMORSKA_ALLOW_COPY = ALLOW_COPY_TRUE;
  public static final List<HealthCareProfessionalLicence> BERTIL_BARNMORSKA_HEALTH_CARE_PROFESSIONAL_LICENCES = List.of(
      new HealthCareProfessionalLicence("Sjuksköterska"),
      new HealthCareProfessionalLicence("Barnmorska")
  );
  public static final SrsActive BERTIL_BARNMORSKA_SRS_ACTIVE = new SrsActive(false);


  public static final String DAN_DENTIST_HSA_ID = "TSTNMT2321000156-BMBL";
  public static final String DAN_DENTIST_FIRST_NAME = "Dan";
  public static final String DAN_DENTIST_MIDDLE_NAME = "";
  public static final String DAN_DENTIST_LAST_NAME = "Tandläkare";
  public static final String DAN_DENTIST_FULL_NAME = "Dan Tandläkare";
  public static final Role DAN_DENTIST_ROLE = Role.DENTIST;
  public static final List<PaTitle> DAN_DENTIST_PA_TITLES = Collections.emptyList();
  public static final List<Speciality> DAN_DENTIST_SPECIALITIES = Collections.emptyList();
  public static final Blocked DAN_DENTIST_BLOCKED = BLOCKED_FALSE;
  public static final AllowCopy DAN_DENTIST_ALLOW_COPY = ALLOW_COPY_TRUE;
  public static final Agreement DAN_DENTIST_AGREEMENT = AGREEMENT_TRUE;
  public static final List<HealthCareProfessionalLicence> DAN_DENTIST_HEALTH_CARE_PROFESSIONAL_LICENCES = List.of(
      new HealthCareProfessionalLicence("Tandläkare")
  );
  public static final SrsActive DAN_DENTIST_SRS_ACTIVE = new SrsActive(false);

}