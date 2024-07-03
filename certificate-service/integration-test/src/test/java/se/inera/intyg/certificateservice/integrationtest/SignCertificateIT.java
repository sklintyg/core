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
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.version;

import java.time.Duration;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;

public abstract class SignCertificateIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();


  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning skall det gå att signera")
  void shallSuccessfullySignIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.signCertificate(
        defaultSignCertificateRequest(),
        certificateId(testCertificates),
        version(testCertificates)
    );

    assertAll(
        () -> assertEquals(SIGNED,
            Objects.requireNonNull(certificate(response)).getMetadata().getStatus()
        )
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall det gå att signera")
  void shallSuccessfullySignIfUnitIsCareUnitAndOnSameCareUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
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
            Objects.requireNonNull(certificate(response)).getMetadata().getStatus()
        )
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall det gå att signera")
  void shallSuccessfullySignIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
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
            Objects.requireNonNull(certificate(response)).getMetadata().getStatus()
        )
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
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
  @DisplayName("Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
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
  @DisplayName("Vårdadministratör - Felkod 403 (FORBIDDEN) returneras")
  void shallReturn403UserIsCareAdmin() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
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
  @DisplayName("Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
  void shallSuccessfullySignIfPatientIsProtectedPersonAndUserIsDoctor() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api.signCertificate(
        defaultSignCertificateRequest(),
        certificateId(testCertificates),
        version(testCertificates)
    );

    assertAll(
        () -> assertEquals(SIGNED,
            Objects.requireNonNull(certificate(response)).getMetadata().getStatus()
        )
    );
  }

  @Test
  @DisplayName("Om intyget inte är klart för signering skall felkod 400 (BAD_REQUEST) returneras")
  void shallReturn403IfCertificateNotValid() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
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
  @DisplayName("Om signatur saknas skall felkod 400 (BAD_REQUEST) returneras")
  void shallReturn403IfSignatureNotValid() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
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
  @DisplayName("Om man försöker signera med en inaktuell revision skall felkod 400 (BAD_REQUEST) returneras")
  void shallReturn403IfVersionNotValid() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
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
  @DisplayName("Om intyget signeras ska ett meddelande läggas på AMQn")
  void shallSuccessfullyAddMessageOfSigningOnAMQ() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
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
