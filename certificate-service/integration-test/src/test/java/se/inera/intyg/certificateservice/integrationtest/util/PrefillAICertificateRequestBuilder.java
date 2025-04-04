package se.inera.intyg.certificateservice.integrationtest.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.PrefillAICertificateRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class PrefillAICertificateRequestBuilder {

  private static final String VERSION = "1.0";
  private static final String TYPE = "fk7210";

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;
  private String text = "Exempel på text som hör till ett intyg";

  private CertificateModelIdDTO certificateModelId = CertificateModelIdDTO.builder()
      .version(VERSION)
      .type(TYPE)
      .build();

  public static PrefillAICertificateRequestBuilder create() {
    return new PrefillAICertificateRequestBuilder();
  }

  private PrefillAICertificateRequestBuilder() {

  }

  public PrefillAICertificateRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public PrefillAICertificateRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public PrefillAICertificateRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public PrefillAICertificateRequestBuilder certificateModelId(
      CertificateModelIdDTO certificateModelIdDTO) {
    this.certificateModelId = certificateModelIdDTO;
    return this;
  }

  public PrefillAICertificateRequestBuilder text(String text) {
    this.text = text;
    return this;
  }

  public PrefillAICertificateRequest build() {
    return PrefillAICertificateRequest.builder()
        .user(user)
        .patient(patient)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .certificateModelId(
            certificateModelId
        )
        .text(text)
        .build();
  }
}
