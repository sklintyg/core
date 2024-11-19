package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FULLNAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_FULL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_FULL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_MIDDLE_NAME;

import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;

public class TestDataCommonStaffDTO {

  private TestDataCommonStaffDTO() {
    throw new IllegalStateException("Utility class");
  }

  public static final StaffDTO AJLA_DOKTOR = ajlaDoctorBuilder().build();
  public static final StaffDTO ALVA_VARDADMINISTRATOR = alvaVardadmininstratorBuilder().build();
  public static final StaffDTO BERTIL_BARNMORSKA = bertilBarnmorskaBuilder().build();

  public static StaffDTO.StaffDTOBuilder ajlaDoctorBuilder() {
    return StaffDTO.builder()
        .personId(AJLA_DOCTOR_HSA_ID)
        .firstName(AJLA_DOCTOR_FIRST_NAME)
        .middleName(AJLA_DOCTOR_MIDDLE_NAME)
        .lastName(AJLA_DOCTOR_LAST_NAME)
        .fullName(AJLA_DOCTOR_FULLNAME);
  }

  public static StaffDTO.StaffDTOBuilder alvaVardadmininstratorBuilder() {
    return StaffDTO.builder()
        .personId(ALVA_VARDADMINISTRATOR_HSA_ID)
        .firstName(ALVA_VARDADMINISTRATOR_FIRST_NAME)
        .middleName(ALVA_VARDADMINISTRATOR_MIDDLE_NAME)
        .lastName(ALVA_VARDADMINISTRATOR_LAST_NAME)
        .fullName(ALVA_VARDADMINISTRATOR_FULL_NAME);
  }

  public static StaffDTO.StaffDTOBuilder bertilBarnmorskaBuilder() {
    return StaffDTO.builder()
        .personId(BERTIL_BARNMORSKA_HSA_ID)
        .firstName(BERTIL_BARNMORSKA_FIRST_NAME)
        .middleName(BERTIL_BARNMORSKA_MIDDLE_NAME)
        .lastName(BERTIL_BARNMORSKA_LAST_NAME)
        .fullName(BERTIL_BARNMORSKA_FULL_NAME);
  }
}
