package se.inera.intyg.certificateservice.integrationtest.fk7472;

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
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.BETA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.BERTIL_BARNMORSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.DAN_DENTIST_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CertificateModelFactoryFK7472.QUESTION_PERIOD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CertificateModelFactoryFK7472.QUESTION_SYMPTOM_ID;
import static se.inera.intyg.certificateservice.integrationtest.fk7472.FK7472Constants.FK7472;
import static se.inera.intyg.certificateservice.integrationtest.fk7472.FK7472Constants.VERSION;
import static se.inera.intyg.certificateservice.integrationtest.fk7472.FK7472Constants.WRONG_VERSION;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customDeleteCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetCertificatePdfRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetCertificateXmlRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetPatientCertificatesRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetUnitCertificatesInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetUnitCertificatesRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customRenewCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customReplaceCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customRevokeCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customUpdateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customValidateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultComplementCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultDeleteCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificatePdfRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateXmlRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetPatientCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetUnitCertificatesInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetUnitCertificatesRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultRenewCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultReplaceCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultRevokeCertificateRequest;
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
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.getValueDateRangeList;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.getValueText;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.hasQuestions;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.pdfData;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.questions;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.recipient;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.relation;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.renewCertificateResponse;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.replaceCertificateResponse;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.revokeCertificateResponse;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.status;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.updateDateRangeListValue;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.updateTextValue;
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
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateRange;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.AccessScopeTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.application.unit.dto.CertificatesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType;
import se.inera.intyg.certificateservice.integrationtest.util.ApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil;
import se.inera.intyg.certificateservice.integrationtest.util.Containers;
import se.inera.intyg.certificateservice.integrationtest.util.InternalApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.StaffUtil;
import se.inera.intyg.certificateservice.integrationtest.util.TestListener;
import se.inera.intyg.certificateservice.integrationtest.util.TestabilityApiUtil;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;


