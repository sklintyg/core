package se.inera.intyg.certificateservice.integrationtest.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityCertificateRequest;

public class TestabilityCertificateRequestBuilder {

  private static final String VERSION = "1.0";
  private static final String TYPE = "fk7211";

  private CertificateModelIdDTO certificateModelId = CertificateModelIdDTO.builder()
      .version(VERSION)
      .type(TYPE)
      .build();

  public static TestabilityCertificateRequestBuilder create() {
    return new TestabilityCertificateRequestBuilder();
  }

  private TestabilityCertificateRequestBuilder() {

  }

  public TestabilityCertificateRequestBuilder certificateModelId(
      CertificateModelIdDTO certificateModelIdDTO) {
    this.certificateModelId = certificateModelIdDTO;
    return this;
  }

  public TestabilityCertificateRequest build() {
    return TestabilityCertificateRequest.builder()
        .user(AJLA_DOCTOR_DTO)
        .patient(ATHENA_REACT_ANDERSSON_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .certificateModelId(
            certificateModelId
        )
        .build();
  }
}
