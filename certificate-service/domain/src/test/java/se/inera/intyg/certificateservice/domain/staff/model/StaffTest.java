package se.inera.intyg.certificateservice.domain.staff.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.testdata.TestDataStaff;
import se.inera.intyg.certificateservice.domain.testdata.TestDataUser;

class StaffTest {

  @Test
  void shallReturnStaffWhenCreatingFromUserAjla() {
    assertEquals(TestDataStaff.AJLA_DOKTOR,
        Staff.create(TestDataUser.AJLA_DOKTOR)
    );
  }

  @Test
  void shallReturnStaffWhenCreatingFromUserAlf() {
    assertEquals(TestDataStaff.ALF_DOKTOR,
        Staff.create(TestDataUser.ALF_DOKTOR)
    );
  }

  @Test
  void shallReturnStaffWhenCreatingFromUserAlva() {
    assertEquals(TestDataStaff.ALVA_VARDADMINISTRATOR,
        Staff.create(TestDataUser.ALVA_VARDADMINISTRATOR)
    );
  }

  @Test
  void shallReturnStaffWhenCreatingFromUserAnna() {
    assertEquals(TestDataStaff.ANNA_SJUKSKOTERSKA,
        Staff.create(TestDataUser.ANNA_SJUKSKOTERKSA)
    );
  }

  @Test
  void shallReturnStaffWhenCreatingFromUserBertil() {
    assertEquals(TestDataStaff.BERTIL_BARNMORSKA,
        Staff.create(TestDataUser.BERTIL_BARNMORSKA)
    );
  }
}
