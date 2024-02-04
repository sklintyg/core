package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_ROLE;

import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.patient.model.Name;

public class TestDataStaff {

  public static final Staff AJLA_DOKTOR = ajlaDoctorBuilder().build();
  public static final Staff ALVA_VARDADMINISTRATOR = alvaVardadmininstratorBuilder().build();

  public static Staff.StaffBuilder ajlaDoctorBuilder() {
    return Staff.builder()
        .hsaId(new HsaId(AJLA_DOCTOR_HSA_ID))
        .name(
            Name.builder()
                .lastName(AJLA_DOCTOR_NAME)
                .build()
        )
        .blocked(AJLA_DOCTOR_BLOCKED)
        .role(AJLA_DOCTOR_ROLE);
  }

  public static Staff.StaffBuilder alvaVardadmininstratorBuilder() {
    return Staff.builder()
        .hsaId(new HsaId(ALVA_VARDADMINISTRATOR_HSA_ID))
        .name(
            Name.builder()
                .lastName(ALVA_VARDADMINISTRATOR_NAME)
                .build()
        )
        .blocked(ALVA_VARDADMINISTRATOR_BLOCKED)
        .role(ALVA_VARDADMINISTRATOR_ROLE);
  }
}
