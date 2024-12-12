package se.inera.intyg.certificateservice.certificate.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateRequestDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateResponseDTO;

@ExtendWith(MockitoExtension.class)
class PrintCertificateFromCertificatePrintServiceTest {


  private static MockWebServer mockWebServer;
  private PrintCertificateFromCertificatePrintService printCertificateFromCertificatePrintService;

  private static final String CERTIFICATE_ID = "certificateId";
  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeAll
  static void setUp() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    mockWebServer.shutdown();
  }

  @BeforeEach
  void initialize() {
    final var scheme = "http";
    final var baseUrl = "localhost";
    final var endpoint = "/internalapi/certificate/{certificateId}/pdf";
    printCertificateFromCertificatePrintService = new PrintCertificateFromCertificatePrintService(
        WebClient.create(baseUrl), scheme, baseUrl,
        mockWebServer.getPort(), endpoint);
  }

  @Test
  void shouldReturnResponse() throws JsonProcessingException {
    final var request = PrintCertificateRequestDTO.builder()
        .build();

    final var expectedResponse = PrintCertificateResponseDTO.builder()
        .build();

    mockWebServer.enqueue(
        new MockResponse().setBody(objectMapper.writeValueAsString(expectedResponse))
            .addHeader("Content-Type", "application/json"));

    final var actualResponse = printCertificateFromCertificatePrintService.print(request,
        CERTIFICATE_ID);

    assertEquals(expectedResponse, actualResponse);
  }
}
