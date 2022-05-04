package se.inera.intyg.cts.infrastructure.integration.tellustalk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.inera.intyg.cts.infrastructure.integration.SendSMS;
import se.inera.intyg.cts.infrastructure.integration.tellustalk.dto.SMSRequestDTO;
import se.inera.intyg.cts.infrastructure.integration.tellustalk.dto.SMSResponseDTO;

@Service
public class SendSMSImpl implements SendSMS {

  @Value("${sms.originator.text}")
  private String smsOriginatorText;

  private final WebClient webClient;
  private final String scheme;
  private final String baseUrl;
  private final int port;
  private final String tellustalkSendEndpoint;

  private final String username;
  private final String password;


  public SendSMSImpl(WebClient webClient,
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
  public SMSResponseDTO sendSMS(String phonenumber, String message) {
    SMSRequestDTO smsRequestDTO = new SMSRequestDTO(phonenumber, message, smsOriginatorText);

    return webClient.post().uri(uriBuilder -> uriBuilder
            .scheme(scheme)
            .host(baseUrl)
            .port(port)
            .path(tellustalkSendEndpoint)
            .build())
            .body(Mono.just(smsRequestDTO), SMSRequestDTO.class)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .headers(headers -> headers.setBasicAuth(username, password))
        .retrieve()
        .bodyToMono(SMSResponseDTO.class)
        .share()
        .block();
  }
}
