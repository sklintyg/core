package se.inera.intyg.certificateservice.certificate.integration;

import static se.inera.intyg.certificateservice.logging.MdcHelper.LOG_SESSION_ID_HEADER;
import static se.inera.intyg.certificateservice.logging.MdcHelper.LOG_TRACE_ID_HEADER;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.SESSION_ID_KEY;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.TRACE_ID_KEY;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateRequestDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateResponseDTO;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;

@Service
public class PrintCertificateFromCertificatePrintService {

  private final RestClient cpsRestClient;

  @Value("${integration.certificateprintservice.address}")
  private String printCertificateServiceUrl;

  public PrintCertificateFromCertificatePrintService(
      @Qualifier("cpsRestClient") RestClient cpsRestClient) {
    this.cpsRestClient = cpsRestClient;
  }
  
  @PerformanceLogging(eventAction = "print-certificate-from-certificate-print-service", eventType = EVENT_TYPE_ACCESSED)
  public PrintCertificateResponseDTO print(PrintCertificateRequestDTO request) {
    return cpsRestClient
        .post()
        .uri(printCertificateServiceUrl + "/api/print")
        .header(LOG_TRACE_ID_HEADER, MDC.get(TRACE_ID_KEY))
        .header(LOG_SESSION_ID_HEADER, MDC.get(SESSION_ID_KEY))
        .body(request)
        .retrieve()
        .body(PrintCertificateResponseDTO.class);
  }
}