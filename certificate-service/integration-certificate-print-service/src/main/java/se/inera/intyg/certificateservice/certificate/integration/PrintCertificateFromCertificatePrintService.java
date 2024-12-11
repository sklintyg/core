package se.inera.intyg.certificateservice.certificate.integration;

import static se.inera.intyg.certificateservice.certificate.integration.constants.ApplicationConstants.APPLICATION_CERTIFICATE_PRINT_SERVICE;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException.GatewayTimeout;
import reactor.core.publisher.Mono;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateRequestDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateResponseDTO;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;

@Service
public class PrintCertificateFromCertificatePrintService {

  private final WebClient webClient;
  private final String scheme;
  private final String baseUrl;
  private final int port;
  private final String endpoint;

  public PrintCertificateFromCertificatePrintService(
      @Qualifier(value = "certificatePrintServiceWebClient") WebClient webClient,
      @Value("${integration.webcert.scheme}") String scheme,
      @Value("${integration.webcert.baseurl}") String baseUrl,
      @Value("${integration.webcert.port}") int port,
      @Value("${integration.webcert.printcertificate.endpoint}") String endpoint) {
    this.webClient = webClient;
    this.scheme = scheme;
    this.baseUrl = baseUrl;
    this.port = port;
    this.endpoint = endpoint;

  }

  @PerformanceLogging(eventAction = "print-certificate-from-certificate-print-service", eventType = EVENT_TYPE_ACCESSED)
  public PrintCertificateResponseDTO print(PrintCertificateRequestDTO request,
      String certificateId) {
    return webClient.post().uri(uriBuilder -> uriBuilder
            .scheme(scheme)
            .host(baseUrl)
            .port(port)
            .path(endpoint)
            .build(certificateId)
        )
        .body(Mono.just(
            request
        ), PrintCertificateRequestDTO.class)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .retrieve()
        .bodyToMono(PrintCertificateResponseDTO.class)
        .share()
        .onErrorMap(
            WebClientRequestException.class,
            ExceptionThrowableFunction.webClientRequest(APPLICATION_CERTIFICATE_PRINT_SERVICE)
        )
        .onErrorMap(
            GatewayTimeout.class,
            ExceptionThrowableFunction.gatewayTimeout(APPLICATION_CERTIFICATE_PRINT_SERVICE)
        )
        .block();
  }
}
