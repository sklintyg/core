package se.inera.intyg.certificateservice.domain.testdata;

import se.inera.intyg.certificateservice.domain.certificate.model.Blocked;
import se.inera.intyg.certificateservice.domain.user.model.Role;

public class TestDataUserConstants {

  public static final Blocked BLOCKED_TRUE = new Blocked(true);
  public static final Blocked BLOCKED_FALSE = new Blocked(false);
  public static final String AJLA_DOCTOR_HSA_ID = "TSTNMT2321000156-DRAA";
  public static final String AJLA_DOCTOR_NAME = "Ajla Doktor";
  public static final Role AJLA_DOCTOR_ROLE = Role.DOCTOR;
  public static final Blocked AJLA_DOCTOR_BLOCKED = BLOCKED_FALSE;
  public static final String ALVA_VARDADMINISTRATOR_HSA_ID = "TSTNMT2321000156-VAAA";
  public static final String ALVA_VARDADMINISTRATOR_NAME = "Alva Vardadministrator";
  public static final Role ALVA_VARDADMINISTRATOR_ROLE = Role.CARE_ADMIN;
  public static final Blocked ALVA_VARDADMINISTRATOR_BLOCKED = BLOCKED_FALSE;

}
