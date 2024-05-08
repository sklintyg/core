package se.inera.intyg.certificateservice.integrationtest.fk7211;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.FK7211;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.VERSION;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
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
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.util.ApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.Containers;
import se.inera.intyg.certificateservice.integrationtest.util.InternalApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.TestListener;
import se.inera.intyg.certificateservice.integrationtest.util.TestabilityApiUtil;

@ActiveProfiles({"integration-test", TESTABILITY_PROFILE})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FK7211CitizenIT {

  @LocalServerPort
  private int port;

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.fk7211.v1_0.active.from", () -> "2024-01-01T00:00:00");
  }

  private final TestRestTemplate restTemplate;
  private static final TestListener testListener = new TestListener();

  private ApiUtil api;
  private InternalApiUtil internalApi;
  private TestabilityApiUtil testabilityApi;

  @Autowired
  public FK7211CitizenIT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
    this.internalApi = new InternalApiUtil(restTemplate, port);
    this.testabilityApi = new TestabilityApiUtil(restTemplate, port);
    testListener.emptyMessages();
  }

  @BeforeAll
  public static void beforeAll() {
    Containers.ensureRunning();
  }

  @AfterEach
  void tearDown() {
    testabilityApi.reset();
    api.reset();
    internalApi.reset();
  }

  @Nested
  @DisplayName("FK7211 - Hämta intyg för invånare")
  class GetCertificateTypeInfo {

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på invånaren ska intyget returneras")
    void shallReturnFK7211IfIssuedOnCitizen() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.getCitizenCertificate(
          GetCitizenCertificateRequest.builder()
              .personId(PersonIdDTO.builder()
                  .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                  .id(ATHENA_REACT_ANDERSSON_ID)
                  .build())
              .build(), certificateId(testCertificates)
      );

      assertNotNull(response.getBody().getCertificate(),
          "Should return certificate of patient.");
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan invånare ska felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfNotIssuedOnCitizen() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.getCitizenCertificate(
          GetCitizenCertificateRequest.builder()
              .personId(PersonIdDTO.builder()
                  .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                  .id(ALVE_REACT_ALFREDSSON_ID)
                  .build())
              .build(), certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }
  }
}
