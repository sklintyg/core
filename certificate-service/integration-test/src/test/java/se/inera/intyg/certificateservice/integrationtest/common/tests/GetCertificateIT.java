package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class GetCertificateIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning skall det returneras")
  void shallReturnCertificateIfUnitIsSubUnitAndOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getCertificate(
        defaultGetCertificateRequest(),
        certificateId(testCertificates)
    );

    assertNotNull(
        certificate(response.getBody()),
        "Should return certificate when exists!"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall det returneras")
  void shallReturnCertificateIfUnitIsCareUnitAndOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getCertificate(
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
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall det returneras")
  void shallReturnCertificateIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api().getCertificate(
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
  @DisplayName("Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getCertificate(
        customGetCertificateRequest().unit(ALFA_HUDMOTTAGNINGEN_DTO).build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getCertificate(
        customGetCertificateRequest()
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .unit(ALFA_VARDCENTRAL_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @ParameterizedTest
  @DisplayName("Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras om användaren har fel roll")
  @MethodSource("rolesNoAccessToProtectedPerson")
  void shallReturn403IfPatientIsProtectedPerson(UserDTO userDTO) {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api().getCertificate(
        customGetCertificateRequest()
            .user(userDTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }


  @Test
  @DisplayName("Om intyget är utfärdat på en patient som har skyddade personuppgifter och användaren har rätt roll och är inloggad på överliggande enheht ska intyget returneras")
  void shallReturnCertificateIfPatientIsProtectedPersonAndUserHasCorrectRoleAndWithinCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api().getCertificate(
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

  @ParameterizedTest
  @DisplayName("Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
  @MethodSource("rolesAccessToProtectedPerson")
  void shallReturnCertificateIfPatientIsProtectedPerson(UserDTO userDTO) {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api().getCertificate(
        customGetCertificateRequest()
            .user(userDTO)
            .build(),
        certificateId(testCertificates)
    );

    assertNotNull(
        certificate(response.getBody()),
        "Should return certificate when exists!"
    );
  }
}