package se.inera.intyg.certificateservice.integrationtest.common.util;

import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesWithQAInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ComplementCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateDraftFromCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewExternalCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.config.ValidateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.DeleteAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.DeleteMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.SaveAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.SaveMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.SendAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageRequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsRequest;
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

  public static GetCertificateEventsRequestBuilder customGetCertificateEventsRequest() {
    return GetCertificateEventsRequestBuilder.create();
  }

  public static GetCertificateRequest defaultGetCertificateRequest() {
    return GetCertificateRequestBuilder.create().build();
  }

  public static GetCertificateEventsRequest defaultGetCertificateEventsRequest() {
    return GetCertificateEventsRequestBuilder.create().build();
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

  public static GetUnitMessagesRequest defaultGetUnitMessagesRequest() {
    return GetUnitMessagesRequestBuilder.create().build();
  }

  public static GetUnitMessagesRequestBuilder customGetUnitMessagesRequest() {
    return GetUnitMessagesRequestBuilder.create();
  }

  public static AnswerComplementRequest defaultAnswerComplementRequest() {
    return AnswerComplementRequestBuilder.create().build();
  }

  public static AnswerComplementRequestBuilder customAnswerComplementRequest() {
    return AnswerComplementRequestBuilder.create();
  }

  public static GetCertificateFromMessageRequest defaultGetCertificateFromMessageRequest() {
    return GetCertificateFromMessageRequestBuilder.create().build();
  }

  public static CreateMessageRequest defaultCreateMessageRequest() {
    return CreateMessageRequestBuilder.create().build();
  }

  public static CreateMessageRequestBuilder customCreateMessageRequest() {
    return CreateMessageRequestBuilder.create();
  }

  public static SaveMessageRequest defaultSaveMessageRequest() {
    return SaveMessageRequestBuilder.create().build();
  }

  public static SaveMessageRequestBuilder customSaveMessageRequest() {
    return SaveMessageRequestBuilder.create();
  }

  public static SendMessageRequest defaultSendMessageRequest() {
    return SendMessageRequestBuilder.create().build();
  }

  public static SendMessageRequestBuilder customSendMessageRequest() {
    return SendMessageRequestBuilder.create();
  }

  public static DeleteMessageRequest defaultDeleteMessageRequest() {
    return DeleteMessageRequestBuilder.create().build();
  }

  public static SaveAnswerRequest defaultSaveAnswerRequest() {
    return SaveAnswerRequestBuilder.create().build();
  }

  public static SendAnswerRequest defaultSendAnswerRequest() {
    return SendAnswerRequestBuilder.create().build();
  }

  public static DeleteAnswerRequest defaultDeleteAnswerRequest() {
    return DeleteAnswerRequestBuilder.create().build();
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

  public static RenewExternalCertificateRequest defaultRenewExternalCertificateRequest() {
    return RenewExternalCertificateRequestBuilder.create().build();
  }

  public static RenewExternalCertificateRequestBuilder customRenewExternalCertificateRequest() {
    return RenewExternalCertificateRequestBuilder.create();
  }

  public static CreateDraftFromCertificateRequest defaultCreateDraftFromCertificateRequest() {
    return CreateDraftFromCertificateRequestBuilder.create().build();
  }

  public static GetCertificateCandidateRequest defaultGetCertificateCandidateRequest() {
    return GetCertificateCandidateRequestBuilder.create().build();
  }

  public static UpdateWithCertificateCandidateRequest defaultUpdateWithCertificateCandidateRequest() {
    return UpdateWithCertificateCandidateRequestBuilder.create().build();
  }

  public static CreateDraftFromCertificateRequestBuilder customCreateDraftFromCertificateRequest() {
    return CreateDraftFromCertificateRequestBuilder.create();
  }

  public static HandleMessageRequest defaultHandleMessageRequest() {
    return HandleMessageRequestBuilder.create().build();
  }

  public static HandleMessageRequestBuilder customHandleMessageRequest() {
    return HandleMessageRequestBuilder.create();
  }

  public static RenewCertificateRequestBuilder customRenewCertificateRequest() {
    return RenewCertificateRequestBuilder.create();
  }

  public static SendCertificateRequestBuilder customSendCertificateRequest() {
    return SendCertificateRequestBuilder.create();
  }

  public static CertificateReadyForSignRequestBuilder customReadyForSignCertificateRequest() {
    return CertificateReadyForSignRequestBuilder.create();
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

  public static ForwardCertificateRequest defaultForwardCertificateRequest() {
    return ForwardCertificateRequestBuilder.create().build();
  }

  public static ForwardCertificateRequestBuilder customForwardCertificateRequest() {
    return ForwardCertificateRequestBuilder.create();
  }

  public static ForwardCertificateRequest defaultForwardCertificateMessageRequest() {
    return ForwardCertificateMessageRequestBuilder.create().build();
  }

  public static ForwardCertificateMessageRequestBuilder customForwardCertificateMessageRequest() {
    return ForwardCertificateMessageRequestBuilder.create();
  }

  public static CertificatesWithQAInternalRequest defaultGetCertificatesInternalWithQARequest() {
    return GetCertificatesInternalWithQARequestBuilder.create().build();
  }

  public static GetCertificatesInternalWithQARequestBuilder customGetCertificatesInternalWithQARequest() {
    return GetCertificatesInternalWithQARequestBuilder.create();
  }

  public static UnitStatisticsRequest defaultUnitStatisticsRequest() {
    return UnitStatisticsRequestBuilder.create().build();
  }

  public static UnitStatisticsRequestBuilder customUnitStatisticsRequest() {
    return UnitStatisticsRequestBuilder.create();
  }

  public static GetSickLeaveCertificatesInternalRequest defaultGetSickLeaveCertificatesInternalRequest() {
    return GetSickLeaveCertificatesInternalRequestBuilder.create().build();
  }
}