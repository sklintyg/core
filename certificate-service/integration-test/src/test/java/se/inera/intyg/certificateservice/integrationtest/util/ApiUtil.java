package se.inera.intyg.certificateservice.integrationtest.util;

import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;

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
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ValidateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.config.ValidateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateTypeVersionResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesRequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesResponse;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoResponse;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesResponse;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityResetCertificateRequest;

@Slf4j
@RequiredArgsConstructor
public class ApiUtil {

  private final TestRestTemplate restTemplate;
  private final int port;

  private List<String> certificateIds = new ArrayList<>();

  public ResponseEntity<GetCertificateTypeInfoResponse> certificateTypeInfo(
      GetCertificateTypeInfoRequest request) {
    final var requestUrl = "http://localhost:%s/api/certificatetypeinfo".formatted(port);
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

  public ResponseEntity<CreateCertificateResponse> createCertificate(
      CreateCertificateRequest request) {
    final var requestUrl = "http://localhost:%s/api/certificate".formatted(port);
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    final var response = this.restTemplate.<CreateCertificateResponse>exchange(
        requestUrl,
        HttpMethod.POST,
        new HttpEntity<>(request, headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );

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

  public ResponseEntity<GetCitizenCertificateResponse> getCitizenCertificate(
      GetCitizenCertificateRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/citizen/certificate/%s".formatted(
        port,
        certificateId
    );

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

  public ResponseEntity<GetPatientCertificatesResponse> getPatientCertificates(
      GetPatientCertificatesRequest request) {
    final var requestUrl = "http://localhost:%s/api/patient/certificates".formatted(
        port
    );

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

  public ResponseEntity<GetUnitCertificatesInfoResponse> getUnitCertificatesInfo(
      GetUnitCertificatesInfoRequest request) {
    final var requestUrl = "http://localhost:%s/api/unit/certificates/info".formatted(
        port
    );

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

  public ResponseEntity<GetUnitCertificatesResponse> getUnitCertificates(
      GetUnitCertificatesRequest request) {
    final var requestUrl = "http://localhost:%s/api/unit/certificates".formatted(
        port
    );

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

  public ResponseEntity<GetCertificateXmlResponse> getCertificateXml(
      GetCertificateXmlRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/xml".formatted(
        port,
        certificateId
    );

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

  public ResponseEntity<SignCertificateResponse> signCertificate(SignCertificateRequest request,
      String certificateId, Long version) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/sign/%s".formatted(
        port,
        certificateId,
        version
    );

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

  public ResponseEntity<SendCertificateResponse> sendCertificate(SendCertificateRequest request,
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/send".formatted(
        port,
        certificateId
    );

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

  public ResponseEntity<RevokeCertificateResponse> revokeCertificate(
      RevokeCertificateRequest request,
      String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/revoke".formatted(
        port,
        certificateId
    );

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

  public ResponseEntity<GetCertificatePdfResponse> getCertificatePdf(
      GetCertificatePdfRequest request, String certificateId) {
    final var requestUrl = "http://localhost:%s/api/certificate/%s/pdf".formatted(
        port,
        certificateId
    );

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

  public void reset() {
    if (certificateIds.isEmpty()) {
      return;
    }

    final var requestUrl = "http://localhost:%s/testability/certificate".formatted(port);
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    final var request = TestabilityResetCertificateRequest.builder()
        .certificateIds(certificateIds)
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
