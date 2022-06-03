package se.inera.intyg.cts.infrastructure.integration.tellustalk;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.inera.intyg.cts.infrastructure.integration.SendEmail;
import se.inera.intyg.cts.infrastructure.integration.tellustalk.dto.EmailRequestDTO;
import se.inera.intyg.cts.infrastructure.integration.tellustalk.dto.TellusTalkResponseDTO;

@Service
public class SendEmailWithTellusTalk implements SendEmail {

  @Value("${sms.originator.text}")
  private String emailFromName;

  private final WebClient webClient;
  private final String scheme;
  private final String baseUrl;
  private final int port;
  private final String tellustalkSendEndpoint;

  private final String username;
  private final String password;

  public SendEmailWithTellusTalk(
      @Qualifier(value = "tellusTalkWebClient") WebClient webClient,
      @Value("${integration.tellustalk.scheme}") String scheme,
      @Value("${integration.tellustalk.baseurl}") String baseUrl,
      @Value("${integration.tellustalk.port}") int port,
      @Value("${integration.tellustalk.send.endpoint}") String tellustalkSendEndpoint,
      @Value("${integration.tellustalk.username}") String username,
      @Value("${integration.tellustalk.password}") String password) {
    this.webClient = webClient;
    this.scheme = scheme;
    this.baseUrl = baseUrl;
    this.port = port;
    this.tellustalkSendEndpoint = tellustalkSendEndpoint;
    this.username = username;
    this.password = password;
  }

  @Override
  public TellusTalkResponseDTO sendEmail(String emailAddress, String message, String subject) {
    EmailRequestDTO emailRequestDTO = new EmailRequestDTO(emailAddress, message, subject, emailFromName);

    return webClient.post().uri(uriBuilder -> uriBuilder
            .scheme(scheme)
            .host(baseUrl)
            .port(port)
            .path(tellustalkSendEndpoint)
            .build())
        .body(Mono.just(emailRequestDTO), EmailRequestDTO.class)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .headers(headers -> headers.setBasicAuth(username, password))
        .retrieve()
        .bodyToMono(TellusTalkResponseDTO.class)
        .share()
        .block();
  }
}
