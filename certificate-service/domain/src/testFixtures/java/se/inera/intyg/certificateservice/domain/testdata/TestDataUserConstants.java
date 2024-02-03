package se.inera.intyg.certificateservice.domain.testdata;

import se.inera.intyg.certificateservice.domain.certificate.model.Blocked;
import se.inera.intyg.certificateservice.domain.user.model.UserRole;

public class TestDataUserConstants {

  public static final Blocked BLOCKED_TRUE = new Blocked(true);
  public static final Blocked BLOCKED_FALSE = new Blocked(false);
  public static final String AJLA_DOCTOR_HSA_ID = "TSTNMT2321000156-DRAA";
  public static final String AJLA_DOCTOR_NAME = "Ajla Doktor";
  public static final UserRole AJLA_DOCTOR_ROLE = UserRole.DOCTOR;
  public static final Blocked AJLA_DOCTOR_BLOCKED = BLOCKED_FALSE;

}
