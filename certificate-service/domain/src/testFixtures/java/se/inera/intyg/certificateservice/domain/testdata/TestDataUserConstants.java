package se.inera.intyg.certificateservice.domain.testdata;

import java.util.Collections;
import java.util.List;
import se.inera.intyg.certificateservice.domain.common.model.Blocked;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;

public class TestDataUserConstants {

  public static final Blocked BLOCKED_TRUE = new Blocked(true);
  public static final Blocked BLOCKED_FALSE = new Blocked(false);
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

  public static final String ALVA_VARDADMINISTRATOR_HSA_ID = "TSTNMT2321000156-VAAA";
  public static final String ALVA_VARDADMINISTRATOR_FIRST_NAME = "Alva";
  public static final String ALVA_VARDADMINISTRATOR_MIDDLE_NAME = "";
  public static final String ALVA_VARDADMINISTRATOR_LAST_NAME = "Vardadministrator";
  public static final String ALVA_VARDADMINISTRATOR_FULL_NAME = "Alva Vardadministrator";
  public static final Role ALVA_VARDADMINISTRATOR_ROLE = Role.CARE_ADMIN;
  public static final List<PaTitle> ALVA_VARDADMINISTRATOR_PA_TITLES = Collections.emptyList();
  public static final List<Speciality> ALVA_VARDADMINISTRATOR_SPECIALITIES = Collections.emptyList();
  public static final Blocked ALVA_VARDADMINISTRATOR_BLOCKED = BLOCKED_FALSE;

}
