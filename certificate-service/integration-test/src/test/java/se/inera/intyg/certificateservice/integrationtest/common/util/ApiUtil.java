package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateReadyForSignRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateReadyForSignResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ComplementCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ComplementCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateDraftFromCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateDraftFromCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateCandidateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewExternalCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ValidateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.config.ValidateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateExternalTypeVersionResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateTypeVersionResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.CitizenCertificateExistsResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateListRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateListResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.DeleteAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.DeleteAnswerResponse;
import se.inera.intyg.certificateservice.application.message.dto.DeleteMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.MessageExistsResponse;
import se.inera.intyg.certificateservice.application.message.dto.SaveAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.SaveAnswerResponse;
import se.inera.intyg.certificateservice.application.message.dto.SaveMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.SaveMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.SendAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.SendAnswerResponse;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageResponse;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesRequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesResponse;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoResponse;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesResponse;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesResponse;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsRequest;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsResponse;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityResetCertificateRequest;

@Slf4j
@RequiredArgsConstructor
public class ApiUtil {

  private final TestRestTemplate restTemplate;
  private final int port;

  private final List<String> certificateIds = new ArrayList<>();
  private final List<String> messageIds = new ArrayList<>();


  public ResponseEntity<GetCertificateTypeInfoResponse> certificateTypeInfo(
      GetCertificateTypeInfoRequest request) {
    final var requestUrl = "http://localhost:%s/api/certificatetypeinfo".formatted(port);

    return sendRequest(request, requestUrl, GetCertificateTypeInfoResponse.class);
  }

  public ResponseEntity<CreateCertificateResponse> createCertificate(
      CreateCertificateRequest request) {
    final var requestUrl = "http://localhost:%s/api/certificate".formatted(port);

    final var response = sendRequest(request, requestUrl, CreateCertificateResponse.class);

    if (certificateId(response.getBody()) != null) {
      certificateIds.add(certificateId(response.getBody()));
    }

    return response;
  }

