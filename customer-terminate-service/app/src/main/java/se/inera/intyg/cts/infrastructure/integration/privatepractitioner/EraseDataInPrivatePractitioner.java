package se.inera.intyg.cts.infrastructure.integration.privatepractitioner;

import static se.inera.intyg.cts.logging.MdcLogConstants.EVENT_TYPE_DELETION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import se.inera.intyg.cts.domain.model.ServiceId;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.service.EraseDataInService;
import se.inera.intyg.cts.domain.service.EraseException;
import se.inera.intyg.cts.logging.PerformanceLogging;

@Service
public class EraseDataInPrivatePractitioner implements EraseDataInService {

  private final static Logger LOG = LoggerFactory.getLogger(EraseDataInPrivatePractitioner.class);
  private final static ServiceId SERVICE_ID = new ServiceId("privatlakarportal");

  private final WebClient webClient;
  private final String scheme;
  private final String baseUrl;
  private final String port;
  private final String eraseEndpoint;

  public EraseDataInPrivatePractitioner(
      @Qualifier(value = "privatePractitionerWebClient") WebClient webClient,
      @Value("${integration.privatepractitioner.scheme}") String scheme,
      @Value("${integration.privatepractitioner.baseurl}") String baseUrl,
      @Value("${integration.privatepractitioner.port}") String port,
      @Value("${integration.privatepractitioner.erase.endpoint}") String eraseEndpoint) {
    this.webClient = webClient;
    this.scheme = scheme;
    this.baseUrl = baseUrl;
    this.port = port;
    this.eraseEndpoint = eraseEndpoint;
  }

  @Override
  @PerformanceLogging(eventAction = "erase-in-privatlakarportal", eventType = EVENT_TYPE_DELETION)
  public void erase(Termination termination) throws EraseException {
    try {
      webClient.delete().uri(uriBuilder -> uriBuilder
              .scheme(scheme)
              .host(baseUrl)
              .port(port)
              .path(eraseEndpoint + "/{careProvider}")
              .build(termination.careProvider().hsaId().id())
          )
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .retrieve()
          .toEntity(String.class)
          .block();
    } catch (Exception ex) {
      LOG.error("Error calling privatlakarportal to delete care provider.", ex);
      throw new EraseException(
          String.format("Erase care provider failed with message '%s'", ex.getMessage())
      );
    }
  }

  @Override
  public ServiceId serviceId() {
    return SERVICE_ID;
  }
}
