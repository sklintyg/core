package se.inera.intyg.certificateservice.integrationtest.util;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;

@RequiredArgsConstructor
public class ApiUtil {

  private final TestRestTemplate restTemplate;
  private final int port;

  public ResponseEntity<List<CertificateTypeInfoDTO>> certificateTypeInfo(
      GetCertificateTypeInfoRequest request) {
    final var requestUrl = "http://localhost:" + port + "/api/certificatetypeinfo";
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
}
