package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ALVE_REACT_ALFREDSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customGetPatientCertificatesRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetPatientCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificates;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.exists;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class GetPatientCertificatesIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning skall det returneras")
  void shallReturnCertificateIfUnitIsSubUnitAndOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ALVE_REACT_ALFREDSSON_DTO)
            .build()
    );

    final var response = api().getPatientCertificates(
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
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall det returneras")
  void shallReturnCertificateIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ALVE_REACT_ALFREDSSON_DTO)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api().getPatientCertificates(
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
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall det returneras")
  void shallReturnCertificateIfUnitIsCareUnitAndOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ALVE_REACT_ALFREDSSON_DTO)
            .build()
    );

    final var response = api().getPatientCertificates(
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

  @ParameterizedTest
  @DisplayName("Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
  @MethodSource("rolesAccessToProtectedPerson")
  void shallReturnCertificateIfPatientIsProtectedPerson(UserDTO userDTO) {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api().getPatientCertificates(
        customGetPatientCertificatesRequest()
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .user(userDTO)
            .build()
    );

    assertAll(
        () -> assertTrue(
            exists(certificates(response.getBody()), certificate(testCertificates))
        ),
        () -> assertEquals(1, certificates(response.getBody()).size())
    );
  }

  @ParameterizedTest
  @DisplayName("Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
  @MethodSource("rolesNoAccessToProtectedPerson")
  void shallReturn403IfPatientIsProtectedPerson(UserDTO userDTO) {
    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api().getPatientCertificates(
        customGetPatientCertificatesRequest()
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .user(userDTO)
            .build()
    );

    assertTrue(certificates(response.getBody()).isEmpty());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getPatientCertificates(
        customGetPatientCertificatesRequest()
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .unit(ALFA_VARDCENTRAL_DTO)
            .build()
    );

    assertTrue(certificates(response.getBody()).isEmpty());
  }
}
