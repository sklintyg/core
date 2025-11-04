package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.athenaReactAnderssonDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.alfaAllergimottagningenDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.metadata;
import static se.inera.intyg.certificateservice.integrationtest.fk7804.FK7804TestSetup.ACTIVE_CERTIFICATE_TYPE_VERSION;
import static se.inera.intyg.certificateservice.integrationtest.fk7804.FK7804TestSetup.CERTIFICATE_TYPE;
import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import se.inera.intyg.certificateservice.integrationtest.common.util.ApiUtil;
import se.inera.intyg.certificateservice.integrationtest.common.util.Containers;
import se.inera.intyg.certificateservice.integrationtest.common.util.TestabilityApiUtil;
import se.inera.intyg.certificateservice.patient.dto.PersonsResponseDTO;

@ActiveProfiles({"integration-test", TESTABILITY_PROFILE})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CertificateMetadataIT {

  @LocalServerPort
  private int port;

  private final TestRestTemplate restTemplate;
  private ApiUtil api;
  private TestabilityApiUtil testabilityApi;

  @Autowired
  public CertificateMetadataIT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @BeforeAll
  static void beforeAll() {
    Containers.ensureRunning();
  }

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
    this.testabilityApi = new TestabilityApiUtil(restTemplate, port);
    final var mockServerClient = new MockServerClient(
        Containers.MOCK_SERVER_CONTAINER.getHost(),
        Containers.MOCK_SERVER_CONTAINER.getServerPort()
    );
    mockIntygProxyService(mockServerClient);
  }

  @AfterEach
  void tearDown() {
    api.reset();
    testabilityApi.reset();
  }

  @Test
  @DisplayName("Om patient uppdaterats ska de uppdaterade informationen finnas på det nya intyget")
  void shallUpdatePatient() {

    final var updatedAthena = athenaReactAnderssonDtoBuilder()
        .lastName("Athenasson").fullName("Athena Athenasson").build();
    final var response = api.createCertificate(
        defaultCreateCertificateRequest(CERTIFICATE_TYPE, ACTIVE_CERTIFICATE_TYPE_VERSION)
    );

    assertAll(
        () -> assertNotNull(certificate(response.getBody()), "Should return certificate"),
        () -> assertEquals(ATHENA_REACT_ANDERSSON_DTO.getLastName(),
            metadata(certificate(response.getBody())).getPatient().getLastName())

    );

    final var updatedResponse = api.createCertificate(
        customCreateCertificateRequest(CERTIFICATE_TYPE, ACTIVE_CERTIFICATE_TYPE_VERSION)
            .patient(updatedAthena)
            .user(AJLA_DOCTOR_DTO)
            .build()
    );

    assertAll(
        () -> assertNotNull(certificate(updatedResponse.getBody()), "Should return certificate"),
        () -> assertEquals(updatedAthena.getLastName(),
            metadata(certificate(updatedResponse.getBody())).getPatient().getLastName())

    );
  }

  @Test
  @DisplayName("Om användaren uppdaterats ska den uppdaterade informationen finnas på det nya "
      + "intyget")
  void shallUpdateStaff() {

    final var updatedAjla = ajlaDoktorDtoBuilder()
        .lastName("Ajlasson").fullName("Ajla Ajlasson").build();

    final var response = api.createCertificate(
        defaultCreateCertificateRequest(CERTIFICATE_TYPE, ACTIVE_CERTIFICATE_TYPE_VERSION)
    );

    assertAll(
        () -> assertNotNull(certificate(response.getBody()), "Should return certificate"),
        () -> assertEquals(AJLA_DOCTOR_DTO.getLastName(),
            metadata(certificate(response.getBody())).getCreatedBy().getLastName())

    );

    final var updatedResponse = api.createCertificate(
        customCreateCertificateRequest(CERTIFICATE_TYPE, ACTIVE_CERTIFICATE_TYPE_VERSION)
            .user(updatedAjla)
            .build()
    );

    assertAll(
        () -> assertNotNull(certificate(updatedResponse.getBody()), "Should return certificate"),
        () -> assertEquals(updatedAjla.getLastName(),
            metadata(certificate(updatedResponse.getBody())).getCreatedBy().getLastName())

    );
  }

  @Test
  @DisplayName("Om vårdenheten uppdaterats ska den uppdaterade informationen finnas på det "
      + "nya intyget")
  void shallUpdateUnit() {

    final var updatedAlfaAllergi = alfaAllergimottagningenDtoBuilder()
        .name("ALERGI").build();
    final var response = api.createCertificate(
        defaultCreateCertificateRequest(CERTIFICATE_TYPE, ACTIVE_CERTIFICATE_TYPE_VERSION)
    );

    assertAll(
        () -> assertNotNull(certificate(response.getBody()), "Should return certificate"),
        () -> assertEquals(ALFA_ALLERGIMOTTAGNINGEN_DTO.getName(),
            metadata(certificate(response.getBody())).getUnit().getUnitName())

    );

    final var updatedResponse = api.createCertificate(
        customCreateCertificateRequest(CERTIFICATE_TYPE, ACTIVE_CERTIFICATE_TYPE_VERSION)
            .unit(updatedAlfaAllergi)
            .build()
    );

    assertAll(
        () -> assertNotNull(certificate(updatedResponse.getBody()), "Should return certificate"),
        () -> assertEquals(updatedAlfaAllergi.getName(),
            metadata(certificate(updatedResponse.getBody())).getUnit().getUnitName())

    );
  }

  private void mockIntygProxyService(MockServerClient mockServerClient) {
    try {
      mockServerClient.when(HttpRequest.request("/api/v1/persons"))
          .respond(
              HttpResponse
                  .response(
                      new ObjectMapper().writeValueAsString(
                          PersonsResponseDTO.builder()
                              .persons(Collections.emptyList())
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