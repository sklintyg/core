package se.inera.intyg.certificateservice.integrationtest.util;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesInternalWithQARequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesInternalWithQAResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalMetadataResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageInternalResponse;

@Slf4j
@RequiredArgsConstructor
public class InternalApiUtil {

  private final TestRestTemplate restTemplate;
  private final int port;

  public ResponseEntity<GetCertificateInternalXmlResponse> getCertificateXml(String certificateId) {
    final var requestUrl = "http://localhost:%s/internalapi/certificate/%s/xml".formatted(
        port,
        certificateId
    );

    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.POST,
        new HttpEntity<>(null, headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<GetCertificateInternalMetadataResponse> getCertificateMetadata(
      String certificateId) {
    final var requestUrl = "http://localhost:%s/internalapi/certificate/%s/metadata".formatted(
        port,
        certificateId
    );

    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.GET,
        new HttpEntity<>(null, headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<GetCertificateInternalResponse> getCertificate(
      String certificateId) {
    final var requestUrl = "http://localhost:%s/internalapi/certificate/%s".formatted(
        port,
        certificateId
    );

    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.POST,
        new HttpEntity<>(null, headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<CertificateExistsResponse> certificateExists(
      String certificateId) {
    final var requestUrl = "http://localhost:%s/internalapi/certificate/%s/exists"
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

  public ResponseEntity<GetCertificateMessageInternalResponse> getMessages(String certificateId) {
    final var requestUrl = "http://localhost:%s/internalapi/message/%s".formatted(
        port,
        certificateId
    );

    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return this.restTemplate.exchange(
        requestUrl,
        HttpMethod.POST,
        new HttpEntity<>(null, headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public ResponseEntity<CertificatesInternalWithQAResponse> getPatientCertificatesWithQA(
      CertificatesInternalWithQARequest request) {
    final var requestUrl = "http://localhost:%s/internalapi/patient/certificates/qa".formatted(
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

  public void reset() {
    // Nothing to reset right now...
  }
}