@ActiveProfiles({"integration-test", TESTABILITY_PROFILE})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FK7472ActiveIT {

  @LocalServerPort
  private int port;

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.fk7472.v1_0.active.from", () -> "2024-01-01T00:00:00");
  }

  private final TestRestTemplate restTemplate;
  private static final TestListener testListener = new TestListener();

  private ApiUtil api;
  private InternalApiUtil internalApi;
  private TestabilityApiUtil testabilityApi;

  @Autowired
  public FK7472ActiveIT(TestRestTemplate restTemplate) {
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
  @DisplayName("FK7472 - Hämta intygstyp när den är aktiv")
  class GetCertificateTypeInfo {

    @Test
    @DisplayName("FK7472 - Om aktiverad ska intygstypen returneras i listan av tillgängliga intygstyper")
    void shallReturnFK7472WhenActive() {
      final var response = api.certificateTypeInfo(
          defaultCertificateTypeInfoRequest()
      );

      assertNotNull(
          certificateTypeInfo(response.getBody(), FK7472),
          "Should contain %s as it is active!".formatted(FK7472)
      );
    }

    @Test
    @DisplayName("FK7272 - Om användaren har rollen tandläkare ska intygstypen inte returneras i listan av tillgängliga intygstyper")
    void shallNotReturnFK7210WhenUserIsDoctor() {
      final var response = api.certificateTypeInfo(
          customCertificateTypeInfoRequest()
              .user(DAN_DENTIST_DTO)
              .build()
      );

      assertNull(
          certificateTypeInfo(response.getBody(), FK7472),
          "Should not contain %s as user is dentist!".formatted(FK7472)
      );
    }

    @Test
    @DisplayName("FK7472 - Om aktiverad ska 'Skapa utkast' vara tillgänglig")
    void shallReturnResourceLinkCreateCertificate() {
      final var response = api.certificateTypeInfo(
          defaultCertificateTypeInfoRequest()
      );

      final var resourceLink = resourceLink(certificateTypeInfo(response.getBody(), FK7472),
          ResourceLinkTypeDTO.CREATE_CERTIFICATE);

      assertNotNull(resourceLink,
          "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE));
      assertTrue(resourceLink.isEnabled(), "Should be enabled");
    }

    @Test
    @DisplayName("FK7472 - Om patienten är avliden ska inte 'Skapa utkast' vara tillgänglig")
    void shallNotReturnResourceLinkCreateCertificateIfPatientIsDeceased() {
      final var response = api.certificateTypeInfo(
          customCertificateTypeInfoRequest()
              .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
              .build()
      );

      final var resourceLink = resourceLink(certificateTypeInfo(response.getBody(), FK7472),
          ResourceLinkTypeDTO.CREATE_CERTIFICATE);

      assertNotNull(resourceLink,
          "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE));
      assertFalse(resourceLink.isEnabled(), "Should be disabled");
    }

    @Test
    @DisplayName("FK7472 - Om användaren är blockerad ska inte 'Skapa utkast' vara tillgänglig")
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

      final var resourceLink = resourceLink(certificateTypeInfo(response.getBody(), FK7472),
          ResourceLinkTypeDTO.CREATE_CERTIFICATE);

      assertNotNull(resourceLink,
          "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE));
      assertFalse(resourceLink.isEnabled(), "Should be disabled");
    }

    @Test
    @DisplayName("FK7472 - Om användaren är blockerad och patienten avliden ska inte 'Skapa utkast' vara tillgänglig")
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

      final var resourceLink = resourceLink(certificateTypeInfo(response.getBody(), FK7472),
          ResourceLinkTypeDTO.CREATE_CERTIFICATE);

      assertNotNull(resourceLink,
          "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE));
      assertFalse(resourceLink.isEnabled(), "Should be disabled");
    }

    @Test
    @DisplayName("FK7472 - Vårdadmininstratör - Om patienten har skyddade personuppgifter ska inte 'Skapa utkast' vara tillgänglig")
    void shallNotReturnResourceLinkCreateCertificateIfUserIsCareAdminAndPatientIsProtected() {
      final var response = api.certificateTypeInfo(
          customCertificateTypeInfoRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var resourceLink = resourceLink(certificateTypeInfo(response.getBody(), FK7472),
          ResourceLinkTypeDTO.CREATE_CERTIFICATE);

      assertNotNull(resourceLink,
          "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE));
      assertFalse(resourceLink.isEnabled(), "Should be disabled");
    }

    @Test
    @DisplayName("FK7472 - Läkare - Om patienten har skyddade personuppgifter ska 'Skapa utkast' vara tillgänglig")
    void shallReturnResourceLinkCreateCertificateIfUserIsDoctorAndPatientIsProtected() {
      final var response = api.certificateTypeInfo(
          customCertificateTypeInfoRequest()
              .user(AJLA_DOCTOR_DTO)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var resourceLink = resourceLink(certificateTypeInfo(response.getBody(), FK7472),
          ResourceLinkTypeDTO.CREATE_CERTIFICATE);

      assertNotNull(resourceLink,
          "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE));
      assertTrue(resourceLink.isEnabled(), "Should be enabled");
    }
  }

  @Nested
  @DisplayName("FK7472 - Aktiva versioner")
  class ExistsCertificateTypeInfo {

    @Test
    @DisplayName("FK7472 - Aktiv version skall vara 1.0")
    void shallReturnLatestVersionWhenTypeExists() {
      final var expectedCertificateModelId = CertificateModelIdDTO.builder()
          .type(FK7472)
          .version(VERSION)
          .build();

      final var response = api.findLatestCertificateTypeVersion(FK7472);

      assertEquals(
          expectedCertificateModelId,
          certificateModelId(response.getBody())
      );
    }
  }

  @Nested
  @DisplayName("FK7472 - Skapa utkast")
  class CreateCertificate {

    @Test
    @DisplayName("FK7472 - Om utkastet framgångsrikt skapats skall utkastet returneras")
    void shallReturnCertificateWhenActive() {
      final var response = api.createCertificate(
          defaultCreateCertificateRequest(FK7472, VERSION)
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate as it is active!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om patienten är avliden skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403PatientIsDeceased() {
      final var response = api.createCertificate(
          customCreateCertificateRequest(FK7472, VERSION)
              .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
              .build()
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om användaren är blockerad skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403UserIsBlocked() {
      final var response = api.createCertificate(
          customCreateCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om patient är avliden och användaren är blockerad skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403PatientIsDeceasedAndUserIsBlocked() {
      final var response = api.createCertificate(
          customCreateCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Läkare - Om patienten har skyddade personuppgifter skall utkastet returneras")
    void shallReturnCertificateIfPatientIsProtectedPersonAndUserDoctor() {
      final var response = api.createCertificate(
          customCreateCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Vårdadministratör - Om patienten har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403PatientIsProtectedPersonAndUserDoctor() {
      final var response = api.createCertificate(
          customCreateCertificateRequest(FK7472, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build()
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om den efterfrågade versionen inte stöds skall felkod 400 (BAD_REQUEST) returneras")
    void shallReturn400IfVersionNotSupported() {
      final var response = api.createCertificate(
          defaultCreateCertificateRequest(FK7472, WRONG_VERSION)
      );

      assertEquals(400, response.getStatusCode().value());
    }
  }

  @Nested
  @DisplayName("FK7472 - Finns intyget i tjänsten")
  class ExistsCertificate {

    @Test
    @DisplayName("FK7472 - Om intyget finns så returneras true")
    void shallReturnTrueIfCertificateExists() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget inte finns lagrat så returneras false")
    void shallReturnFalseIfCertificateDoesnt() {
      final var response = api.certificateExists("certificate-not-exists");

      assertFalse(
          exists(response.getBody()),
          "Should return false when certificate doesnt exists!"
      );
    }
  }

  @Nested
  @DisplayName("FK7472 - Hämta intyg")
  class GetCertificate {

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma mottagning skall det returneras")
    void shallReturnCertificateIfUnitIsSubUnitAndOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det returneras")
    void shallReturnCertificateIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på samma vårdenhet skall det returneras")
    void shallReturnCertificateIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var response = api.getCertificate(
          customGetCertificateRequest().unit(ALFA_HUDMOTTAGNINGEN_DTO).build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Vårdadministratör - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfPatientIsProtectedPersonAndUserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
    void shallReturnCertificateIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
  @DisplayName("FK7472 - Uppdatera svarsalternativ")
  class UpdateCertificate {

    @Test
    @DisplayName("FK7472 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall svarsalternativ uppdateras")
    void shallUpdateDataIfUserIsDoctorAndPatientIsProtectedPerson() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );
      final var expectedText = "Ett nytt exempel på ett svar.";
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), expectedText))
      );

      final var response = api.updateCertificate(
          customUpdateCertificateRequest()
              .user(AJLA_DOCTOR_DTO)
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(expectedText, getValueText(response, QUESTION_SYMPTOM_ID.id()));
    }

    @Test
    @DisplayName("FK7472 - Vårdadmin - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallNotUpdateDataIfUserIsCareAdminAndPatientIsProtectedPerson() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall svarsalternativ uppdateras")
    void shallUpdateDataIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var expectedText = "Ett nytt exempel på ett svar.";
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), expectedText))
      );

      final var response = api.updateCertificate(
          customUpdateCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(expectedText, getValueText(response, QUESTION_SYMPTOM_ID.id()));
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma mottagning skall svarsalternativ uppdateras")
    void shallUpdateDataIfUnitIsSubUnitAndOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var expectedText = "Ett nytt exempel på ett svar.";
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), expectedText))
      );

      final var response = api.updateCertificate(
          customUpdateCertificateRequest()
              .certificate(certificate)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(expectedText, getValueText(response, QUESTION_SYMPTOM_ID.id())
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma vårdenhet skall svarsalternativ uppdateras")
    void shallUpdateDataIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var expectedText = "Ett nytt exempel på ett svar.";
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), expectedText))
      );

      final var response = api.updateCertificate(
          customUpdateCertificateRequest()
              .certificate(certificate)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(expectedText, getValueText(response, QUESTION_SYMPTOM_ID.id()));
    }

    @Test
    @DisplayName("FK7472 - Om intyget får ett uppdaterat värde för period ska svarsalternativ uppdateras")
    void shallUpdateDataForPeriod() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var expectedData = List.of(
          CertificateDataValueDateRange.builder()
              .id("HALVA")
              .to(LocalDate.now())
              .from(LocalDate.now().minusDays(10))
              .build()
      );
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_PERIOD_ID.id(),
              updateDateRangeListValue(certificate, QUESTION_PERIOD_ID.id(), expectedData))
      );

      final var response = api.updateCertificate(
          customUpdateCertificateRequest()
              .certificate(certificate)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(expectedData, getValueDateRangeList(response, QUESTION_PERIOD_ID.id()));
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om utkastet sparas med en inaktuell revision av utkastet skall felkod 409 (CONFLICT) returneras")
    void shallNotUpdateDataIfUserIsTryingToSaveWithAnOldRevision() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
  @DisplayName("FK7472 - Ta bort utkast")
  class DeleteCertificate {

    @Test
    @DisplayName("FK7472 - Om utkastet är skapat på samma mottagning skall det gå att tas bort")
    void shallDeleteCertificateIfCertificateIsOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om utkastet är skapat på samma vårdenhet skall det gå att tas bort")
    void shallDeleteCertificateIfCertificateIsOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om utkastet är skapat på annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om utkastet är skapat på annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Vårdadmin - Om utkastet är skapat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallNotDeleteDataIfUserIsCareAdminAndPatientIsProtectedPerson() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
  @DisplayName("FK7472 - Hämta tidigare intyg för patient")
  class GetPatientCertificates {

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma mottagning skall det returneras")
    void shallReturnCertificateIfUnitIsSubUnitAndOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på samma vårdenhet skall det returneras")
    void shallReturnCertificateIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det returneras")
    void shallReturnCertificateIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
    void shallReturnCertificateIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Vårdadministratör - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfPatientIsProtectedPersonAndUserIsCareAdmin() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
  @DisplayName("FK7472 - Hämta ej signerade utkast sökkriterier")
  class GetUnitCertificatesInfo {

    @Test
    @DisplayName("FK7472 - Returnera lista av personal som har sparat utkast på mottagning")
    void shallReturnAListOfStaffOnTheSameSubUnit() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION),
          defaultTestablilityCertificateRequest(FK7472, VERSION),
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Returnera lista av personal som har sparat utkast på vårdenhet")
    void shallReturnAListOfStaffOnTheSameCareUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          customTestabilityCertificateRequest(FK7472, VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Inkludera inte personal som har sparat utkast på annan mottagning")
    void shallReturnAListOfStaffNotIncludingStaffOnDifferentSubUnit() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION),
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Inkludera inte personal som har sparat utkast på annan vårdenhet")
    void shallReturnAListOfStaffNotIncludingStaffOnDifferentCareUnit() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION),
          customTestabilityCertificateRequest(FK7472, VERSION)
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
  @DisplayName("FK7472 - Hämta ej signerade utkast")
  class GetUnitCertificates {

    @Test
    @DisplayName("FK7472 - Returnera lista med utkast som har sparats på mottagning")
    void shallReturnCertificatesOnTheSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Returnera lista med utkast som har sparats på vårdenhet")
    void shallReturnCertificatesOnTheSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Returnera lista med utkast som har sparats på mottagning inom vårdenhet")
    void shallReturnCertificatesIssuedOnSubUnitOnTheSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera utkast som sparats på annan mottagning")
    void shallNotReturnCertificatesOnDifferentSubUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera utkast som sparats på annan vårdenhet")
    void shallNotReturnCertificatesOnDifferentCareUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera utkast som sparats på vårdenheten när man är på mottagningen")
    void shallNotReturnCertificatesOnCareUnitWhenOnSubUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Returnera lista med utkast som har sparats datum efter från och med datum")
    void shallReturnCertificatesSavedAfterFrom() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera utkast som har sparats datum före från och med datum")
    void shallNotReturnCertificatesSavedBeforeFrom() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Returnera lista med utkast som har sparat datum före till och med datum")
    void shallReturnCertificatesSavedBeforeTo() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera utkast som har sparats datum efter till och med datum")
    void shallNotReturnCertificatesSavedAfterTo() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Returnera lista med utkast som har sparats på patienten")
    void shallReturnCertificatesSavedOnPatient() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera utkast som har sparats på annan patient")
    void shallNotReturnCertificatesSavedOnDifferentPatient() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Returnera lista med utkast som har sparats av vald användare")
    void shallReturnCertificatesSavedBySameStaff() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera utkast som har sparats av annan användare")
    void shallNotReturnCertificatesSavedByDifferentStaff() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera utkast som har annan status")
    void shallNotReturnCertificatesWithDifferentStatus() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera utkast som har signerad status")
    void shallNotReturnCertificatesWithSignedStatus() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Returnera utkast som inte är färdiga för signering när man söker på ej färdiga")
    void shallReturnDraftsWhichAreNotValid() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera utkast som är färdiga för signering när man söker på ej färdiga")
    void shallNotReturnDraftsWhichAreValidWhenQueryingInvalid() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var expectedText = "Ett nytt exempel på ett svar.";
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), expectedText))
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
    @DisplayName("FK7472 - Returnera utkast som är färdiga för signering när man söker på färdiga")
    void shallReturnDraftsWhichAreValid() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var expectedText = "Ett nytt exempel på ett svar.";
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), expectedText))
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
    @DisplayName("FK7472 - Ej returnera utkast som är inte är färdig för signering när man söker på färdiga")
    void shallNotReturnDraftsWhichAreInvalidWhenQueryingValid() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
  @DisplayName("FK7472 - Hämta signerade intyg")
  class GetUnitCertificatesWhenSigned {

    @Test
    @DisplayName("FK7472 - Returnera lista med intyg som har utfärdats på mottagning")
    void shallReturnCertificatesOnTheSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Returnera lista med intyg som har utfärdats på vårdenhet")
    void shallReturnCertificatesOnTheSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Returnera lista med intyg som har utfärdats på mottagning inom vårdenhet")
    void shallReturnCertificatesIssuedOnSubUnitOnTheSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Ej returnera intyg som utfärdats på annan mottagning")
    void shallNotReturnCertificatesOnDifferentSubUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera intyg som utfärdats på annan vårdenhet")
    void shallNotReturnCertificatesOnDifferentCareUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera intyg som utfärdats på vårdenheten när man är på mottagningen")
    void shallNotReturnCertificatesOnCareUnitWhenOnSubUnit() {
      testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Returnera lista med intyg som har signerats datum efter från och med datum")
    void shallReturnCertificatesSavedAfterFrom() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Ej returnera intyg som har signerats datum före från och med datum")
    void shallNotReturnCertificatesSavedBeforeFrom() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Returnera lista med intyg som har signerats datum före till och med datum")
    void shallReturnCertificatesSavedBeforeTo() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Ej returnera intyg som har signerats datum efter till och med datum")
    void shallNotReturnCertificatesSavedAfterTo() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Returnera lista med intyg som har utfärdats på patienten")
    void shallReturnCertificatesSavedOnPatient() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Ej returnera intyg som har utfärdats på annan patient")
    void shallNotReturnCertificatesSavedOnDifferentPatient() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Returnera lista med intyg som har signerats av vald användare")
    void shallReturnCertificatesSavedBySameStaff() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Ej returnera intyg som har signerats av annan användare")
    void shallNotReturnCertificatesSavedByDifferentStaff() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Ej returnera intyg som inte har signerats")
    void shallNotReturnCertificatesWithDifferentStatus() {
      testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
  @DisplayName("FK7472 - Validera utkast")
  class ValidateCertificate {

    @Test
    @DisplayName("FK7472 - Om utkastet innehåller korrekta värden skall utkastet vara klar för signering")
    void shallReturnEmptyListOfErrorsIfDateIsCorrect() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var expectedText = "Ett nytt exempel på ett svar.";
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), expectedText))
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
    @DisplayName("FK7472 - Om utkastet innehåller 'symtom' längre än 318 tecken ska ett valideringsmeddelande visas.")
    void shallReturnListOfErrorsIfSymtomIsLongerThanLimit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var expectedText = "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
          + "Ett nytt exempel på ett svar.  Ett nytt exempel på ett svar."
          + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
          + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
          + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
          + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
          + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
          + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
          + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
          + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
          + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar.";
      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), expectedText))
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
                  .contains("Ange en text som inte är längre än"),
              () -> "Expect to contain 'Ange en text som inte är längre än' but was '%s'"
                  .formatted(validationErrors(response).get(0))
          )
      );
    }

    @Test
    @DisplayName("FK7472 - Om utkastet saknar 'symtom' skall valideringsfel returneras")
    void shallReturnListOfErrorsIfTextOfSymtomIsMissing() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), null))
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
                  .contains("Ange ett svar."),
              () -> "Expect to contain 'Ange ett svar.' but was '%s'"
                  .formatted(validationErrors(response).get(0))
          )
      );
    }

    @Test
    @DisplayName("FK7472 - Om utkastet saknar 'period' skall valideringsfel returneras")
    void shallReturnListOfErrorsIfDateRangeListOfPeriodIsMissing() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_PERIOD_ID.id(),
              updateDateRangeListValue(certificate, QUESTION_PERIOD_ID.id(),
                  Collections.emptyList()))
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
                  .contains("Välj minst ett alternativ."),
              () -> "Expect to contain 'Välj minst ett alternativ.' but was '%s'"
                  .formatted(validationErrors(response).get(0))
          )
      );
    }

    @Test
    @DisplayName("FK7472 - Om utkastet har en icke komplett 'period' skall valideringsfel returneras")
    void shallReturnListOfErrorsIfDateRangeListOfPeriodIsNotComplete() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var certificate = certificate(testCertificates);

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_PERIOD_ID.id(),
              updateDateRangeListValue(certificate, QUESTION_PERIOD_ID.id(),
                  List.of(
                      CertificateDataValueDateRange.builder()
                          .id("HALVA")
                          .to(LocalDate.now())
                          .build()
                  )
              ))
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
                  .contains("Ange ett datum"),
              () -> "Expect to contain 'Ange ett datum.' but was '%s'"
                  .formatted(validationErrors(response).get(0))
          ),
          () -> assertTrue(validationErrors(response).get(0).getField()
                  .contains("HALVA.from"),
              () -> "Expect field to contain 'HALVA.from' but was '%s'"
                  .formatted(validationErrors(response).get(0))
          )
      );
    }

    @Test
    @DisplayName("FK7472 - Om utkastet saknar 'Vårdenhetens adress' skall valideringsfel returneras")
    void shallReturnListOfErrorsIfMissingUnitContactAddress() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var certificate = updateUnit(
          testCertificates,
          certificate(testCertificates).getMetadata().getUnit()
              .withAddress("")
      );

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), "Text")
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
    @DisplayName("FK7472 - Om utkastet saknar 'Vårdenhetens postnummer' skall valideringsfel returneras")
    void shallReturnListOfErrorsIfMissingUnitContactZipCode() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var certificate = updateUnit(
          testCertificates,
          certificate(testCertificates).getMetadata().getUnit()
              .withZipCode("")
      );

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), "Text")
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
    @DisplayName("FK7472 - Om utkastet saknar 'Vårdenhetens postort' skall valideringsfel returneras")
    void shallReturnListOfErrorsIfMissingUnitContactCity() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var certificate = updateUnit(
          testCertificates,
          certificate(testCertificates).getMetadata().getUnit()
              .withCity("")
      );

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), "Text")
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
    @DisplayName("FK7472 - Om utkastet saknar 'Vårdenhetens telefonnummer' skall valideringsfel returneras")
    void shallReturnListOfErrorsIfMissingUnitContactPhoneNumber() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var certificate = updateUnit(
          testCertificates,
          certificate(testCertificates).getMetadata().getUnit()
              .withPhoneNumber("")
      );

      Objects.requireNonNull(
          certificate.getData().put(
              QUESTION_SYMPTOM_ID.id(),
              updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), "Text")
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
  @DisplayName("FK7472 - Hämta intygsxml")
  class GetCertificateXml {

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma mottagning skall det returneras")
    void shallReturnCertificateXMLIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det returneras")
    void shallReturnCertificateXMLIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på samma vårdenhet skall det returneras")
    void shallReturnCertificateXMLIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Vårdadministratör - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfPatientIsProtectedPersonAndUserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
    void shallReturnCertificateXMLIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
  @DisplayName("FK7472 - Signera")
  class SignCertificate {

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma mottagning skall det gå att signera")
    void shallSuccessfullySignIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det gå att signera")
    void shallSuccessfullySignIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på samma vårdenhet skall det gå att signera")
    void shallSuccessfullySignIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om användaren har rollen barnmorska ska intyget gå att signeras")
    void shallSuccessfullySignIfRoleIsMidwife() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var response = api.signCertificate(
          customSignCertificateRequest()
              .user(BERTIL_BARNMORSKA_DTO)
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
    @DisplayName("FK7472 - Vårdadministratör - Felkod 403 (FORBIDDEN) returneras")
    void shallReturn403UserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
    void shallSuccessfullySignIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget inte är klart för signering skall felkod 400 (BAD_REQUEST) returneras")
    void shallReturn403IfCertificateNotValid() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om signatur saknas skall felkod 400 (BAD_REQUEST) returneras")
    void shallReturn403IfSignatureNotValid() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om man försöker signera med en inaktuell revision skall felkod 400 (BAD_REQUEST) returneras")
    void shallReturn403IfVersionNotValid() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
    @DisplayName("FK7472 - Om intyget signeras ska ett meddelande läggas på AMQn")
    void shallSuccessfullyAddMessageOfSigningOnAMQ() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION)
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
              .untilAsserted(() -> assertEquals(1, testListener.messages().size())),
          () -> assertEquals(
              certificateId(testCertificates),
              testListener.messages().get(0).getStringProperty("certificateId")
          ),
          () -> assertEquals(
              "certificate-signed",
              testListener.messages().get(0).getStringProperty("eventType")
          )
      );
    }
  }

  @Nested
  @DisplayName("FK7472 - Skicka")
  class SendCertificate {

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma mottagning skall det gå att skicka")
    void shallSuccessfullySendIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det gå att skicka")
    void shallSuccessfullySendIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på samma vårdenhet skall det gå att skicka")
    void shallSuccessfullySendIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Om intyget skickas ska ett meddelande läggas på AMQn")
    void shallSuccessfullyAddMessageToAMQWhenSendingCertificate() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION, SIGNED)
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
              .untilAsserted(() -> assertEquals(1, testListener.messages().size())),
          () -> assertEquals(
              certificateId(testCertificates),
              testListener.messages().get(0).getStringProperty("certificateId")
          ),
          () -> assertEquals(
              "certificate-sent",
              testListener.messages().get(0).getStringProperty("eventType")
          )
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Vårdadministratör - Felkod 403 (FORBIDDEN) returneras")
    void shallReturn403UserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det gå att skicka")
    void shallSuccessfullySendIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION, SIGNED)
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
    @DisplayName("FK7472 - Om intyget inte är signerat skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfCertificateNotSigned() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var response = api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om intyget redan är skickat skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfCertificateIsAlreadySent() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
  @DisplayName("FK7472 - Intern api för Intygstjänsten")
  class InternalApi {

    @Test
    @DisplayName("FK7472 - Signerat intyg skall gå att hämta från intern api:et")
    void shallReturnSignedCertificate() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
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
          () -> assertEquals(FK7472, certificateInternalXmlResponse(response).getCertificateType()),
          () -> assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ID,
              certificateInternalXmlResponse(response).getUnit().getUnitId()),
          () -> assertNull(certificateInternalXmlResponse(response).getRevoked()),
          () -> assertTrue(decodeXml(certificateInternalXmlResponse(response).getXml()).contains(
                  certificateId(testCertificates)),
              () -> "Expected 'Läkare' to be part of xml: '%s'"
                  .formatted(decodeXml(certificateInternalXmlResponse(response).getXml())))
      );
    }

    @Test
    @DisplayName("FK7472 - Makulerat intyg skall gå att hämta från intern api:et")
    void shallReturnRevokedCertificate() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.revokeCertificate(
          defaultRevokeCertificateRequest(),
          certificateId(testCertificates)
      );

      final var response = internalApi.getCertificateXml(certificateId(testCertificates));

      assertAll(
          () -> assertEquals(certificateId(testCertificates),
              certificateInternalXmlResponse(response).getCertificateId()),
          () -> assertEquals(FK7472, certificateInternalXmlResponse(response).getCertificateType()),
          () -> assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ID,
              certificateInternalXmlResponse(response).getUnit().getUnitId()),
          () -> assertNotNull(certificateInternalXmlResponse(response).getRevoked()),
          () -> assertTrue(decodeXml(certificateInternalXmlResponse(response).getXml()).contains(
                  certificateId(testCertificates)),
              () -> "Expected 'Läkare' to be part of xml: '%s'"
                  .formatted(decodeXml(certificateInternalXmlResponse(response).getXml())))
      );
    }

    @Test
    @DisplayName("FK7472 - Skickat intyg skall gå att hämta från intern api:et")
    void shallReturnSentCertificate() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      final var response = internalApi.getCertificateXml(certificateId(testCertificates));

      assertAll(
          () -> assertEquals(certificateId(testCertificates),
              certificateInternalXmlResponse(response).getCertificateId()),
          () -> assertEquals(FK7472, certificateInternalXmlResponse(response).getCertificateType()),
          () -> assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ID,
              certificateInternalXmlResponse(response).getUnit().getUnitId()),
          () -> assertNotNull(certificateInternalXmlResponse(response).getRecipient()),
          () -> assertNull(certificateInternalXmlResponse(response).getRevoked()),
          () -> assertTrue(decodeXml(certificateInternalXmlResponse(response).getXml()).contains(
                  certificateId(testCertificates)),
              () -> "Expected 'Läkare' to be part of xml: '%s'"
                  .formatted(decodeXml(certificateInternalXmlResponse(response).getXml())))
      );
    }
  }

  @Nested
  @DisplayName("FK7472 - Makulera")
  class RevokeCertificate {

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma mottagning skall det gå att makulera")
    void shallSuccessfullyRevokeIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      final var response = api.revokeCertificate(
          defaultRevokeCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals(CertificateStatusTypeDTO.REVOKED,
              status(revokeCertificateResponse(response)))
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det gå att makulera")
    void shallSuccessfullyRevokeIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      final var response = api.revokeCertificate(
          defaultRevokeCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals(CertificateStatusTypeDTO.REVOKED,
              status(revokeCertificateResponse(response)))
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma vårdenhet skall det gå att makulera")
    void shallSuccessfullyRevokeIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION, SIGNED)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.revokeCertificate(
          customRevokeCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals(CertificateStatusTypeDTO.REVOKED,
              status(revokeCertificateResponse(response)))
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget makuleras ska ett meddelande läggas på AMQn")
    void shallSuccessfullyAddMessageToAMQWhenRevokingCertificate() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.revokeCertificate(
          defaultRevokeCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> waitAtMost(Duration.ofSeconds(5))
              .untilAsserted(() -> assertEquals(1, testListener.messages().size())),
          () -> assertEquals(
              certificateId(testCertificates),
              testListener.messages().get(0).getStringProperty("certificateId")
          ),
          () -> assertEquals(
              "certificate-revoked",
              testListener.messages().get(0).getStringProperty("eventType")
          )
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      final var response = api.revokeCertificate(
          customRevokeCertificateRequest()
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      final var response = api.revokeCertificate(
          customRevokeCertificateRequest()
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .unit(ALFA_VARDCENTRAL_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Vårdadministratör - Felkod 403 (FORBIDDEN) returneras")
    void shallReturn403UserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      final var response = api.revokeCertificate(
          customRevokeCertificateRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det gå att makulera")
    void shallSuccessfullyRevokeIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION, SIGNED)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.revokeCertificate(
          defaultRevokeCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertEquals(CertificateStatusTypeDTO.REVOKED,
              status(revokeCertificateResponse(response)))
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget inte är signerat skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfCertificateNotSigned() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var response = api.revokeCertificate(
          defaultRevokeCertificateRequest(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om intyget redan är makulerat skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfCertificateIsAlreadyRevoked() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      api.revokeCertificate(
          defaultRevokeCertificateRequest(),
          certificateId(testCertificates)
      );

      final var response = api.revokeCertificate(
          defaultRevokeCertificateRequest(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }
  }

  @Nested
  @DisplayName("FK7472 - Hämta intygspdf")
  class GetCertificatePdf {

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma mottagning skall pdf returneras")
    void shallReturnCertificatePdfIfUnitIsSubUnitAndOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION)
      );

      final var response = api.getCertificatePdf(
          defaultGetCertificatePdfRequest(),
          certificateId(testCertificates)
      );

      assertNotNull(
          pdfData(response.getBody()),
          "Should return certificate pdf data when exists!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall pdf returneras")
    void shallReturnCertificatePdfIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION)
      );

      final var response = api.getCertificatePdf(
          customGetCertificatePdfRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertNotNull(
          pdfData(response.getBody()),
          "Should return certificate pdf data when exists!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma vårdenhet skall pdf returneras")
    void shallReturnCertificatePdfIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, FK7472Constants.VERSION)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.getCertificatePdf(
          customGetCertificatePdfRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertNotNull(
          pdfData(response.getBody()),
          "Should return certificate pdf data when exists!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION)
      );

      final var response = api.getCertificatePdf(
          customGetCertificatePdfRequest().unit(ALFA_HUDMOTTAGNINGEN_DTO).build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION)
      );

      final var response = api.getCertificatePdf(
          customGetCertificatePdfRequest()
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .unit(ALFA_VARDCENTRAL_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Vårdadministratör - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfPatientIsProtectedPersonAndUserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, FK7472Constants.VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.getCertificatePdf(
          customGetCertificatePdfRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall pdf returneras")
    void shallReturnCertificatePdfIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, FK7472Constants.VERSION)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.getCertificatePdf(
          customGetCertificatePdfRequest()
              .user(AJLA_DOCTOR_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertNotNull(
          pdfData(response.getBody()),
          "Should return certificate pdf data when exists!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är signerat skall pdf returneras")
    void shallReturnSignedCertificatePdf() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.getCertificatePdf(
          defaultGetCertificatePdfRequest(),
          certificateId(testCertificates)
      );

      assertNotNull(
          pdfData(response.getBody()),
          "Should return signed certificate pdf data!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är signerat och skickat skall pdf returneras")
    void shallReturnSentCertificatePdf() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      final var response = api.getCertificatePdf(
          defaultGetCertificatePdfRequest(),
          certificateId(testCertificates)
      );

      assertNotNull(
          pdfData(response.getBody()),
          "Should return sent certificate pdf data!"
      );
    }
  }

  @Nested
  @DisplayName("FK7472 - Utökad behörighet vid djupintegration utan SVOD")
  class AccessLevelsDeepIntegration {

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan enhet inom samma vårdgivare skall det gå att läsa intyget")
    void shallReturnCertificateIfOnDifferentUnitButSameCareProvider() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION)
      );

      final var response = api.getCertificate(
          customGetCertificateRequest()
              .user(ajlaDoktorDtoBuilder()
                  .accessScope(AccessScopeTypeDTO.WITHIN_CARE_PROVIDER)
                  .build())
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate when exists!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan enhet inom samma vårdgivare skall det gå att hämta PDF")
    void shallReturnPdfIfOnDifferentUnitButSameCareProvider() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION)
      );

      final var response = api.getCertificatePdf(
          customGetCertificatePdfRequest()
              .user(ajlaDoktorDtoBuilder()
                  .accessScope(AccessScopeTypeDTO.WITHIN_CARE_PROVIDER)
                  .build())
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertNotNull(
          pdfData(response.getBody()),
          "Should return pdf when exists!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan enhet inom samma vårdgivare skall felkod 403 (FORBIDDEN) returneras vid skickande av intyg")
    void shallNotAllowToSendOnDifferentUnitButSameCareProvider() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION)
      );

      final var response = api.sendCertificate(
          customSendCertificateRequest()
              .user(ajlaDoktorDtoBuilder()
                  .accessScope(AccessScopeTypeDTO.WITHIN_CARE_PROVIDER)
                  .build())
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }
  }

  @Nested
  @DisplayName("FK7472 - Utökad behörighet vid djupintegration och SVOD (sjf=true)")
  class AccessLevelsSVOD {

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat inom en annan vårdgivare skall det gå att läsa intyget")
    void shallReturnCertificateIfOnDifferentUnitButSameCareProvider() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION)
      );

      final var response = api.getCertificate(
          customGetCertificateRequest()
              .user(ajlaDoktorDtoBuilder()
                  .accessScope(AccessScopeTypeDTO.ALL_CARE_PROVIDERS)
                  .build())
              .careProvider(BETA_REGIONEN_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertNotNull(
          certificate(response.getBody()),
          "Should return certificate when exists!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan enhet inom samma vårdgivare skall felkod 403 (FORBIDDEN) returneras vid hämtning av PDF")
    void shallReturnPdfIfOnDifferentUnitButSameCareProvider() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION)
      );

      final var response = api.getCertificatePdf(
          customGetCertificatePdfRequest()
              .user(ajlaDoktorDtoBuilder()
                  .accessScope(AccessScopeTypeDTO.WITHIN_CARE_PROVIDER)
                  .build())
              .careProvider(BETA_REGIONEN_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan enhet inom samma vårdgivare skall felkod 403 (FORBIDDEN) returneras vid skickande av intyg")
    void shallNotAllowToSendOnDifferentUnitButSameCareProvider() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION)
      );

      final var response = api.sendCertificate(
          customSendCertificateRequest()
              .user(ajlaDoktorDtoBuilder()
                  .accessScope(AccessScopeTypeDTO.WITHIN_CARE_PROVIDER)
                  .build())
              .careProvider(BETA_REGIONEN_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }
  }

  @Nested
  @DisplayName("FK7472 - Ersätta")
  class ReplaceCertificate {

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma mottagning skall det gå att ersätta")
    void shallSuccessfullyReplaceIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.replaceCertificate(
          defaultReplaceCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertNotNull(
              relation(replaceCertificateResponse(response)).getParent()
              , "Should add parent to replaced certificate"),
          () -> assertEquals(certificateId(testCertificates),
              relation(replaceCertificateResponse(response)).getParent().getCertificateId())
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det gå att ersätta")
    void shallSuccessfullyReplaceIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.replaceCertificate(
          defaultReplaceCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertNotNull(
              relation(replaceCertificateResponse(response)).getParent()
              , "Should add parent to replaced certificate"),
          () -> assertEquals(certificateId(testCertificates),
              relation(replaceCertificateResponse(response)).getParent().getCertificateId())
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma vårdenhet skall det gå att ersätta")
    void shallSuccessfullyReplaceIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.replaceCertificate(
          customReplaceCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertNotNull(
              relation(replaceCertificateResponse(response)).getParent()
              , "Should add parent to replaced certificate"),
          () -> assertEquals(certificateId(testCertificates),
              relation(replaceCertificateResponse(response)).getParent().getCertificateId())
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.replaceCertificate(
          customReplaceCertificateRequest()
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.replaceCertificate(
          customReplaceCertificateRequest()
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .unit(ALFA_VARDCENTRAL_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Vårdadministratör - Felkod 403 (FORBIDDEN) returneras")
    void shallReturn403UserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.replaceCertificate(
          customReplaceCertificateRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det gå att ersätta")
    void shallSuccessfullyRevokeIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.replaceCertificate(
          defaultReplaceCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertNotNull(
              relation(replaceCertificateResponse(response)).getParent()
              , "Should add parent to replaced certificate"),
          () -> assertEquals(certificateId(testCertificates),
              relation(replaceCertificateResponse(response)).getParent().getCertificateId())
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget inte är signerat skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfCertificateNotSigned() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION)
      );

      final var response = api.replaceCertificate(
          defaultReplaceCertificateRequest(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om användaren är blockerad ska inte 'Ersätt intyg' vara tillgänglig")
    void shallReturn403IfUserIsBlocked() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.replaceCertificate(
          customReplaceCertificateRequest()
              .user(
                  ajlaDoktorDtoBuilder()
                      .blocked(Boolean.TRUE)
                      .build()
              )
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om intyget redan ersatts så ska felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfCertificateAlreadyIsReplaced() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      api.replaceCertificate(
          defaultReplaceCertificateRequest(),
          certificateId(testCertificates)
      );

      final var response = api.replaceCertificate(
          defaultReplaceCertificateRequest(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }
  }

  @Nested
  @DisplayName("FK7472 - Förnya")
  class RenewCertificate {

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma mottagning skall det gå att förnya")
    void shallSuccessfullyRenewIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.renewCertificate(
          defaultRenewCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertNotNull(
              relation(renewCertificateResponse(response)).getParent()
              , "Should add parent to renewed certificate"),
          () -> assertEquals(certificateId(testCertificates),
              relation(renewCertificateResponse(response)).getParent().getCertificateId())
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på mottagning men på samma vårdenhet skall det gå att förnya")
    void shallSuccessfullyRenewIfUnitIsCareUnitAndOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.renewCertificate(
          defaultRenewCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertNotNull(
              relation(renewCertificateResponse(response)).getParent()
              , "Should add parent to renewed certificate"),
          () -> assertEquals(certificateId(testCertificates),
              relation(renewCertificateResponse(response)).getParent().getCertificateId())
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma vårdenhet skall det gå att förnya")
    void shallSuccessfullyRenewIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      final var response = api.renewCertificate(
          customRenewCertificateRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertNotNull(
              relation(renewCertificateResponse(response)).getParent()
              , "Should add parent to renewed certificate"),
          () -> assertEquals(certificateId(testCertificates),
              relation(renewCertificateResponse(response)).getParent().getCertificateId())
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.renewCertificate(
          customRenewCertificateRequest()
              .unit(ALFA_HUDMOTTAGNINGEN_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.renewCertificate(
          customRenewCertificateRequest()
              .careUnit(ALFA_VARDCENTRAL_DTO)
              .unit(ALFA_VARDCENTRAL_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Vårdadministratör - Felkod 403 (FORBIDDEN) returneras")
    void shallReturn403UserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.renewCertificate(
          customRenewCertificateRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det gå att förnya")
    void shallSuccessfullyRenewIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      final var response = api.renewCertificate(
          defaultRenewCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertNotNull(
              relation(renewCertificateResponse(response)).getParent()
              , "Should add parent to renewed certificate"),
          () -> assertEquals(certificateId(testCertificates),
              relation(renewCertificateResponse(response)).getParent().getCertificateId())
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget inte är signerat skall felkod 403 (FORBIDDEN) returneras")
    void shallReturn403IfCertificateNotSigned() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION)
      );

      final var response = api.renewCertificate(
          defaultRenewCertificateRequest(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om användaren är blockerad ska inte 'Förnya intyg' vara tillgänglig")
    void shallReturn403IfUserIsBlocked() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      final var response = api.renewCertificate(
          customRenewCertificateRequest()
              .user(
                  ajlaDoktorDtoBuilder()
                      .blocked(Boolean.TRUE)
                      .build()
              )
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Om intyget redan förnyats så ska det gå att förnya pånytt")
    void shallSuccessfullyRenewIfAlreadyRenewed() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      api.renewCertificate(
          defaultRenewCertificateRequest(),
          certificateId(testCertificates)
      );

      final var response = api.renewCertificate(
          defaultRenewCertificateRequest(),
          certificateId(testCertificates)
      );

      assertAll(
          () -> assertNotNull(
              relation(renewCertificateResponse(response)).getParent()
              , "Should add parent to renewed certificate"),
          () -> assertEquals(certificateId(testCertificates),
              relation(renewCertificateResponse(response)).getParent().getCertificateId())
      );
    }
  }

  @Nested
  @DisplayName("FK7472 - Kompletteringsbegäran")
  class Complement {

    @Test
    @DisplayName("FK7472 - Intyg skall kunna ta emot kompletteringsbegäran")
    void shallReturn200IfComplementCanBeReceived() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      final var response = api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Intyg som har kompletteringsbegäran ska kunna kompletteras")
    void shallReturnCertificateIfComplementingCertificateWithComplements() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .id("NEW_MESSAGE_ID")
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      final var response = api.complementCertificate(
          defaultComplementCertificateRequest(),
          certificateId(testCertificates)
      );

      assertEquals(200, response.getStatusCode().value());
      assertNotNull(response.getBody().getCertificate());
    }

    @Test
    @DisplayName("FK7472 - Intyg som har påbörjad komplettering ska inte kunna kompletteras - 403 (FORBIDDEN)")
    void shallReturn403IfDraftComplementAlreadyExists() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .id("NEW_MESSAGE_ID")
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      api.complementCertificate(
          defaultComplementCertificateRequest(),
          certificateId(testCertificates)
      );

      final var response = api.complementCertificate(
          defaultComplementCertificateRequest(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Intyg som inte har kompletteringsbegäran ska inte kunna kompletteras - 403 (FORBIDDEN)")
    void shallReturn403IfNoComplementsFromRecipient() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      final var response = api.complementCertificate(
          defaultComplementCertificateRequest(),
          certificateId(testCertificates)
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Utkast skall inte kunna ta emot kompletteringsbegäran - 403 (FORBIDDEN) ")
    void shallReturn403IfCertificateIsDraft() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION)
      );

      final var response = api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Makulerade intyg skall inte kunna ta emot kompletteringsbegäran - 403 (FORBIDDEN)")
    void shallReturn403IfCertificateIsRevoked() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.revokeCertificate(
          defaultRevokeCertificateRequest(),
          certificateId(testCertificates)
      );

      final var response = api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Intyg skall inte kunna ta emot kompletteringsbegäran på fel patient - 403 (FORBIDDEN)")
    void shallReturn403IfComplementIsOfDifferentPatient() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      final var response = api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .personId(ALVE_REACT_ALFREDSSON_DTO.getId())
              .build()
      );

      assertEquals(403, response.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Kompletteringsbegäran skall sättas som hanterad när ersättande intyg signeras")
    void shallSetComplementAsHandledWhenComplementingCertificateIsSigned() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      final var response = api.replaceCertificate(
          defaultReplaceCertificateRequest(),
          certificateId(testCertificates)
      );

      api.signCertificate(
          defaultSignCertificateRequest(),
          certificateId(response.getBody()),
          certificate(response.getBody()).getMetadata().getVersion()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          defaultGetCertificateMessageRequest(),
          certificateId(testCertificates)
      );

      assertTrue(questions(messagesForCertificate.getBody()).get(0).isHandled(),
          "Expected that complement message was handled, but it was not!"
      );
    }

    @Test
    @DisplayName("FK7472 - Kompletteringsbegäran skall sättas som hanterad när förnyade intyget signeras")
    void shallSetComplementAsHandledWhenRenewingCertificateIsSigned() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      final var renewResponse = api.renewCertificate(
          defaultRenewCertificateRequest(),
          certificateId(testCertificates)
      );

      final var renewingCertificate = certificate(renewResponse.getBody());
      renewingCertificate.getData().put(
          QUESTION_PERIOD_ID.id(),
          updateDateRangeListValue(renewingCertificate, QUESTION_PERIOD_ID.id(),
              List.of(
                  CertificateDataValueDateRange.builder()
                      .id(WorkCapacityType.HALVA.name())
                      .to(LocalDate.now())
                      .from(LocalDate.now().minusDays(10))
                      .build()
              )
          )
      );

      final var updateResponse = api.updateCertificate(
          customUpdateCertificateRequest()
              .certificate(renewingCertificate)
              .build(),
          certificateId(renewResponse.getBody())
      );

      api.signCertificate(
          defaultSignCertificateRequest(),
          certificateId(renewResponse.getBody()),
          certificate(updateResponse.getBody()).getMetadata().getVersion()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          defaultGetCertificateMessageRequest(),
          certificateId(testCertificates)
      );

      assertTrue(questions(messagesForCertificate.getBody()).get(0).isHandled(),
          "Expected that complement message was handled, but it was not!"
      );
    }
  }

  @Nested
  @DisplayName("FK7472 - Hantering av frågor för intyg")
  class Messages {

    @Test
    @DisplayName("FK7472 - Skall returnera lista av frågor som finns på intyget")
    void shallReturnListOfQuestionsForCertificate() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          defaultGetCertificateMessageRequest(),
          certificateId(testCertificates)
      );

      assertTrue(
          hasQuestions(messagesForCertificate.getBody())
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma mottagning ska hantering av frågor vara tillgänglig")
    void shallReturnListOfQuestionsForCertificateOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          defaultGetCertificateMessageRequest(),
          certificateId(testCertificates)
      );

      assertTrue(
          CertificateUtil.resourceLink(messagesForCertificate.getBody())
              .stream()
              .anyMatch(link -> link.getType().equals(ResourceLinkTypeDTO.COMPLEMENT_CERTIFICATE)),
          "Should return link!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på mottagning men på samma vårdenhet ska hantering av frågor vara tillgänglig")
    void shallReturnQuestionWithComplementCertificateIfIssuedOnSameCareUnitDifferentSubUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          customGetCertificateMessageRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertTrue(
          CertificateUtil.resourceLink(messagesForCertificate.getBody())
              .stream()
              .anyMatch(link -> link.getType().equals(ResourceLinkTypeDTO.COMPLEMENT_CERTIFICATE)),
          "Should return link!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på samma vårdenhet ska hantering av frågor vara tillgänglig")
    void shallReturnQuestionWithComplementCertificateIfIssuedOnSameCareUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION, SIGNED)
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build()
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          customGetCertificateMessageRequest()
              .unit(ALFA_MEDICINCENTRUM_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertTrue(
          CertificateUtil.resourceLink(messagesForCertificate.getBody())
              .stream()
              .anyMatch(link -> link.getType().equals(ResourceLinkTypeDTO.COMPLEMENT_CERTIFICATE)),
          "Should return link!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om intyget är utfärdat på en annan mottagning ska hantering av frågor vara tillgänglig")
    void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, VERSION, SIGNED)
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          customGetCertificateMessageRequest()
              .unit(ALFA_HUDMOTTAGNINGEN_DTO).build(),
          certificateId(testCertificates)
      );

      assertEquals(403, messagesForCertificate.getStatusCode().value());
    }


    @Test
    @DisplayName("FK7472 - Vårdadministratör - Om intyget är utfärdat på en patient som har skyddade personuppgifter ska inte hantering av frågor vara tillgänglig")
    void shallReturn403IfPatientIsProtectedPersonAndUserIsCareAdmin() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION, SIGNED)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .personId(ANONYMA_REACT_ATTILA_DTO.getId())
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          customGetCertificateMessageRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertEquals(403, messagesForCertificate.getStatusCode().value());
    }

    @Test
    @DisplayName("FK7472 - Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter ska inte hantering av frågor vara tillgänglig")
    void shallReturnComplementCertificateLinkIfPatientIsProtectedPersonAndUserIsDoctor() {
      final var testCertificates = testabilityApi.addCertificates(
          customTestabilityCertificateRequest(FK7472, VERSION, SIGNED)
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .build()
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .personId(ANONYMA_REACT_ATTILA_DTO.getId())
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          customGetCertificateMessageRequest()
              .user(AJLA_DOCTOR_DTO)
              .build(),
          certificateId(testCertificates)
      );

      assertTrue(
          CertificateUtil.resourceLink(messagesForCertificate.getBody())
              .stream()
              .anyMatch(link -> link.getType().equals(ResourceLinkTypeDTO.COMPLEMENT_CERTIFICATE)),
          "Should return link!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om användaren är blockerad ska inte hantering av frågor vara tillgänglig")
    void shallNotReturnComplementCertificateIfUserIsBlocked() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          customGetCertificateMessageRequest()
              .user(
                  ajlaDoktorDtoBuilder()
                      .blocked(Boolean.TRUE)
                      .build()
              )
              .build(),
          certificateId(testCertificates)
      );

      assertTrue(
          questions(messagesForCertificate.getBody()).get(0).getLinks()
              .isEmpty(),
          "Should not return link!"
      );
    }

    @Test
    @DisplayName("FK7472 - Om användaren saknar kopieringsmöjligheter ska inte hantering av frågor vara tillgänglig")
    void shallNotReturnComplementCertificateIfUserCopyIsFalse() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(FK7472, FK7472Constants.VERSION, SIGNED)
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(
                  certificateId(testCertificates)
              )
              .build()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          customGetCertificateMessageRequest()
              .user(
                  ajlaDoktorDtoBuilder()
                      .allowCopy(Boolean.FALSE)
                      .build()
              )
              .build(),
          certificateId(testCertificates)
      );

      assertTrue(
          questions(messagesForCertificate.getBody()).get(0).getLinks()
              .isEmpty(),
          "Should not return link!"
      );
    }
  }
}
