package se.inera.intyg.certificateservice.integrationtest.fk7472;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.integrationtest.fk7472.FK7472Constants.FK7472;
import static se.inera.intyg.certificateservice.integrationtest.fk7472.FK7472Constants.VERSION;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateModelIdUtil.certificateModelId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateTypeInfoUtil.certificateTypeInfo;
import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
import se.inera.intyg.certificateservice.integrationtest.util.Containers;

@ActiveProfiles({"integration-test", TESTABILITY_PROFILE})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FK7472InactiveIT {

  @LocalServerPort
  private int port;

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.fk7472.v1_0.active.from", () -> "2099-01-01T00:00:00");
  }

  private final TestRestTemplate restTemplate;
  private ApiUtil api;

  @Autowired
  public FK7472InactiveIT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @BeforeAll
  public static void beforeAll() {
    Containers.ensureRunning();
  }

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
  }

  @AfterEach
  void tearDown() {
    api.reset();
  }

  @Nested
  @DisplayName("FK7472 - Hämta intygstyp när den är inte är aktiv")
  class GetCertificateTypeInfo {

    @Test
    @DisplayName("FK7472 - Om inte aktiverad ska intygstypen inte vara med i listan av tillgängliga intygstyper")
    void shallNotReturnFK7472WhenInactive() {
      final var response = api.certificateTypeInfo(
          defaultCertificateTypeInfoRequest()
      );

      assertNull(
          certificateTypeInfo(response.getBody(), FK7472),
          "Should not contain %s as it is not active!".formatted(FK7472)
      );
    }
  }

  @Nested
  @DisplayName("FK7472 - Aktiva versioner för inaktiv intygstyp")
  class ExistsCertificateTypeInfo {

    @Test
    @DisplayName("FK7472 - Om typen inte är aktiverad skall ingen version returneras")
    void shallReturnEmptyWhenTypeIsNotActive() {
      final var response = api.findLatestCertificateTypeVersion(FK7472);

      assertNull(
          certificateModelId(response.getBody()),
          () -> "Expected that no version should be returned but instead returned %s"
              .formatted(certificateModelId(response.getBody()))
      );
    }
  }

  @Nested
  @DisplayName("FK7472 - Skapa utkast")
  class CreateCertificate {

    @Test
    @DisplayName("FK7472 - Om typen inte är aktiverad skall felkod 400 (BAD_REQUEST) returneras")
    void shallReturn400WhenTypeIsNotActive() {
      final var response = api.createCertificate(
          defaultCreateCertificateRequest(FK7472, VERSION)
      );

      assertEquals(400, response.getStatusCode().value());
    }
  }
}
