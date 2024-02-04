package se.inera.intyg.certificateservice.integrationtest.fk7211;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATLAS_REACT_ABRAHAMSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.FK7211;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.VERSION;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.WRONG_VERSION;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateModelIdUtil.certificateModelId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateTypeInfoUtil.certificateTypeInfo;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.exists;
import static se.inera.intyg.certificateservice.integrationtest.util.ResourceLinkUtil.resourceLink;
import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import org.junit.jupiter.api.AfterEach;
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
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.util.ApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.TestabilityApiUtil;


@ActiveProfiles({"integration-test", TESTABILITY_PROFILE})
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
  private TestabilityApiUtil testabilityApi;

  @Autowired
  public FK7211ActiveIT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
    this.testabilityApi = new TestabilityApiUtil(restTemplate, port);
  }

  @AfterEach
  void tearDown() {
    testabilityApi.reset();
    api.reset();
  }

  @Nested
  @DisplayName("FK7211 - Hämta intygstyp när den är aktiv")
  class GetCertificateTypeInfo {

    @Test
    @DisplayName("FK7211 - Om aktiverad ska intygstypen returneras i listan av tillgängliga intygstyper")
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
    @DisplayName("FK7211 - Om aktiverad ska 'Skapa utkast' vara tillgänglig")
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
    @DisplayName("FK7211 - Om patienten är avliden ska inte 'Skapa utkast' vara tillgänglig")
    void shallNotReturnResourceLinkCreateCertificateIfPatientIsDeceased() {
      final var response = api.certificateTypeInfo(
          customCertificateTypeInfoRequest()
              .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
              .build()
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
    @DisplayName("FK7211 - Om användaren är blockerad ska inte 'Skapa utkast' vara tillgänglig")
    void shallNotReturnResourceLinkCreateCertificateIfUserIsBlocked() {
      final var response = api.certificateTypeInfo(
          customCertificateTypeInfoRequest()
              .user(
                  ajlaDoktorDtoBuilder()
                      .blocked(Boolean.TRUE)
                      .build()
              )
              .build()
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
    @DisplayName("FK7211 - Om användaren är blockerad och patienten avliden ska inte 'Skapa utkast' vara tillgänglig")
    void shallNotReturnResourceLinkCreateCertificateIfUserIsBlockedAndPatientIsDeceased() {
      final var response = api.certificateTypeInfo(
          customCertificateTypeInfoRequest()
              .user(
                  ajlaDoktorDtoBuilder()
                      .blocked(Boolean.TRUE)
                      .build()
              )
              .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
              .build()
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
    @DisplayName("FK7211 - Vårdadmininstratör - Om patienten har skyddade personuppgifter ska inte 'Skapa utkast' vara tillgänglig")
    void shallNotReturnResourceLinkCreateCertificateIfUserIsCareAdminAndPatientIsProtected() {
      final var response = api.certificateTypeInfo(
          customCertificateTypeInfoRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
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
    @DisplayName("FK7211 - Läkare - Om patienten har skyddade personuppgifter ska 'Skapa utkast' vara tillgänglig")
    void shallReturnResourceLinkCreateCertificateIfUserIsDoctorAndPatientIsProtected() {
      final var response = api.certificateTypeInfo(
          customCertificateTypeInfoRequest()
              .user(AJLA_DOCTOR_DTO)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      assertNotNull(
          resourceLink(
              certificateTypeInfo(response.getBody(), FK7211),
              ResourceLinkTypeDTO.CREATE_CERTIFICATE
          ),
          "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE)
      );
    }
  }

  @Nested
  @DisplayName("FK7211 - Aktiva versioner")
  class ExistsCertificateTypeInfo {

    @Test
    @DisplayName("FK7211 - Aktiv version skall vara 1.0")
    void shallReturnLatestVersionWhenTypeExists() {
      final var expectedCertificateModelId = CertificateModelIdDTO.builder()
          .type(FK7211)
          .version(VERSION)
          .build();

      final var response = api.findLatestCertificateTypeVersion(FK7211);

      assertEquals(
          expectedCertificateModelId,
          certificateModelId(response.getBody())
      );
    }
  }

  @Nested
  @DisplayName("FK7211 - Skapa utkast")
  class CreateCertificate {

    @Test
    @DisplayName("FK7211 - Om utkastet framgångsrikt skapats skall utkastet returneras")
    void shallReturnCertificateWhenActive() {
      final var response = api.createCertificate(
          defaultCreateCertificateRequest(FK7211, VERSION)
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate as it is active!"
      );
    }

    @Test
    @DisplayName("FK7211 - Om patienten är avliden skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403PatientIsDeceased() {
      final var response = api.createCertificate(
          customCreateCertificateRequest(FK7211, VERSION)
              .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
              .build()
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om användaren är blockerad skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403UserIsBlocked() {
      final var response = api.createCertificate(
          customCreateCertificateRequest(FK7211, VERSION)
              .user(
                  ajlaDoktorDtoBuilder()
                      .blocked(Boolean.TRUE)
                      .build()
              )
              .build()
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om patient är avliden och användaren är blockerad skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403PatientIsDeceasedAndUserIsBlocked() {
      final var response = api.createCertificate(
          customCreateCertificateRequest(FK7211, VERSION)
              .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
              .user(
                  ajlaDoktorDtoBuilder()
                      .blocked(Boolean.TRUE)
                      .build()
              )
              .build()
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Läkare - Om patienten har skyddade personuppgifter skall utkastet returneras")
    void shallReturnCertificateIfPatientIsProtectedPersonAndUserDoctor() {
      final var response = api.createCertificate(
          customCreateCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .user(AJLA_DOCTOR_DTO)
              .build()
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate because the user is a doctor!"
      );
    }

    @Test
    @DisplayName("FK7211 - Vårdadministratör - Om patienten har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403PatientIsProtectedPersonAndUserDoctor() {
      final var response = api.createCertificate(
          customCreateCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build()
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om den efterfrågade versionen inte stöds skall felkod 400 (BAD_REQUEST) returneras")
    void shallReturn400IfVersionNotSupported() {
      final var response = api.createCertificate(
          defaultCreateCertificateRequest(FK7211, WRONG_VERSION)
      );

      assertEquals(400, response.getStatusCode().value());
    }
  }

  @Nested
  @DisplayName("FK7211 - Finns intyget i tjänsten")
  class ExistsCertificate {

    @Test
    @DisplayName("FK7211 - Om intyget finns så returneras true")
    void shallReturnTrueIfCertificateExists() {
      final var testCertificate = testabilityApi.addCertificate(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.certificateExists(
          certificateId(testCertificate.getBody())
      );

      assertTrue(
          exists(response.getBody()),
          "Should return true when certificate exists!"
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget inte finns lagrat så returneras false")
    void shallReturnFalseIfCertificateDoesnt() {
      final var response = api.certificateExists("certificate-not-exists");

      assertFalse(
          exists(response.getBody()),
          "Should return false when certificate doesnt exists!"
      );
    }
  }

  @Nested
  @DisplayName("FK7211 - Hämta intyg")
  class GetCertificate {

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på samma mottagning skall det returneras")
    void shallReturnCertificateIfUnitIsSubUnitAndOnSameUnit() {
      final var testCertificate = testabilityApi.addCertificate(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getCertificate(
          defaultGetCertificateRequest(),
          certificateId(testCertificate.getBody())
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate when exists!"
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det returneras")
    void shallReturnCertificateIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificate = testabilityApi.addCertificate(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getCertificate(
          customGetCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificate.getBody())
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate when exists!"
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på på samma vårdenhet skall det returneras")
    void shallReturnCertificateIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificate = testabilityApi.addCertificate(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.getCertificate(
          customGetCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificate.getBody())
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate when exists!"
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificate = testabilityApi.addCertificate(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getCertificate(
          customGetCertificateRequest().unit(ALFA_HUDMOTTAGNINGEN_DTO).build(),
          certificateId(testCertificate.getBody())
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificate = testabilityApi.addCertificate(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getCertificate(
          customGetCertificateRequest()
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .unit(ALFA_VARDCENTRAL_DTO)
              .build(),
          certificateId(testCertificate.getBody())
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Vårdadministratör - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfPatientIsProtectedPersonAndUserIsCareAdmin() {
      final var testCertificate = testabilityApi.addCertificate(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.getCertificate(
          customGetCertificateRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build(),
          certificateId(testCertificate.getBody())
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
    void shallReturnCertificateIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificate = testabilityApi.addCertificate(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.getCertificate(
          customGetCertificateRequest()
              .user(AJLA_DOCTOR_DTO)
              .build(),
          certificateId(testCertificate.getBody())
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate when exists!"
      );
    }
  }
}
