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
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalMetadataResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;

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
        HttpMethod.POST,
        new HttpEntity<>(null, headers),
        new ParameterizedTypeReference<>() {
        },
        Collections.emptyMap()
    );
  }

  public void reset() {
    // Nothing to reset right now...
  }
}
