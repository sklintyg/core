package se.inera.intyg.certificateservice.integrationtest.fk7211;

import static org.awaitility.Awaitility.waitAtMost;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ALVE_REACT_ALFREDSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATLAS_REACT_ABRAHAMSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonStaffDTO.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonStaffDTO.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.FK7211;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.VERSION;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.WRONG_VERSION;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customDeleteCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetCertificateXmlRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetPatientCertificatesRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetUnitCertificatesInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetUnitCertificatesRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customUpdateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customValidateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultDeleteCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateXmlRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetPatientCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetUnitCertificatesInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetUnitCertificatesRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateModelIdUtil.certificateModelId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateTypeInfoUtil.certificateTypeInfo;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateInternalXmlResponse;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateXmlReponse;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificates;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.decodeXml;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.exists;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.getValueFromData;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.recipient;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.updateDateValue;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.updateUnit;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.validationErrors;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.version;
import static se.inera.intyg.certificateservice.integrationtest.util.ResourceLinkUtil.resourceLink;
import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.application.unit.dto.CertificatesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.integrationtest.util.ApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.Containers;
import se.inera.intyg.certificateservice.integrationtest.util.InternalApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.StaffUtil;
import se.inera.intyg.certificateservice.integrationtest.util.TestListener;
import se.inera.intyg.certificateservice.integrationtest.util.TestabilityApiUtil;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;


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
  private final TestListener testListener;

  private ApiUtil api;
  private InternalApiUtil internalApi;
  private TestabilityApiUtil testabilityApi;

  @Autowired
  public FK7211ActiveIT(TestRestTemplate restTemplate, TestListener testListener) {
    this.restTemplate = restTemplate;
    this.testListener = testListener;
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
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.certificateExists(
          certificateId(testCertificates)
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
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getCertificate(
          defaultGetCertificateRequest(),
          certificateId(testCertificates)
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate when exists!"
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det returneras")
    void shallReturnCertificateIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getCertificate(
          customGetCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate when exists!"
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på samma vårdenhet skall det returneras")
    void shallReturnCertificateIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.getCertificate(
          customGetCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate when exists!"
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getCertificate(
          customGetCertificateRequest().unit(ALFA_HUDMOTTAGNINGEN_DTO).build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getCertificate(
          customGetCertificateRequest()
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .unit(ALFA_VARDCENTRAL_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Vårdadministratör - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfPatientIsProtectedPersonAndUserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.getCertificate(
          customGetCertificateRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
    void shallReturnCertificateIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.getCertificate(
          customGetCertificateRequest()
              .user(AJLA_DOCTOR_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate when exists!"
      );
    }
  }

  @Nested
  @DisplayName("FK7211 - Uppdatera svarsalternativ")
  class UpdateCertificate {

    @Test
    @DisplayName("FK7211 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall svarsalternativ uppdateras")
    void shallUpdateDataIfUserIsDoctorAndPatientIsProtectedPerson() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );
      final var questionId = "1";
      final var expectedDate = LocalDate.now().plusDays(5);
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, expectedDate))
      );

      final var response = api.updateCertificate(
          customUpdateCertificateRequest()
              .user(AJLA_DOCTOR_DTO)
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(expectedDate,
          getValueFromData(response.getBody(), CertificateDataValueType.DATE, questionId)
      );
    }

    @Test
    @DisplayName("FK7211 - Vårdadmin - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallNotUpdateDataIfUserIsCareAdminAndPatientIsProtectedPerson() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var certificate = certificate(testCertificates);

      final var response = api.updateCertificate(
          customUpdateCertificateRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall svarsalternativ uppdateras")
    void shallUpdateDataIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var questionId = "1";
      final var expectedDate = LocalDate.now().plusDays(5);
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, expectedDate))
      );

      final var response = api.updateCertificate(
          customUpdateCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(expectedDate,
          getValueFromData(response.getBody(), CertificateDataValueType.DATE, questionId)
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på samma mottagning skall svarsalternativ uppdateras")
    void shallUpdateDataIfUnitIsSubUnitAndOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var questionId = "1";
      final var expectedDate = LocalDate.now().plusDays(5);
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, expectedDate))
      );

      final var response = api.updateCertificate(
          customUpdateCertificateRequest()
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(expectedDate,
          getValueFromData(response.getBody(), CertificateDataValueType.DATE, questionId)
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på samma vårdenhet skall svarsalternativ uppdateras")
    void shallUpdateDataIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var questionId = "1";
      final var expectedDate = LocalDate.now().plusDays(5);
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, expectedDate))
      );

      final var response = api.updateCertificate(
          customUpdateCertificateRequest()
              .certificate(certificate)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(expectedDate,
          getValueFromData(response.getBody(), CertificateDataValueType.DATE, questionId)
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var certificate = certificate(testCertificates);

      final var response = api.updateCertificate(
          customUpdateCertificateRequest()
              .certificate(certificate)
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var certificate = certificate(testCertificates);

      final var response = api.updateCertificate(
          customUpdateCertificateRequest()
              .certificate(certificate)
              .unit(ALFA_VARDCENTRAL_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om utkastet sparas med en inaktuell revision av utkastet skall felkod 409 (CONFLICT) returneras")
    void shallNotUpdateDataIfUserIsTryingToSaveWithAnOldRevision() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var certificate = certificate(testCertificates);

      api.updateCertificate(
          customUpdateCertificateRequest()
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      final var response = api.updateCertificateWithConcurrentError(
          customUpdateCertificateRequest()
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(409, response.getStatusCode().value());
    }
  }

  @Nested
  @DisplayName("FK7211 - Ta bort utkast")
  class DeleteCertificate {

    @Test
    @DisplayName("FK7211 - Om utkastet är skapat på samma mottagning skall det gå att tas bort")
    void shallDeleteCertificateIfCertificateIsOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var certificateId = certificateId(testCertificates);
      api.deleteCertificate(
          defaultDeleteCertificateRequest(),
          certificateId,
          version(testCertificates)
      );

      assertFalse(
          exists(api.certificateExists(certificateId).getBody())
      );
    }

    @Test
    @DisplayName("FK7211 - Om utkastet är skapat på samma vårdenhet skall det gå att tas bort")
    void shallDeleteCertificateIfCertificateIsOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var certificateId = certificateId(testCertificates);
      api.deleteCertificate(
          customDeleteCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId,
          version(testCertificates)
      );

      assertFalse(
          exists(api.certificateExists(certificateId).getBody())
      );
    }

    @Test
    @DisplayName("FK7211 - Om utkastet är skapat på annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var certificateId = certificateId(testCertificates);
      final var response = api.deleteCertificate(
          customDeleteCertificateRequest()
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build(),
          certificateId,
          version(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om utkastet är skapat på annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var certificateId = certificateId(testCertificates);
      final var response = api.deleteCertificate(
          customDeleteCertificateRequest()
              .unit(ALFA_VARDCENTRAL_DTO)
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .build(),
          certificateId,
          version(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Vårdadmin - Om utkastet är skapat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallNotDeleteDataIfUserIsCareAdminAndPatientIsProtectedPerson() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var certificateId = certificateId(testCertificates);
      final var response = api.deleteCertificate(
          customDeleteCertificateRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build(),
          certificateId,
          version(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }
  }

  @Nested
  @DisplayName("FK7211 - Hämta tidigare intyg för patient")
  class GetPatientCertificates {

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på samma mottagning skall det returneras")
    void shallReturnCertificateIfUnitIsSubUnitAndOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ALVE_REACT_ALFREDSSON_DTO)
              .build()
      );

      final var response = api.getPatientCertificates(
          defaultGetPatientCertificateRequest()
      );

      assertAll(
          () -> assertTrue(
              exists(certificates(response.getBody()), certificate(testCertificates))
          ),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på samma vårdenhet skall det returneras")
    void shallReturnCertificateIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ALVE_REACT_ALFREDSSON_DTO)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.getPatientCertificates(
          customGetPatientCertificatesRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      assertAll(
          () -> assertTrue(
              exists(certificates(response.getBody()), certificate(testCertificates))
          ),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det returneras")
    void shallReturnCertificateIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ALVE_REACT_ALFREDSSON_DTO)
              .build()
      );

      final var response = api.getPatientCertificates(
          customGetPatientCertificatesRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      assertAll(
          () -> assertTrue(
              exists(certificates(response.getBody()), certificate(testCertificates))
          ),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
    void shallReturnCertificateIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.getPatientCertificates(
          customGetPatientCertificatesRequest()
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .user(AJLA_DOCTOR_DTO)
              .build()
      );

      assertAll(
          () -> assertTrue(
              exists(certificates(response.getBody()), certificate(testCertificates))
          ),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Vårdadministratör - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfPatientIsProtectedPersonAndUserIsCareAdmin() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.getPatientCertificates(
          customGetPatientCertificatesRequest()
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build()
      );

      assertTrue(certificates(response.getBody()).isEmpty());
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getPatientCertificates(
          customGetPatientCertificatesRequest()
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .unit(ALFA_VARDCENTRAL_DTO)
              .build()
      );

      assertTrue(certificates(response.getBody()).isEmpty());
    }
  }

  @Nested
  @DisplayName("FK7211 - Hämta ej signerade utkast sökkriterier")
  class GetUnitCertificatesInfo {

    @Test
    @DisplayName("FK7211 - Returnera lista av personal som har sparat utkast på mottagning")
    void shallReturnAListOfStaffOnTheSameSubUnit() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION),
          defaultTestablilityCertificateRequest(FK7211, VERSION),
          customTestabilityCertificateRequest(FK7211, VERSION)
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build()
      );

      final var response = api.getUnitCertificatesInfo(
          defaultGetUnitCertificatesInfoRequest()
      );

      final var staff = StaffUtil.staff(response.getBody());

      assertAll(
          () -> assertTrue(() -> staff.contains(AJLA_DOKTOR),
              () -> "Expected '%s' in result: '%s'".formatted(AJLA_DOKTOR, staff)),
          () -> assertTrue(() -> staff.contains(ALVA_VARDADMINISTRATOR),
              () -> "Expected '%s' in result: '%s'".formatted(ALVA_VARDADMINISTRATOR, staff)),
          () -> assertEquals(2, staff.size())
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista av personal som har sparat utkast på vårdenhet")
    void shallReturnAListOfStaffOnTheSameCareUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build()
      );

      final var response = api.getUnitCertificatesInfo(
          customGetUnitCertificatesInfoRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var staff = StaffUtil.staff(response.getBody());

      assertAll(
          () -> assertTrue(() -> staff.contains(AJLA_DOKTOR),
              () -> "Expected '%s' in result: '%s'".formatted(AJLA_DOKTOR, staff)),
          () -> assertTrue(() -> staff.contains(ALVA_VARDADMINISTRATOR),
              () -> "Expected '%s' in result: '%s'".formatted(ALVA_VARDADMINISTRATOR, staff)),
          () -> assertEquals(2, staff.size())
      );
    }

    @Test
    @DisplayName("FK7211 - Inkludera inte personal som har sparat utkast på annan mottagning")
    void shallReturnAListOfStaffNotIncludingStaffOnDifferentSubUnit() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION),
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build()
      );

      final var response = api.getUnitCertificatesInfo(
          defaultGetUnitCertificatesInfoRequest()
      );

      final var staff = StaffUtil.staff(response.getBody());

      assertAll(
          () -> assertTrue(() -> staff.contains(AJLA_DOKTOR),
              () -> "Expected '%s' in result: '%s'".formatted(AJLA_DOKTOR, staff)),
          () -> assertFalse(() -> staff.contains(ALVA_VARDADMINISTRATOR),
              () -> "Didnt expect '%s' in result: '%s'".formatted(ALVA_VARDADMINISTRATOR, staff)),
          () -> assertEquals(1, staff.size())
      );
    }

    @Test
    @DisplayName("FK7211 - Inkludera inte personal som har sparat utkast på annan vårdenhet")
    void shallReturnAListOfStaffNotIncludingStaffOnDifferentCareUnit() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION),
          customTestabilityCertificateRequest(FK7211, VERSION)
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .unit(ALFA_VARDCENTRAL_DTO)
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build()
      );

      final var response = api.getUnitCertificatesInfo(
          customGetUnitCertificatesInfoRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var staff = StaffUtil.staff(response.getBody());

      assertAll(
          () -> assertTrue(() -> staff.contains(AJLA_DOKTOR),
              () -> "Expected '%s' in result: '%s'".formatted(AJLA_DOKTOR, staff)),
          () -> assertFalse(() -> staff.contains(ALVA_VARDADMINISTRATOR),
              () -> "Didnt expect '%s' in result: '%s'".formatted(ALVA_VARDADMINISTRATOR, staff)),
          () -> assertEquals(1, staff.size())
      );
    }
  }

  @Nested
  @DisplayName("FK7211 - Hämta ej signerade utkast")
  class GetUnitCertificates {

    @Test
    @DisplayName("FK7211 - Returnera lista med utkast som har sparats på mottagning")
    void shallReturnCertificatesOnTheSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          defaultGetUnitCertificatesRequest()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista med utkast som har sparats på vårdenhet")
    void shallReturnCertificatesOnTheSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista med utkast som har sparats på mottagning inom vårdenhet")
    void shallReturnCertificatesIssuedOnSubUnitOnTheSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera utkast som sparats på annan mottagning")
    void shallNotReturnCertificatesOnDifferentSubUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build()
      );

      final var response = api.getUnitCertificates(
          defaultGetUnitCertificatesRequest()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera utkast som sparats på annan vårdenhet")
    void shallNotReturnCertificatesOnDifferentCareUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_VARDCENTRAL_DTO)
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .build()
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera utkast som sparats på vårdenheten när man är på mottagningen")
    void shallNotReturnCertificatesOnCareUnitWhenOnSubUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista med utkast som har sparats datum efter från och med datum")
    void shallReturnCertificatesSavedAfterFrom() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .from(LocalDateTime.now().minusDays(1))
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera utkast som har sparats datum före från och med datum")
    void shallNotReturnCertificatesSavedBeforeFrom() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .from(LocalDateTime.now().plusDays(1))
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista med utkast som har sparat datum före till och med datum")
    void shallReturnCertificatesSavedBeforeTo() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .to(LocalDateTime.now().plusDays(1))
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera utkast som har sparats datum efter till och med datum")
    void shallNotReturnCertificatesSavedAfterTo() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .to(LocalDateTime.now().minusDays(1))
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista med utkast som har sparats på patienten")
    void shallReturnCertificatesSavedOnPatient() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .personId(
                          PersonIdDTO.builder()
                              .id(ATHENA_REACT_ANDERSSON_DTO.getId().getId())
                              .type(ATHENA_REACT_ANDERSSON_DTO.getId().getType().name())
                              .build()
                      )
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera utkast som har sparats på annan patient")
    void shallNotReturnCertificatesSavedOnDifferentPatient() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .personId(
                          PersonIdDTO.builder()
                              .id(ALVE_REACT_ALFREDSSON_DTO.getId().getId())
                              .type(ALVE_REACT_ALFREDSSON_DTO.getId().getType().name())
                              .build()
                      )
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista med utkast som har sparats av vald användare")
    void shallReturnCertificatesSavedBySameStaff() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .issuedByStaffId(AJLA_DOCTOR_DTO.getId())
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera utkast som har sparats av annan användare")
    void shallNotReturnCertificatesSavedByDifferentStaff() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .issuedByStaffId(ALVA_VARDADMINISTRATOR_DTO.getId())
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera utkast som har annan status")
    void shallNotReturnCertificatesWithDifferentStatus() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(CertificateStatusTypeDTO.LOCKED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera utkast som har signerad status")
    void shallNotReturnCertificatesWithSignedStatus() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .fillType(TestabilityFillTypeDTO.MAXIMAL)
              .build()
      );

      api.signCertificate(
          defaultSignCertificateRequest(),
          certificateId(testCertificates),
          version(testCertificates)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera utkast som inte är färdiga för signering när man söker på ej färdiga")
    void shallReturnDraftsWhichAreNotValid() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .fillType(TestabilityFillTypeDTO.EMPTY)
              .build()
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .validForSign(Boolean.FALSE)
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera utkast som är färdiga för signering när man söker på ej färdiga")
    void shallNotReturnDraftsWhichAreValidWhenQueryingInvalid() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var questionId = "1";
      final var expectedDate = LocalDate.now().plusDays(5);
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, expectedDate))
      );

      api.updateCertificate(
          customUpdateCertificateRequest()
              .user(AJLA_DOCTOR_DTO)
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .validForSign(Boolean.FALSE)
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera utkast som är färdiga för signering när man söker på färdiga")
    void shallReturnDraftsWhichAreValid() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var questionId = "1";
      final var expectedDate = LocalDate.now().plusDays(5);
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, expectedDate))
      );

      api.updateCertificate(
          customUpdateCertificateRequest()
              .user(AJLA_DOCTOR_DTO)
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .validForSign(Boolean.TRUE)
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera utkast som är inte är färdig för signering när man söker på färdiga")
    void shallNotReturnDraftsWhichAreInvalidWhenQueryingValid() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .fillType(TestabilityFillTypeDTO.EMPTY)
              .build()
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                      .validForSign(Boolean.TRUE)
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }
  }

  @Nested
  @DisplayName("FK7211 - Hämta signerade intyg")
  class GetUnitCertificatesWhenSigned {

    @Test
    @DisplayName("FK7211 - Returnera lista med intyg som har utfärdats på mottagning")
    void shallReturnCertificatesOnTheSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista med intyg som har utfärdats på vårdenhet")
    void shallReturnCertificatesOnTheSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION, SIGNED)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista med intyg som har utfärdats på mottagning inom vårdenhet")
    void shallReturnCertificatesIssuedOnSubUnitOnTheSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera intyg som utfärdats på annan mottagning")
    void shallNotReturnCertificatesOnDifferentSubUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build()
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera intyg som utfärdats på annan vårdenhet")
    void shallNotReturnCertificatesOnDifferentCareUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_VARDCENTRAL_DTO)
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .build()
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera intyg som utfärdats på vårdenheten när man är på mottagningen")
    void shallNotReturnCertificatesOnCareUnitWhenOnSubUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista med intyg som har signerats datum efter från och med datum")
    void shallReturnCertificatesSavedAfterFrom() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .from(LocalDateTime.now().minusDays(1))
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera intyg som har signerats datum före från och med datum")
    void shallNotReturnCertificatesSavedBeforeFrom() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .from(LocalDateTime.now().plusDays(1))
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista med intyg som har signerats datum före till och med datum")
    void shallReturnCertificatesSavedBeforeTo() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .to(LocalDateTime.now().plusDays(1))
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera intyg som har signerats datum efter till och med datum")
    void shallNotReturnCertificatesSavedAfterTo() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .to(LocalDateTime.now().minusDays(1))
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista med intyg som har utfärdats på patienten")
    void shallReturnCertificatesSavedOnPatient() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .personId(
                          PersonIdDTO.builder()
                              .id(ATHENA_REACT_ANDERSSON_DTO.getId().getId())
                              .type(ATHENA_REACT_ANDERSSON_DTO.getId().getType().name())
                              .build()
                      )
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera intyg som har utfärdats på annan patient")
    void shallNotReturnCertificatesSavedOnDifferentPatient() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .personId(
                          PersonIdDTO.builder()
                              .id(ALVE_REACT_ALFREDSSON_DTO.getId().getId())
                              .type(ALVE_REACT_ALFREDSSON_DTO.getId().getType().name())
                              .build()
                      )
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Returnera lista med intyg som har signerats av vald användare")
    void shallReturnCertificatesSavedBySameStaff() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .issuedByStaffId(AJLA_DOCTOR_DTO.getId())
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertAll(
          () -> assertTrue(
              () -> exists(certificates(response.getBody()), certificate(testCertificates)),
              () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                  certificates(response.getBody()))),
          () -> assertEquals(1, certificates(response.getBody()).size())
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera intyg som har signerats av annan användare")
    void shallNotReturnCertificatesSavedByDifferentStaff() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .issuedByStaffId(ALVA_VARDADMINISTRATOR_DTO.getId())
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }

    @Test
    @DisplayName("FK7211 - Ej returnera intyg som inte har signerats")
    void shallNotReturnCertificatesWithDifferentStatus() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getUnitCertificates(
          customGetUnitCertificatesRequest()
              .queryCriteria(
                  CertificatesQueryCriteriaDTO.builder()
                      .statuses(List.of(SIGNED))
                      .build()
              )
              .build()
      );

      assertEquals(0, certificates(response.getBody()).size(),
          "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
      );
    }
  }

  @Nested
  @DisplayName("FK7211 - Validera utkast")
  class ValidateCertificate {

    @Test
    @DisplayName("FK7211 - Om utkastet innehåller korrekt 'beräknad nedkomst' skall utkastet vara klar för signering")
    void shallReturnEmptyListOfErrorsIfDateIsCorrect() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var questionId = "1";
      final var date = LocalDate.now().plusDays(5);
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, date))
      );

      final var response = api.validateCertificate(
          customValidateCertificateRequest()
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(Collections.emptyList(), validationErrors(response));
    }

    @Test
    @DisplayName("FK7211 - Om utkastet innehåller 'beräknad nedkomst' före dagens datum skall valideringsfel returneras")
    void shallReturnListOfErrorsIfDateIsBeforeTodaysDate() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var questionId = "1";
      final var date = LocalDate.now().minusDays(1);
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, date))
      );

      final var response = api.validateCertificate(
          customValidateCertificateRequest()
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals(1, validationErrors(response).size(),
              () -> "Wrong number of errors '%s'".formatted(validationErrors(response))
          ),
          () -> assertTrue(validationErrors(response).get(0).getText()
                  .contains("Ange ett datum som är tidigast"),
              () -> "Expect to contain 'Ange ett datum som är tidigast' but was '%s'"
                  .formatted(validationErrors(response).get(0))
          )
      );
    }

    @Test
    @DisplayName("FK7211 - Om utkastet innehåller 'beräknad nedkomst' längre än ett år fram skall valideringsfel returneras")
    void shallReturnListOfErrorsIfDateIsAfterOneYearInFuture() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var questionId = "1";
      final var date = LocalDate.now().plusYears(1).plusDays(1);
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, date))
      );

      final var response = api.validateCertificate(
          customValidateCertificateRequest()
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals(1, validationErrors(response).size(),
              () -> "Wrong number of errors '%s'".formatted(validationErrors(response))
          ),
          () -> assertTrue(validationErrors(response).get(0).getText()
                  .contains("Ange ett datum som är senast"),
              () -> "Expect to contain 'Ange ett datum som är senast' but was '%s'"
                  .formatted(validationErrors(response).get(0))
          )
      );
    }

    @Test
    @DisplayName("FK7211 - Om utkastet saknar 'beräknad nedkomst' skall valideringsfel returneras")
    void shallReturnListOfErrorsIfDateIsAMissing() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var questionId = "1";
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, null))
      );

      final var response = api.validateCertificate(
          customValidateCertificateRequest()
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals(1, validationErrors(response).size(),
              () -> "Wrong number of errors '%s'".formatted(validationErrors(response))
          ),
          () -> assertTrue(validationErrors(response).get(0).getText()
                  .contains("Ange ett datum."),
              () -> "Expect to contain 'Ange ett datum.' but was '%s'"
                  .formatted(validationErrors(response).get(0))
          )
      );
    }

    @Test
    @DisplayName("FK7211 - Om utkastet saknar 'Vårdenhetens adress' skall valideringsfel returneras")
    void shallReturnListOfErrorsIfMissingUnitContactAddress() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var certificate = updateUnit(
          testCertificates,
          certificate(testCertificates).getMetadata().getUnit()
              .withAddress("")
      );

      final var questionId = "1";
      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, LocalDate.now()
              )
          )
      );

      final var response = api.validateCertificate(
          customValidateCertificateRequest()
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals(1, validationErrors(response).size(),
              () -> "Wrong number of errors '%s'".formatted(validationErrors(response))
          ),
          () -> assertTrue(validationErrors(response).get(0).getText()
                  .contains("Ange postadress."),
              () -> "Expect to contain 'Ange postadress.' but was '%s'"
                  .formatted(validationErrors(response).get(0))
          )
      );
    }

    @Test
    @DisplayName("FK7211 - Om utkastet saknar 'Vårdenhetens postnummer' skall valideringsfel returneras")
    void shallReturnListOfErrorsIfMissingUnitContactZipCode() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var certificate = updateUnit(
          testCertificates,
          certificate(testCertificates).getMetadata().getUnit()
              .withZipCode("")
      );

      final var questionId = "1";
      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, LocalDate.now()
              )
          )
      );

      final var response = api.validateCertificate(
          customValidateCertificateRequest()
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals(1, validationErrors(response).size(),
              () -> "Wrong number of errors '%s'".formatted(validationErrors(response))
          ),
          () -> assertTrue(validationErrors(response).get(0).getText()
                  .contains("Ange postnummer."),
              () -> "Expect to contain 'Ange postnummer.' but was '%s'"
                  .formatted(validationErrors(response).get(0))
          )
      );
    }

    @Test
    @DisplayName("FK7211 - Om utkastet saknar 'Vårdenhetens postort' skall valideringsfel returneras")
    void shallReturnListOfErrorsIfMissingUnitContactCity() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var certificate = updateUnit(
          testCertificates,
          certificate(testCertificates).getMetadata().getUnit()
              .withCity("")
      );

      final var questionId = "1";
      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, LocalDate.now()
              )
          )
      );

      final var response = api.validateCertificate(
          customValidateCertificateRequest()
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals(1, validationErrors(response).size(),
              () -> "Wrong number of errors '%s'".formatted(validationErrors(response))
          ),
          () -> assertTrue(validationErrors(response).get(0).getText()
                  .contains("Ange postort."),
              () -> "Expect to contain 'Ange postort.' but was '%s'"
                  .formatted(validationErrors(response).get(0))
          )
      );
    }

    @Test
    @DisplayName("FK7211 - Om utkastet saknar 'Vårdenhetens telefonnummer' skall valideringsfel returneras")
    void shallReturnListOfErrorsIfMissingUnitContactPhoneNumber() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var certificate = updateUnit(
          testCertificates,
          certificate(testCertificates).getMetadata().getUnit()
              .withPhoneNumber("")
      );

      final var questionId = "1";
      Objects.requireNonNull(
          certificate.getData().put(
              questionId,
              updateDateValue(certificate, questionId, LocalDate.now()
              )
          )
      );

      final var response = api.validateCertificate(
          customValidateCertificateRequest()
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals(1, validationErrors(response).size(),
              () -> "Wrong number of errors '%s'".formatted(validationErrors(response))
          ),
          () -> assertTrue(validationErrors(response).get(0).getText()
                  .contains("Ange telefonnummer."),
              () -> "Expect to contain 'Ange telefonnummer.' but was '%s'"
                  .formatted(validationErrors(response).get(0))
          )
      );
    }
  }

  @Nested
  @DisplayName("FK7211 - Hämta intygsxml")
  class GetCertificateXml {

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på samma mottagning skall det returneras")
    void shallReturnCertificateXMLIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getCertificateXml(
          defaultGetCertificateXmlRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertTrue(decodeXml(certificateXmlReponse(response).getXml()).contains("Läkare"),
              () -> "Expected 'Läkare' to be part of xml: '%s'"
                  .formatted(decodeXml(certificateXmlReponse(response).getXml()))),
          () -> assertEquals(certificate(testCertificates).getMetadata().getId(),
              certificateXmlReponse(response).getCertificateId()),
          () -> assertEquals(certificate(testCertificates).getMetadata().getVersion(),
              certificateXmlReponse(response).getVersion())
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det returneras")
    void shallReturnCertificateXMLIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getCertificateXml(
          customGetCertificateXmlRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertTrue(decodeXml(certificateXmlReponse(response).getXml()).contains("Läkare"),
              () -> "Expected 'Läkare' to be part of xml: '%s'"
                  .formatted(decodeXml(certificateXmlReponse(response).getXml()))),
          () -> assertEquals(certificate(testCertificates).getMetadata().getId(),
              certificateXmlReponse(response).getCertificateId()),
          () -> assertEquals(certificate(testCertificates).getMetadata().getVersion(),
              certificateXmlReponse(response).getVersion())
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på samma vårdenhet skall det returneras")
    void shallReturnCertificateXMLIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.getCertificateXml(
          customGetCertificateXmlRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertTrue(decodeXml(certificateXmlReponse(response).getXml()).contains("Läkare"),
              () -> "Expected 'Läkare' to be part of xml: '%s'"
                  .formatted(decodeXml(certificateXmlReponse(response).getXml()))),
          () -> assertEquals(certificate(testCertificates).getMetadata().getId(),
              certificateXmlReponse(response).getCertificateId()),
          () -> assertEquals(certificate(testCertificates).getMetadata().getVersion(),
              certificateXmlReponse(response).getVersion())
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getCertificateXml(
          customGetCertificateXmlRequest()
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.getCertificateXml(
          customGetCertificateXmlRequest()
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .unit(ALFA_VARDCENTRAL_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Vårdadministratör - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfPatientIsProtectedPersonAndUserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.getCertificateXml(
          customGetCertificateXmlRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
    void shallReturnCertificateXMLIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.getCertificateXml(
          defaultGetCertificateXmlRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertTrue(decodeXml(certificateXmlReponse(response).getXml()).contains("Läkare"),
              () -> "Expected 'Läkare' to be part of xml: '%s'"
                  .formatted(decodeXml(certificateXmlReponse(response).getXml()))),
          () -> assertEquals(certificate(testCertificates).getMetadata().getId(),
              certificateXmlReponse(response).getCertificateId()),
          () -> assertEquals(certificate(testCertificates).getMetadata().getVersion(),
              certificateXmlReponse(response).getVersion())
      );
    }
  }

  @Nested
  @DisplayName("FK7211 - Signera")
  class SignCertificate {

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på samma mottagning skall det gå att signera")
    void shallSuccessfullySignIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.signCertificate(
          defaultSignCertificateRequest(),
          certificateId(testCertificates),
          version(testCertificates)
      );

      assertAll(
          () -> assertEquals(SIGNED,
              certificate(response).getMetadata().getStatus()
          )
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det gå att signera")
    void shallSuccessfullySignIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.signCertificate(
          customSignCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates),
          version(testCertificates)
      );

      assertAll(
          () -> assertEquals(SIGNED,
              certificate(response).getMetadata().getStatus()
          )
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på samma vårdenhet skall det gå att signera")
    void shallSuccessfullySignIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.signCertificate(
          customSignCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates),
          version(testCertificates)
      );

      assertAll(
          () -> assertEquals(SIGNED,
              certificate(response).getMetadata().getStatus()
          )
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.signCertificate(
          customSignCertificateRequest()
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build(),
          certificateId(testCertificates),
          version(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.signCertificate(
          customSignCertificateRequest()
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .unit(ALFA_VARDCENTRAL_DTO)
              .build(),
          certificateId(testCertificates),
          version(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Vårdadministratör - Felkod 403 (FORBIDDEN) returneras")
    void shallReturn403UserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.signCertificate(
          customSignCertificateRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build(),
          certificateId(testCertificates),
          version(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
    void shallSuccessfullySignIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.signCertificate(
          defaultSignCertificateRequest(),
          certificateId(testCertificates),
          version(testCertificates)
      );

      assertAll(
          () -> assertEquals(SIGNED, certificate(response).getMetadata().getStatus()
          )
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget inte är klart för signering skall felkod 400 (BAD_REQUEST) returneras")
    void shallReturn403IfCertificateNotValid() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .fillType(TestabilityFillTypeDTO.EMPTY)
              .build()
      );

      final var response = api.signCertificate(
          defaultSignCertificateRequest(),
          certificateId(testCertificates),
          version(testCertificates)
      );

      assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om signatur saknas skall felkod 400 (BAD_REQUEST) returneras")
    void shallReturn403IfSignatureNotValid() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.signCertificate(
          customSignCertificateRequest()
              .signatureXml(null)
              .build(),
          certificateId(testCertificates),
          version(testCertificates)
      );

      assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om man försöker signera med en inaktuell revision skall felkod 400 (BAD_REQUEST) returneras")
    void shallReturn403IfVersionNotValid() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.signCertificate(
          customSignCertificateRequest()
              .signatureXml(null)
              .build(),
          certificateId(testCertificates),
          version(testCertificates) + 1L
      );

      assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om intyget signeras ska ett meddelande läggas på AMQn")
    void shallSuccessfullyAddMessageOfSigningOnAMQ() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      api.signCertificate(
          customSignCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates),
          version(testCertificates)
      );

      assertAll(
          () -> waitAtMost(Duration.ofSeconds(5))
              .untilAsserted(() -> assertEquals(1, testListener.messages.size())),
          () -> assertEquals(
              certificateId(testCertificates),
              testListener.messages.get(0).getStringProperty("certificateId")
          ),
          () -> assertEquals(
              "certificate-sign",
              testListener.messages.get(0).getStringProperty("eventType")
          )
      );
    }
  }

  @Nested
  @DisplayName("FK7211 - Skicka")
  class SendCertificate {

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på samma mottagning skall det gå att skicka")
    void shallSuccessfullySendIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals("FKASSA", recipient(response).getId()),
          () -> assertEquals("Försäkringskassan", recipient(response).getName()),
          () -> assertNotNull(recipient(response).getSent())
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det gå att skicka")
    void shallSuccessfullySendIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals("FKASSA", recipient(response).getId()),
          () -> assertEquals("Försäkringskassan", recipient(response).getName()),
          () -> assertNotNull(recipient(response).getSent())
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på samma vårdenhet skall det gå att skicka")
    void shallSuccessfullySendIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION, SIGNED)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.sendCertificate(
          customSendCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals("FKASSA", recipient(response).getId()),
          () -> assertEquals("Försäkringskassan", recipient(response).getName()),
          () -> assertNotNull(recipient(response).getSent())
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget skickas ska ett meddelande läggas på AMQn")
    void shallSuccessfullyAddMessageToAMQWhenSendingCertificate() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION, SIGNED)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      api.sendCertificate(
          customSendCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> waitAtMost(Duration.ofSeconds(5))
              .untilAsserted(() -> assertEquals(1, testListener.messages.size())),
          () -> assertEquals(
              certificateId(testCertificates),
              testListener.messages.get(0).getStringProperty("certificateId")
          ),
          () -> assertEquals(
              "certificate-sent",
              testListener.messages.get(0).getStringProperty("eventType")
          )
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.sendCertificate(
          customSendCertificateRequest()
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.sendCertificate(
          customSendCertificateRequest()
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .unit(ALFA_VARDCENTRAL_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Vårdadministratör - Felkod 403 (FORBIDDEN) returneras")
    void shallReturn403UserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION, SIGNED)
      );

      final var response = api.sendCertificate(
          customSendCertificateRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det gå att skicka")
    void shallSuccessfullySendIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7211, VERSION, SIGNED)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals("FKASSA", recipient(response).getId()),
          () -> assertEquals("Försäkringskassan", recipient(response).getName()),
          () -> assertNotNull(recipient(response).getSent())
      );
    }

    @Test
    @DisplayName("FK7211 - Om intyget inte är signerat skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfCertificateNotSigned() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      final var response = api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7211 - Om intyget redan är skickat skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfCertificateIsAlreadySent() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      final var response = api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }
  }

  @Nested
  @DisplayName("FK7211 - Intern api för Intygstjänsten")
  class InternalApi {

    @Test
    @DisplayName("FK7211 - Signerat intyg skall gå att hämta från intern api:et")
    void shallReturnSignedCertificate() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7211, VERSION)
      );

      api.signCertificate(
          defaultSignCertificateRequest(),
          certificateId(testCertificates),
          version(testCertificates)
      );

      final var response = internalApi.getCertificateXml(certificateId(testCertificates));

      assertAll(
          () -> assertEquals(certificateId(testCertificates),
              certificateInternalXmlResponse(response).getCertificateId()),
          () -> assertEquals(FK7211, certificateInternalXmlResponse(response).getCertificateType()),
          () -> assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ID,
              certificateInternalXmlResponse(response).getUnitId()),
          () -> assertTrue(decodeXml(certificateInternalXmlResponse(response).getXml()).contains(
                  certificateId(testCertificates)),
              () -> "Expected 'Läkare' to be part of xml: '%s'"
                  .formatted(decodeXml(certificateInternalXmlResponse(response).getXml())))
      );
    }
  }
}
