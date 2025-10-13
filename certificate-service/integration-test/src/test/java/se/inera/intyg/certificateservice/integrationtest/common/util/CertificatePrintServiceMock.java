package se.inera.intyg.certificateservice.integrationtest.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse;

@RequiredArgsConstructor
public class CertificatePrintServiceMock {

  private final MockServerClient mockServerClient;

  public void mockPdf() {
    try {
      mockServerClient.when(HttpRequest.request("/api/print"))
          .respond(
              HttpResponse
                  .response(
                      new ObjectMapper().writeValueAsString(
                          GetCertificatePdfResponse.builder()
                              .fileName("lakarintyg_transportstyrelsen")
                              .pdfData("pdfData".getBytes())
                              .build()
                      )
                  )
                  .withStatusCode(200)
                  .withContentType(MediaType.APPLICATION_JSON)
          );
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }
}
