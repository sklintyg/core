package se.inera.intyg.certificateservice.integrationtest.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.CertificateAIPrefillRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class PrefillCertificateRequestBuilder {

  private static final String VERSION = "1.0";
  private static final String TYPE = "fk7210";

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private String prefillData;

  private CertificateModelIdDTO certificateModelId = CertificateModelIdDTO.builder()
      .version(VERSION)
      .type(TYPE)
      .build();

  public static PrefillCertificateRequestBuilder create() {
    return new PrefillCertificateRequestBuilder();
  }

  private PrefillCertificateRequestBuilder() {

  }

  public PrefillCertificateRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public PrefillCertificateRequestBuilder prefillData(String prefillData) {
    this.prefillData = prefillData;
    return this;
  }

  public PrefillCertificateRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public PrefillCertificateRequestBuilder certificateModelId(
      CertificateModelIdDTO certificateModelIdDTO) {
    this.certificateModelId = certificateModelIdDTO;
    return this;
  }

  public CertificateAIPrefillRequest build() {
    return CertificateAIPrefillRequest.builder()
        .user(user)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .preFillData(prefillData)
        .build();
  }
}
