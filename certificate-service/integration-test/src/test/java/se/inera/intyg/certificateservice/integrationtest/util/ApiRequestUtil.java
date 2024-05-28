package se.inera.intyg.certificateservice.integrationtest.util;

import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.ComplementCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.config.ValidateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageRequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesRequest;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityCertificateRequest;

public class ApiRequestUtil {

  public static CertificateTypeInfoRequestBuilder customCertificateTypeInfoRequest() {
    return CertificateTypeInfoRequestBuilder.create();
  }

  public static GetCertificateTypeInfoRequest defaultCertificateTypeInfoRequest() {
    return CertificateTypeInfoRequestBuilder.create().build();
  }

  public static CreateCertificateRequestBuilder customCreateCertificateRequest(String type,
      String version) {
    return CreateCertificateRequestBuilder.create()
        .certificateModelId(
            CertificateModelIdDTO.builder()
                .type(type)
                .version(version)
                .build()
        );
  }

  public static CreateCertificateRequest defaultCreateCertificateRequest(String type,
      String version) {
    return CreateCertificateRequestBuilder.create()
        .certificateModelId(
            CertificateModelIdDTO.builder()
                .type(type)
                .version(version)
                .build()
        )
        .build();
  }

  public static GetCertificateRequestBuilder customGetCertificateRequest() {
    return GetCertificateRequestBuilder.create();
  }

  public static GetCertificateRequest defaultGetCertificateRequest() {
    return GetCertificateRequestBuilder.create().build();
  }

  public static GetCertificatePdfRequest defaultGetCertificatePdfRequest() {
    return GetCertificatePdfRequestBuilder.create().build();
  }

  public static GetCertificatePdfRequestBuilder customGetCertificatePdfRequest() {
    return GetCertificatePdfRequestBuilder.create();
  }

  public static GetPatientCertificatesRequest defaultGetPatientCertificateRequest() {
    return GetPatientCertificatesRequestBuilder.create().build();
  }

  public static GetPatientCertificatesRequestBuilder customGetPatientCertificatesRequest() {
    return GetPatientCertificatesRequestBuilder.create();
  }

  public static GetUnitCertificatesInfoRequest defaultGetUnitCertificatesInfoRequest() {
    return GetUnitCertificatesInfoRequestBuilder.create().build();
  }

  public static GetUnitCertificatesInfoRequestBuilder customGetUnitCertificatesInfoRequest() {
    return GetUnitCertificatesInfoRequestBuilder.create();
  }

  public static GetUnitCertificatesRequest defaultGetUnitCertificatesRequest() {
    return GetUnitCertificatesRequestBuilder.create().build();
  }

  public static GetUnitCertificatesRequestBuilder customGetUnitCertificatesRequest() {
    return GetUnitCertificatesRequestBuilder.create();
  }

  public static UpdateCertificateRequest defaultUpdateCertificateRequest() {
    return UpdateCertificateRequestBuilder.create().build();
  }

  public static UpdateCertificateRequestBuilder customUpdateCertificateRequest() {
    return UpdateCertificateRequestBuilder.create();
  }

  public static ValidateCertificateRequest defaultValidateCertificateRequest() {
    return ValidateCertificateRequestBuilder.create().build();
  }

  public static ValidateCertificateRequestBuilder customValidateCertificateRequest() {
    return ValidateCertificateRequestBuilder.create();
  }

  public static DeleteCertificateRequest defaultDeleteCertificateRequest() {
    return DeleteCertificateRequestBuilder.create().build();
  }

  public static DeleteCertificateRequestBuilder customDeleteCertificateRequest() {
    return DeleteCertificateRequestBuilder.create();
  }

  public static GetCertificateXmlRequest defaultGetCertificateXmlRequest() {
    return GetCertificateXmlRequestBuilder.create().build();
  }

  public static GetCertificateXmlRequestBuilder customGetCertificateXmlRequest() {
    return GetCertificateXmlRequestBuilder.create();
  }

  public static SignCertificateRequest defaultSignCertificateRequest() {
    return SignCertificateRequestBuilder.create().build();
  }

  public static SignCertificateRequestBuilder customSignCertificateRequest() {
    return SignCertificateRequestBuilder.create();
  }

  public static SendCertificateRequest defaultSendCertificateRequest() {
    return SendCertificateRequestBuilder.create().build();
  }

  public static GetCertificateMessageRequest defaultGetCertificateMessageRequest() {
    return GetCertificateMessageRequestBuilder.create().build();
  }

  public static GetCertificateFromMessageRequest defaultGetCertificateFromMessageRequest() {
    return GetCertificateFromMessageRequestBuilder.create().build();
  }

  public static GetCertificateFromMessageRequestBuilder customGetCertificateFromMessageRequest() {
    return GetCertificateFromMessageRequestBuilder.create();
  }

  public static GetCertificateMessageRequestBuilder customGetCertificateMessageRequest() {
    return GetCertificateMessageRequestBuilder.create();
  }

  public static RevokeCertificateRequest defaultRevokeCertificateRequest() {
    return RevokeCertificateRequestBuilder.create().build();
  }

  public static RevokeCertificateRequestBuilder customRevokeCertificateRequest() {
    return RevokeCertificateRequestBuilder.create();
  }

  public static ComplementCertificateRequest defaultComplementCertificateRequest() {
    return ComplementCertificateRequestBuilder.create().build();
  }

  public static ReplaceCertificateRequest defaultReplaceCertificateRequest() {
    return ReplaceCertificateRequestBuilder.create().build();
  }

  public static ReplaceCertificateRequestBuilder customReplaceCertificateRequest() {
    return ReplaceCertificateRequestBuilder.create();
  }

  public static RenewCertificateRequest defaultRenewCertificateRequest() {
    return RenewCertificateRequestBuilder.create().build();
  }

  public static RenewCertificateRequestBuilder customRenewCertificateRequest() {
    return RenewCertificateRequestBuilder.create();
  }

  public static SendCertificateRequestBuilder customSendCertificateRequest() {
    return SendCertificateRequestBuilder.create();
  }

  public static TestabilityCertificateRequestBuilder customTestabilityCertificateRequest(
      String type,
      String version) {
    return customTestabilityCertificateRequest(type, version, CertificateStatusTypeDTO.UNSIGNED);
  }

  public static TestabilityCertificateRequestBuilder customTestabilityCertificateRequest(
      String type,
      String version,
      CertificateStatusTypeDTO status) {
    return TestabilityCertificateRequestBuilder.create()
        .certificateModelId(
            CertificateModelIdDTO.builder()
                .type(type)
                .version(version)
                .build()
        )
        .status(status);
  }

  public static TestabilityCertificateRequest defaultTestablilityCertificateRequest(
      String type, String version) {
    return defaultTestablilityCertificateRequest(type, version, CertificateStatusTypeDTO.UNSIGNED);
  }

  public static TestabilityCertificateRequest defaultTestablilityCertificateRequest(
      String type, String version, CertificateStatusTypeDTO status) {
    return TestabilityCertificateRequestBuilder.create()
        .certificateModelId(
            CertificateModelIdDTO.builder()
                .type(type)
                .version(version)
                .build()
        )
        .status(status)
        .build();
  }

}