  public ResponseEntity<GetCertificateResponse> getCertificate(
      GetCertificateRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, GetCertificateResponse.class);
  }

  public ResponseEntity<GetCertificateEventsResponse> getCertificateEvents(
      GetCertificateEventsRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/events".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, GetCertificateEventsResponse.class);
  }

  public ResponseEntity<GetCitizenCertificateResponse> getCitizenCertificate(
      GetCitizenCertificateRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/citizen/certificate/%s".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, GetCitizenCertificateResponse.class);
  }

  public ResponseEntity<GetCitizenCertificateListResponse> getCitizenCertificateList(
      GetCitizenCertificateListRequest request) {
    final var requestUrl = "http://localhost:%s/api/citizen/certificate".formatted(port);

    return sendRequest(request, requestUrl, GetCitizenCertificateListResponse.class);

  }

  public ResponseEntity<PrintCitizenCertificateResponse> printCitizenCertificate(
      PrintCitizenCertificateRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/citizen/certificate/%s/print".formatted(port,
        certificateId);

    return sendRequest(request, requestUrl, PrintCitizenCertificateResponse.class);
  }

  public ResponseEntity<SendCitizenCertificateResponse> sendCitizenCertificate(
      SendCitizenCertificateRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/citizen/certificate/%s/send".formatted(port,
        certificateId);

    return sendRequest(request, requestUrl, SendCitizenCertificateResponse.class);
  }

  public ResponseEntity<GetPatientCertificatesResponse> getPatientCertificates(
      GetPatientCertificatesRequest request) {
    final var requestUrl = "http://localhost:%s/api/patient/certificates".formatted(
        port
    );

    return sendRequest(request, requestUrl, GetPatientCertificatesResponse.class);
  }

  public ResponseEntity<GetUnitCertificatesInfoResponse> getUnitCertificatesInfo(
      GetUnitCertificatesInfoRequest request) {
    final var requestUrl = "http://localhost:%s/api/unit/certificates/info".formatted(
        port
    );

    return sendRequest(request, requestUrl, GetUnitCertificatesInfoResponse.class);
  }

  public ResponseEntity<GetUnitCertificatesResponse> getUnitCertificates(
      GetUnitCertificatesRequest request) {
    final var requestUrl = "http://localhost:%s/api/unit/certificates".formatted(
        port
    );

    return sendRequest(request, requestUrl, GetUnitCertificatesResponse.class);
  }

  public ResponseEntity<GetCertificateResponse> deleteCertificate(
      DeleteCertificateRequest request, String certificateId, long version) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/%s".formatted(
        port,
        certificateId,
        Long.toString(version)
    );

    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.DELETE,
        new HttpEntity<>(request, headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<GetLatestCertificateTypeVersionResponse> findLatestCertificateTypeVersion(
      String type) {
    final var requestUrl = "http://localhost:%s/api/certificatetypeinfo/%s/exists"
        .formatted(port, type);
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.GET,
        new HttpEntity<>(headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<GetLatestCertificateExternalTypeVersionResponse> findLatestCertificateExternalTypeVersion(
      String codeSystem, String code) {
    final var requestUrl = "http://localhost:%s/api/certificatetypeinfo/%s/%s/exists"
        .formatted(port, codeSystem, code);
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.GET,
        new HttpEntity<>(headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<CitizenCertificateExistsResponse> findExistingCitizenCertificate(
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/citizen/certificate/%s/exists"
        .formatted(port, certificateId);
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.GET,
        new HttpEntity<>(headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<CertificateExistsResponse> certificateExists(
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/exists"
        .formatted(port, certificateId);
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.GET,
        new HttpEntity<>(headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<ForwardCertificateResponse> forwardCertificate(
      String certificateId, ForwardCertificateRequest request) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/forward"
        .formatted(port, certificateId);
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.POST,
        new HttpEntity<>(request, headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<MessageExistsResponse> messageExists(
      String messageId) {
    final var requestUrl = "http://localhost:%s/api/message/%s/exists"
        .formatted(port, messageId);
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.GET,
        new HttpEntity<>(headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<GetCertificateFromMessageResponse> certificateFromMessage(
      GetCertificateFromMessageRequest request,
      String messageId) {
    final var requestUrl = "http://localhost:%s/api/message/%s/certificate"
        .formatted(port, messageId);

    return sendRequest(request, requestUrl, GetCertificateFromMessageResponse.class);
  }

  public ResponseEntity<HandleMessageResponse> handleMessage(
      HandleMessageRequest request, String messageId) {
    final var requestUrl = "http://localhost:%s/api/message/%s/handle"
        .formatted(port, messageId);

    return sendRequest(request, requestUrl, HandleMessageResponse.class);
  }

  public ResponseEntity<UpdateCertificateResponse> updateCertificate(
      UpdateCertificateRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s".formatted(
        port,
        certificateId
    );

    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.PUT,
        new HttpEntity<>(request, headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<String> updateCertificateWithConcurrentError(
      UpdateCertificateRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s".formatted(
        port,
        certificateId
    );

    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.PUT,
        new HttpEntity<>(request, headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<ValidateCertificateResponse> validateCertificate(
      ValidateCertificateRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/validate".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, ValidateCertificateResponse.class);
  }

  public ResponseEntity<GetCertificateXmlResponse> getCertificateXml(
      GetCertificateXmlRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/xml".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, GetCertificateXmlResponse.class);
  }

  public ResponseEntity<AnswerComplementResponse> answerComplement(
      AnswerComplementRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/answerComplement".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, AnswerComplementResponse.class);
  }

  public ResponseEntity<CreateMessageResponse> createMessage(CreateMessageRequest request,
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/message/%s/create".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, CreateMessageResponse.class);
  }

  public ResponseEntity<SaveMessageResponse> saveMessage(SaveMessageRequest request,
      String messageId) {
    final var requestUrl = "http://localhost:%s/api/message/%s/save".formatted(
        port,
        messageId
    );

    return sendRequest(request, requestUrl, SaveMessageResponse.class);
  }

  public ResponseEntity<SendMessageResponse> sendMessage(SendMessageRequest request,
      String messageId) {
    final var requestUrl = "http://localhost:%s/api/message/%s/send".formatted(
        port,
        messageId
    );

    return sendRequest(request, requestUrl, SendMessageResponse.class);
  }

  public ResponseEntity<Void> deleteMessage(DeleteMessageRequest request, String messageId) {
    final var requestUrl = "http://localhost:%s/api/message/%s/send".formatted(
        port,
        messageId
    );

    return sendRequest(request, requestUrl, Void.class);
  }

  public ResponseEntity<SaveAnswerResponse> saveAnswer(
      SaveAnswerRequest request, String messageId) {
    final var requestUrl = "http://localhost:%s/api/message/%s/saveanswer".formatted(
        port,
        messageId
    );

    return sendRequest(request, requestUrl, SaveAnswerResponse.class);
  }

  public ResponseEntity<SendAnswerResponse> sendAnswer(
      SendAnswerRequest request, String messageId) {
    final var requestUrl = "http://localhost:%s/api/message/%s/sendanswer".formatted(
        port,
        messageId
    );

    return sendRequest(request, requestUrl, SendAnswerResponse.class);
  }

  public ResponseEntity<DeleteAnswerResponse> deleteAnswer(
      DeleteAnswerRequest request, String messageId) {
    final var requestUrl = "http://localhost:%s/api/message/%s/deleteanswer".formatted(
        port,
        messageId
    );

    return sendRequest(request, requestUrl, DeleteAnswerResponse.class);
  }

  public ResponseEntity<SignCertificateResponse> signCertificate(SignCertificateRequest request,
      String certificateId, Long version) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/sign/%s".formatted(
        port,
        certificateId,
        version
    );

    return sendRequest(request, requestUrl, SignCertificateResponse.class);
  }

  public ResponseEntity<SendCertificateResponse> sendCertificate(SendCertificateRequest request,
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/send".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, SendCertificateResponse.class);
  }

  public ResponseEntity<CertificateReadyForSignResponse> readyForSignCertificate(
      CertificateReadyForSignRequest request,
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/readyForSign".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, CertificateReadyForSignResponse.class);
  }

  public ResponseEntity<Void> receiveMessage(IncomingMessageRequest request) {
    final var requestUrl = "http://localhost:%s/api/message".formatted(port);

    messageIds.add(request.getId());

    return sendRequest(request, requestUrl, Void.class);
  }

  public ResponseEntity<GetCertificateMessageResponse> getMessagesForCertificate(
      GetCertificateMessageRequest request,
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/message/%s".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, GetCertificateMessageResponse.class);
  }

  public ResponseEntity<GetUnitMessagesResponse> getMessagesForUnit(
      GetUnitMessagesRequest request) {
    final var requestUrl = "http://localhost:%s/api/unit/messages".formatted(
        port
    );

    return sendRequest(request, requestUrl, GetUnitMessagesResponse.class);
  }

  public ResponseEntity<RevokeCertificateResponse> revokeCertificate(
      RevokeCertificateRequest request,
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/revoke".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, RevokeCertificateResponse.class);
  }

  public ResponseEntity<ComplementCertificateResponse> complementCertificate(
      ComplementCertificateRequest request,
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/complement".formatted(
        port,
        certificateId
    );

    final var response = sendRequest(request, requestUrl, ComplementCertificateResponse.class);

    if (certificateId(response.getBody()) != null) {
      certificateIds.add(certificateId(response.getBody()));
    }

    return response;
  }

  public ResponseEntity<ReplaceCertificateResponse> replaceCertificate(
      ReplaceCertificateRequest request,
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/replace".formatted(
        port,
        certificateId
    );

    final var response = sendRequest(request, requestUrl, ReplaceCertificateResponse.class);

    if (certificateId(response.getBody()) != null) {
      certificateIds.add(certificateId(response.getBody()));
    }

    return response;
  }

  public ResponseEntity<RenewCertificateResponse> renewCertificate(
      RenewCertificateRequest request,
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/renew".formatted(
        port,
        certificateId
    );

    final var response = sendRequest(request, requestUrl, RenewCertificateResponse.class);

    if (certificateId(response.getBody()) != null) {
      certificateIds.add(certificateId(response.getBody()));
    }

    return response;
  }

  public ResponseEntity<RenewCertificateResponse> renewExternalCertificate(
      RenewExternalCertificateRequest request,
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/renew/external".formatted(
        port,
        certificateId
    );

    final var response = sendRequest(request, requestUrl, RenewCertificateResponse.class);

    if (certificateId(response.getBody()) != null) {
      certificateIds.add(certificateId);
      certificateIds.add(certificateId(response.getBody()));
    }

    return response;
  }

  public ResponseEntity<CreateDraftFromCertificateResponse> createDraftFromCertificate(
      CreateDraftFromCertificateRequest request,
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/draft".formatted(
        port,
        certificateId
    );

    final var response = sendRequest(request, requestUrl,
        CreateDraftFromCertificateResponse.class);

    if (certificateId(response.getBody()) != null) {
      certificateIds.add(certificateId);
      certificateIds.add(certificateId(response.getBody()));
    }

    return response;
  }

  public ResponseEntity<GetCertificateCandidateResponse> getCertificateCandidate(
      GetCertificateCandidateRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/candidate".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, GetCertificateCandidateResponse.class);
  }

  public ResponseEntity<UpdateWithCertificateCandidateResponse> updateWithCertificateCandidate(
      UpdateWithCertificateCandidateRequest request, String certificateId, String candidateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/candidate/%s"
        .formatted(port, certificateId, candidateId);

    return sendRequest(request, requestUrl, UpdateWithCertificateCandidateResponse.class);
  }

  public ResponseEntity<GetCertificatePdfResponse> getCertificatePdf(
      GetCertificatePdfRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/pdf".formatted(
        port,
        certificateId
    );

    return sendRequest(request, requestUrl, GetCertificatePdfResponse.class);
  }

  public ResponseEntity<UnitStatisticsResponse> getUnitStatistics(
      UnitStatisticsRequest request) {
    final var requestUrl = "http://localhost:%s/api/unit/certificates/statistics".formatted(
        port
    );

    return sendRequest(request, requestUrl, UnitStatisticsResponse.class);
  }

  private <T, R> ResponseEntity<T> sendRequest(R request, String requestUrl, Class<T> clazz) {
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.POST,
        new HttpEntity<>(request, headers),
        clazz,
        Collections.emptyMap()
    );
  }

  public void reset() {
    if (certificateIds.isEmpty() && messageIds.isEmpty()) {
      return;
    }

    final var requestUrl = "http://localhost:%s/testability/certificate".formatted(port);
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    final var request = TestabilityResetCertificateRequest.builder()
        .certificateIds(certificateIds)
        .messageIds(messageIds)
        .build();
    final var response = this.restTemplate.<Void>exchange(
        requestUrl,
        HttpMethod.DELETE,
        new HttpEntity<>(request, headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
    if (response.getStatusCode() != HttpStatus.OK) {
      log.error(
          "Could not reset testability with request '%s'! StatusCode: '%s'".formatted(
              request,
              response.getStatusCode()
          )
      );
    }
  }
}