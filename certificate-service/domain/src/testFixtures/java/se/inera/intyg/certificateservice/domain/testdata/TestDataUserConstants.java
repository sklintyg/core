package se.inera.intyg.certificateservice.domain.testdata;

import se.inera.intyg.certificateservice.domain.staff.model.Blocked;
import se.inera.intyg.certificateservice.domain.staff.model.Role;

public class TestDataUserConstants {

  public static final Blocked BLOCKED_TRUE = new Blocked(true);
  public static final Blocked BLOCKED_FALSE = new Blocked(false);
  public static final String AJLA_DOCTOR_HSA_ID = "TSTNMT2321000156-DRAA";
  public static final String AJLA_DOCTOR_FIRST_NAME = "Ajla";
  public static final String AJLA_DOCTOR_MIDDLE_NAME = "";
  public static final String AJLA_DOCTOR_LAST_NAME = "Doktor";
  public static final String AJLA_DOCTOR_FULLNAME = "Ajla Doktor";
  public static final Role AJLA_DOCTOR_ROLE = Role.DOCTOR;
  public static final Blocked AJLA_DOCTOR_BLOCKED = BLOCKED_FALSE;
  public static final String ALVA_VARDADMINISTRATOR_HSA_ID = "TSTNMT2321000156-VAAA";
  public static final String ALVA_VARDADMINISTRATOR_FIRST_NAME = "Alva";
  public static final String ALVA_VARDADMINISTRATOR_MIDDLE_NAME = "";
  public static final String ALVA_VARDADMINISTRATOR_LAST_NAME = "Vardadministrator";
  public static final String ALVA_VARDADMINISTRATOR_FULL_NAME = "Alva Vardadministrator";
  public static final Role ALVA_VARDADMINISTRATOR_ROLE = Role.CARE_ADMIN;
  public static final Blocked ALVA_VARDADMINISTRATOR_BLOCKED = BLOCKED_FALSE;

}
