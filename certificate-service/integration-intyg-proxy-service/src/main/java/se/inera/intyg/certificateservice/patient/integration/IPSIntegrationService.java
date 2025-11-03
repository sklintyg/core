package se.inera.intyg.certificateservice.patient.integration;

import static se.inera.intyg.certificateservice.logging.MdcHelper.LOG_SESSION_ID_HEADER;
import static se.inera.intyg.certificateservice.logging.MdcHelper.LOG_TRACE_ID_HEADER;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.SESSION_ID_KEY;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.TRACE_ID_KEY;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;
import se.inera.intyg.certificateservice.patient.dto.PersonsRequestDTO;
import se.inera.intyg.certificateservice.patient.dto.PersonsResponseDTO;

@Service
public class IPSIntegrationService {

  private final RestClient ipsRestClient;

  public IPSIntegrationService(@Qualifier("ipsRestClient") RestClient ipsRestClient) {
    this.ipsRestClient = ipsRestClient;
  }

  @PerformanceLogging(eventAction = "find-persons-in-ips", eventType = EVENT_TYPE_ACCESSED)
  public PersonsResponseDTO findPersons(PersonsRequestDTO request) {
    return ipsRestClient
        .post()
        .uri("/api/v1/persons")
        .body(request)
        .header(LOG_TRACE_ID_HEADER, MDC.get(TRACE_ID_KEY))
        .header(LOG_SESSION_ID_HEADER, MDC.get(SESSION_ID_KEY))
        .contentType(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(PersonsResponseDTO.class);
  }
}