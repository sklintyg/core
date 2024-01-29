package se.inera.intyg.certificateservice.integrationtest.fk7211;

import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.FK7211;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateTypeInfoUtil.certificateTypeInfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.integrationtest.util.ApiUtil;

@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FK7211InactiveIT {

  @LocalServerPort
  private int port;

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.fk7211.v1_0.active.from", () -> "2099-01-01T00:00:00");
  }

  private final TestRestTemplate restTemplate;

  private ApiUtil api;

  @Autowired
  public FK7211InactiveIT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
  }

  @Test
  void shallNotReturnFK7211WhenInactive() {
    final var response = api.certificateTypeInfo(
        defaultCertificateTypeInfoRequest()
    );

    assertNull(
        certificateTypeInfo(response.getBody(), "FK7211"),
        "Should not contain %s as it is not active!".formatted(FK7211)
    );
  }
}
