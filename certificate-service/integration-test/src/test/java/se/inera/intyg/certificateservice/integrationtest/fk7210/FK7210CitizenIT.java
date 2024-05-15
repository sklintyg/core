package se.inera.intyg.certificateservice.integrationtest.fk7210;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.integrationtest.fk7210.FK7210Constants.FK7210;
import static se.inera.intyg.certificateservice.integrationtest.fk7210.FK7210Constants.VERSION;
import static se.inera.intyg.certificateservice.integrationtest.fk7472.FK7472Constants.FK7472;
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
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateListRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.util.ApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.Containers;
import se.inera.intyg.certificateservice.integrationtest.util.InternalApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.TestListener;
import se.inera.intyg.certificateservice.integrationtest.util.TestabilityApiUtil;

@ActiveProfiles({"integration-test", TESTABILITY_PROFILE})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FK7210CitizenIT {

  @LocalServerPort
  private int port;

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.fk7210.v1_0.active.from", () -> "2024-01-01T00:00:00");
  }

  private final TestRestTemplate restTemplate;
  private static final TestListener testListener = new TestListener();

  private ApiUtil api;
  private InternalApiUtil internalApi;
  private TestabilityApiUtil testabilityApi;

  @Autowired
  public FK7210CitizenIT(TestRestTemplate restTemplate) {
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
  @DisplayName("FK7210 - Hämta intyg för invånare")
  class GetCitizenCertificate {

    @Test
    @DisplayName("FK7210 - Om intyget är utfärdat på invånaren ska intyget returneras")
    void shallReturnFK7210IfIssuedOnCitizen() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7210, VERSION, SIGNED)
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
    @DisplayName("FK7210 - Om intyget är utfärdat på en annan invånare ska felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfNotIssuedOnCitizen() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7210, VERSION, SIGNED)
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

  @Nested
  @DisplayName("FK7210 - Hämta intygslista för invånare")
  class GetCitizenCertificateList {

    @Test
    @DisplayName("FK7210 - Om intyget är utfärdat på invånaren ska intyget returneras")
    void shallReturnListOfCertificatesIfAvailableForCitizen() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7210, VERSION, SIGNED),
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED),
          defaultTestablilityCertificateRequest(FK7210, VERSION, SIGNED)
      );

      final var response = api.getCitizenCertificateList(
          GetCitizenCertificateListRequest.builder()
              .personId(PersonIdDTO.builder()
                  .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                  .id(ATHENA_REACT_ANDERSSON_ID)
                  .build())
              .build()
      );

      assertEquals(2, response.getBody().getCitizenCertificates().size(),
          "Should return list of certificates available for citizen.");
    }

    @Test
    @DisplayName("FK7210 - Om invånaren inte har några intyg ska en tom lista returneras")
    void shallReturnEmptyListIfNoCertificatesIssuedOnCitizen() {
      final var response = api.getCitizenCertificateList(
          GetCitizenCertificateListRequest.builder()
              .personId(PersonIdDTO.builder()
                  .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                  .id(ATHENA_REACT_ANDERSSON_ID)
                  .build())
              .build()
      );

      assertEquals(0, response.getBody().getCitizenCertificates().size(),
          "Should return empty list of certificates.");
    }
  }

  @Nested
  @DisplayName("FK7210 - Skriv ut intyg för invånare")
  class PrintCitizenCertificate {

    @Test
    @DisplayName("FK7210 - Om intyget är utfärdat på invånaren ska intyget skrivas ut")
    void shallReturnFK7210IfIssuedOnCitizen() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7210, VERSION, SIGNED)
      );

      final var response = api.printCitizenCertificate(
          PrintCitizenCertificateRequest.builder()
              .personId(PersonIdDTO.builder()
                  .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                  .id(ATHENA_REACT_ANDERSSON_ID)
                  .build())
              .additionalInfo("Intyget är utskrivet från 1177 Intyg.")
              .build(), certificateId(testCertificates)
      );

      assertNotNull(response.getBody().getPdfData(),
          "Should return pdf of certificate of patient.");
    }

    @Test
    @DisplayName("FK7210 - Om intyget är utfärdat på en annan invånare ska felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfNotIssuedOnCitizen() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7210, VERSION, SIGNED)
      );

      final var response = api.printCitizenCertificate(
          PrintCitizenCertificateRequest.builder()
              .personId(PersonIdDTO.builder()
                  .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                  .id(ALVE_REACT_ALFREDSSON_ID)
                  .build())
              .additionalInfo("Intyget är utskrivet från 1177 Intyg.")
              .build(), certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }
  }
}
