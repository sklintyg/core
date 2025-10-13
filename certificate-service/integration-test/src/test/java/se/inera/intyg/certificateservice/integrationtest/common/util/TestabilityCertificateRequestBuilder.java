package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityCertificateRequest;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;

public class TestabilityCertificateRequestBuilder {

  private static final String VERSION = "1.0";
  private static final String TYPE = "fk7210";

  private CertificateModelIdDTO certificateModelId = CertificateModelIdDTO.builder()
      .version(VERSION)
      .type(TYPE)
      .build();

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;
  private TestabilityFillTypeDTO fillType = TestabilityFillTypeDTO.MINIMAL;
  private CertificateStatusTypeDTO status;

  public static TestabilityCertificateRequestBuilder create() {
    return new TestabilityCertificateRequestBuilder();
  }

  private TestabilityCertificateRequestBuilder() {

  }

  public TestabilityCertificateRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public TestabilityCertificateRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public TestabilityCertificateRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public TestabilityCertificateRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public TestabilityCertificateRequestBuilder fillType(TestabilityFillTypeDTO fillType) {
    this.fillType = fillType;
    return this;
  }

  public TestabilityCertificateRequestBuilder status(CertificateStatusTypeDTO status) {
    this.status = status;
    return this;
  }

  public TestabilityCertificateRequestBuilder certificateModelId(
      CertificateModelIdDTO certificateModelIdDTO) {
    this.certificateModelId = certificateModelIdDTO;
    return this;
  }

  public TestabilityCertificateRequest build() {
    return TestabilityCertificateRequest.builder()
        .user(user)
        .patient(patient)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(careUnit)
        .certificateModelId(
            certificateModelId
        )
        .fillType(fillType)
        .status(status)
        .build();
  }
}
