package se.inera.intyg.certificateservice.integrationtest.fk7211;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.FK7211;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateTypeInfoUtil.certificateTypeInfo;
import static se.inera.intyg.certificateservice.integrationtest.util.ResourceLinkUtil.resourceLink;

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
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.util.ApiUtil;


@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FK7211ActiveIT {

  @LocalServerPort
  private int port;

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.fk7211.v1_0.active.from", () -> "2024-01-01T00:00:00");
  }

  private final TestRestTemplate restTemplate;

  private ApiUtil api;

  @Autowired
  public FK7211ActiveIT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
  }

  @Nested
  class GetCertificateTypeInfo {

    @Test
    void shallReturnFK7211WhenActive() {
      final var response = api.certificateTypeInfo(
          defaultCertificateTypeInfoRequest()
      );

      assertNotNull(
          certificateTypeInfo(response.getBody(), FK7211),
          "Should contain %s as it is active!".formatted(FK7211)
      );
    }

    @Test
    void shallReturnResourceLinkCreateCertificate() {
      final var response = api.certificateTypeInfo(
          defaultCertificateTypeInfoRequest()
      );

      assertNotNull(
          resourceLink(
              certificateTypeInfo(response.getBody(), FK7211),
              ResourceLinkTypeDTO.CREATE_CERTIFICATE
          ),
          "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE)
      );
    }

    @Test
    void shallNotReturnResourceLinkCreateCertificateIfPatientIsDeceased() {
      final var response = api.certificateTypeInfo(
          customCertificateTypeInfoRequest().deceased(true).build()
      );

      assertNull(
          resourceLink(
              certificateTypeInfo(response.getBody(), FK7211),
              ResourceLinkTypeDTO.CREATE_CERTIFICATE
          ),
          "Should not contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE)
      );
    }

    @Test
    void shallNotReturnResourceLinkCreateCertificateIfUserIsBlocked() {
      final var response = api.certificateTypeInfo(
          customCertificateTypeInfoRequest().blocked(true).build()
      );

      assertNull(
          resourceLink(
              certificateTypeInfo(response.getBody(), FK7211),
              ResourceLinkTypeDTO.CREATE_CERTIFICATE
          ),
          "Should not contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE)
      );
    }

    @Test
    void shallNotReturnResourceLinkCreateCertificateIfUserIsBlockedAndPatientIsDeceased() {
      final var response = api.certificateTypeInfo(
          customCertificateTypeInfoRequest().blocked(true).deceased(true).build()
      );

      assertNull(
          resourceLink(
              certificateTypeInfo(response.getBody(), FK7211),
              ResourceLinkTypeDTO.CREATE_CERTIFICATE
          ),
          "Should not contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE)
      );
    }
  }

  @Nested
  @DisplayName("FK7211 - Aktiva versioner")
  class ExistsCertificateTypeInfo {

    @Test
    @DisplayName("FK7211 - Aktiv version skall vara 1.0")
    void shallReturnLatestVersionWhenTypeExists() {

    }
  }

  @Nested
  @DisplayName("FK7211 - Skapa utkast")
  class CreateCertificate {

    @Test
    @DisplayName("FK7211 - Om utkastet framgångsrikt skapats skall utkastet returneras")
    void shallReturnCertificateWhenActive() {

    }

    @Test
    @DisplayName("FK7211 - Om patienten är avliden skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403PatientIsDeceased() {

    }

    @Test
    @DisplayName("FK7211 - Om användaren är blockerad skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403UserIsBlocked() {

    }

    @Test
    @DisplayName("FK7211 - Om patient är avliden och användaren är blockerad skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403PatientIsDeceasedAndUserIsBlocked() {

    }

    @Test
    @DisplayName("FK7211 - Om den efterfrågade versionen inte stöds skall felkod 400 (BAD_REQUEST) returneras")
    void shallReturn400IfVersionNotSupported() {

    }
  }
}
