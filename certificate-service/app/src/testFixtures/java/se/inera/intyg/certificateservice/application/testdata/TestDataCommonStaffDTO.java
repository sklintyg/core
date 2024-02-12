package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_NAME;

import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;

public class TestDataCommonStaffDTO {

  public static final StaffDTO AJLA_DOKTOR = ajlaDoctorBuilder().build();
  public static final StaffDTO ALVA_VARDADMINISTRATOR = alvaVardadmininstratorBuilder().build();


  public static StaffDTO.StaffDTOBuilder ajlaDoctorBuilder() {
    return StaffDTO.builder()
        .personId(AJLA_DOCTOR_HSA_ID)
        .fullName(AJLA_DOCTOR_NAME);
  }

  public static StaffDTO.StaffDTOBuilder alvaVardadmininstratorBuilder() {
    return StaffDTO.builder()
        .personId(ALVA_VARDADMINISTRATOR_HSA_ID)
        .fullName(ALVA_VARDADMINISTRATOR_NAME);
  }
}
