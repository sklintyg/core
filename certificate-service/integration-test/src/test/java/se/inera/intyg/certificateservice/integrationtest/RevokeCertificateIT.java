package se.inera.intyg.certificateservice.integrationtest;

import static org.awaitility.Awaitility.waitAtMost;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customRevokeCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultRevokeCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.revokeCertificateResponse;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.status;

import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;

public abstract class RevokeCertificateIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning skall det gå att makulera")
  void shallSuccessfullyRevokeIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
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
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall det gå att makulera")
  void shallSuccessfullyRevokeIfUnitIsCareUnitAndOnSameCareUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
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
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall det gå att makulera")
  void shallSuccessfullyRevokeIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
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
  @DisplayName("Om intyget makuleras ska ett meddelande läggas på AMQn")
  void shallSuccessfullyAddMessageToAMQWhenRevokingCertificate() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
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
  @DisplayName("Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
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
  @DisplayName("Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
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
  @DisplayName("Vårdadministratör - Felkod 403 (FORBIDDEN) returneras")
  void shallReturn403UserIsCareAdmin() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
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
  @DisplayName("Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det gå att makulera")
  void shallSuccessfullyRevokeIfPatientIsProtectedPersonAndUserIsDoctor() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
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
  @DisplayName("Om intyget inte är signerat skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfCertificateNotSigned() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.revokeCertificate(
        defaultRevokeCertificateRequest(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget redan är makulerat skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfCertificateIsAlreadyRevoked() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
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
