package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateModelIdDTO.FK7804_CERTIFICATE_MODEL_ID_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REF;

import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PrefillXmlDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewExternalCertificateRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;

public class RenewExternalCertificateRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;
  private CertificateModelIdDTO certificateModelId = FK7804_CERTIFICATE_MODEL_ID_DTO;
  private CertificateStatusTypeDTO status = CertificateStatusTypeDTO.SIGNED;
  private UnitDTO issuingUnit = ALFA_MEDICINCENTRUM_DTO;
  private PrefillXmlDTO prefillXml = new PrefillXmlDTO(
      new Xml(
          """
              <ns3:forifyllnad xmlns:ns3="urn:riv:clinicalprocess:healthcond:certificate:3.3"></ns3:forifyllnad>"""
      ).base64()
  );

  public static RenewExternalCertificateRequestBuilder create() {
    return new RenewExternalCertificateRequestBuilder();
  }

  private RenewExternalCertificateRequestBuilder() {

  }

  public RenewExternalCertificateRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public RenewExternalCertificateRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public RenewExternalCertificateRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public RenewExternalCertificateRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public RenewExternalCertificateRequestBuilder certificateModelId(
      CertificateModelIdDTO certificateModelId) {
    this.certificateModelId = certificateModelId;
    return this;
  }

  public RenewExternalCertificateRequestBuilder status(CertificateStatusTypeDTO status) {
    this.status = status;
    return this;
  }

  public RenewExternalCertificateRequestBuilder issuingUnit(UnitDTO issuingUnit) {
    this.issuingUnit = issuingUnit;
    return this;
  }

  public RenewExternalCertificateRequestBuilder prefillXml(PrefillXmlDTO prefillXml) {
    this.prefillXml = prefillXml;
    return this;
  }

  public RenewExternalCertificateRequest build() {
    return RenewExternalCertificateRequest.builder()
        .user(user)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(careUnit)
        .patient(patient)
        .externalReference(EXTERNAL_REF)
        .certificateModelId(certificateModelId)
        .issuingUnit(issuingUnit)
        .status(status)
        .prefillXml(prefillXml)
        .build();
  }
}