package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customGetCertificatePdfRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetCertificatePdfRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.pdfData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockserver.client.MockServerClient;
import se.inera.intyg.certificateservice.application.common.dto.AccessScopeTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;
import se.inera.intyg.certificateservice.integrationtest.common.util.CertificatePrintServiceMock;
import se.inera.intyg.certificateservice.integrationtest.common.util.Containers;

public abstract class GetCertificateGeneralPdfIT extends BaseIntegrationIT {


  private CertificatePrintServiceMock certificatePrintServiceMock;

  @BeforeEach
  void setupMocks() {
    final var mockServerClient = new MockServerClient(
        Containers.MOCK_SERVER_CONTAINER.getHost(),
        Containers.MOCK_SERVER_CONTAINER.getServerPort()
    );
    this.certificatePrintServiceMock = new CertificatePrintServiceMock(mockServerClient);
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning ska pdf returneras")
  void shallReturnCertificatePdfIfUnitIsSubUnitAndOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    certificatePrintServiceMock.mockPdf();

    final var response = api().getCertificatePdf(
        defaultGetCertificatePdfRequest(),
        certificateId(testCertificates)
    );

    assertNotNull(
        pdfData(response.getBody()),
        "Should return certificate pdf data when exists"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet ska pdf returneras")
  void shallReturnCertificatePdfIfUnitIsCareUnitAndOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    certificatePrintServiceMock.mockPdf();

    final var response = api().getCertificatePdf(
        customGetCertificatePdfRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertNotNull(
        pdfData(response.getBody()),
        "Should return certificate pdf data when exists"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma vårdenhet ska pdf returneras")
  void shallReturnCertificatePdfIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    certificatePrintServiceMock.mockPdf();

    final var response = api().getCertificatePdf(
        customGetCertificatePdfRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertNotNull(
        pdfData(response.getBody()),
        "Should return certificate pdf data when exists"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan enhet inom samma vårdgivare ska pdf returneras")
  void shallReturnPdfIfOnDifferentUnitButSameCareProvider() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    certificatePrintServiceMock.mockPdf();

    final var response = api().getCertificatePdf(
        customGetCertificatePdfRequest()
            .user(ajlaDoktorDtoBuilder()
                .accessScope(AccessScopeTypeDTO.WITHIN_CARE_PROVIDER)
                .build())
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertNotNull(
        pdfData(response.getBody()),
        "Should return pdf when exists"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan mottagning ska felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    certificatePrintServiceMock.mockPdf();

    final var response = api().getCertificatePdf(
        customGetCertificatePdfRequest().unit(ALFA_HUDMOTTAGNINGEN_DTO).build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan vårdenhet ska felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    certificatePrintServiceMock.mockPdf();

    final var response = api().getCertificatePdf(
        customGetCertificatePdfRequest()
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .unit(ALFA_VARDCENTRAL_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @ParameterizedTest
  @DisplayName("Om intyget är utfärdat på en patient som har skyddade personuppgifter ska felkod 403 (FORBIDDEN) returneras")
  @MethodSource("rolesNoAccessToProtectedPerson")
  void shallReturn403IfPatientIsProtectedPerson(UserDTO userDTO) {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    certificatePrintServiceMock.mockPdf();

    final var response = api().getCertificatePdf(
        customGetCertificatePdfRequest()
            .user(userDTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter ska pdf returneras")
  void shallReturnCertificatePdfIfPatientIsProtectedPersonAndUserIsDoctor() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    certificatePrintServiceMock.mockPdf();

    final var response = api().getCertificatePdf(
        customGetCertificatePdfRequest()
            .user(AJLA_DOCTOR_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertNotNull(
        pdfData(response.getBody()),
        "Should return certificate pdf data when exists"
    );
  }

  @Test
  @DisplayName("Om intyget är signerat ska pdf returneras")
  void shallReturnSignedCertificatePdf() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    certificatePrintServiceMock.mockPdf();

    final var response = api().getCertificatePdf(
        defaultGetCertificatePdfRequest(),
        certificateId(testCertificates)
    );

    assertNotNull(
        pdfData(response.getBody()),
        "Should return signed certificate pdf data"
    );
  }

  @Test
  @DisplayName("Om intyget är signerat och skickat ska pdf returneras")
  void shallReturnSentCertificatePdf() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    certificatePrintServiceMock.mockPdf();

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().getCertificatePdf(
        defaultGetCertificatePdfRequest(),
        certificateId(testCertificates)
    );

    assertNotNull(
        pdfData(response.getBody()),
        "Should return sent certificate pdf data"
    );
  }
}
