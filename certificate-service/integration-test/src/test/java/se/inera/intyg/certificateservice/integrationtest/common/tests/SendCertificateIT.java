package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ANNA_SJUKSKOTERSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.BERTIL_BARNMORSKA_DTO;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultReplaceCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getRecipient;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.replaceCertificateResponse;

import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;
import se.inera.intyg.certificateservice.integrationtest.common.util.MessageListenerUtil;

public abstract class SendCertificateIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning skall det gå att skicka")
  void shallSuccessfullySendIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(recipient(), getRecipient(response).getId()),
        () -> assertNotNull(getRecipient(response).getSent())
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall det gå att skicka")
  void shallSuccessfullySendIfUnitIsCareUnitAndOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(recipient(), getRecipient(response).getId()),
        () -> assertNotNull(getRecipient(response).getSent())
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall det gå att skicka")
  void shallSuccessfullySendIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api().sendCertificate(
        customSendCertificateRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(recipient(), getRecipient(response).getId()),
        () -> assertNotNull(getRecipient(response).getSent())
    );
  }

  @Test
  @DisplayName("Om intyget skickas ska ett meddelande läggas på AMQn")
  void shallSuccessfullyAddMessageToAMQWhenSendingCertificate() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    api().sendCertificate(
        customSendCertificateRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    final var expectedCertificateId = certificateId(testCertificates);

    final var message = MessageListenerUtil.awaitByCertificateId(Duration.ofSeconds(10),
        expectedCertificateId);

    assertAll(
        () -> assertNotNull(message,
            "Expected to receive a message for certificateId: " + expectedCertificateId),
        () -> assertEquals(
            expectedCertificateId, message.getStringProperty("certificateId")
        ),
        () -> assertEquals(
            "certificate-sent", message.getStringProperty("eventType")
        )
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().sendCertificate(
        customSendCertificateRequest()
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().sendCertificate(
        customSendCertificateRequest()
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .unit(ALFA_VARDCENTRAL_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Vårdadministratör - Om intyget är signerat ska intyget gå att skicka")
  void shallReturnBeAbleToSendIfUserIsCareAdmin() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().sendCertificate(
        customSendCertificateRequest()
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(recipient(), getRecipient(response).getId()),
        () -> assertNotNull(getRecipient(response).getSent())
    );
  }

  @Test
  @DisplayName("Barnmorska - Om intyget är signerat ska intyget gå att skicka")
  void shallReturnBeAbleToSendIfUserIsMidWife() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().sendCertificate(
        customSendCertificateRequest()
            .user(BERTIL_BARNMORSKA_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(recipient(), getRecipient(response).getId()),
        () -> assertNotNull(getRecipient(response).getSent())
    );
  }

  @Test
  @DisplayName("Sjuksköterska - Om intyget är signerat ska intyget gå att skicka")
  void shallReturnBeAbleToSendIfUserIsNurse() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().sendCertificate(
        customSendCertificateRequest()
            .user(ANNA_SJUKSKOTERSKA_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(recipient(), getRecipient(response).getId()),
        () -> assertNotNull(getRecipient(response).getSent())
    );
  }

  @Test
  @DisplayName("Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det gå att skicka")
  void shallSuccessfullySendIfPatientIsProtectedPersonAndUserIsDoctor() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(recipient(), getRecipient(response).getId()),
        () -> assertNotNull(getRecipient(response).getSent())
    );
  }

  @Test
  @DisplayName("Om intyget inte är signerat skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfCertificateNotSigned() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget redan är skickat skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfCertificateIsAlreadySent() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är ersatt av ett signerat intyg skall det inte gå att skicka")
  void shallReturn403IfCertificateReplacedIsAlreadySent() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var replaceCertificate = api().replaceCertificate(
        defaultReplaceCertificateRequest(),
        certificateId(testCertificates)
    );

    final var replacedCertificateId = replaceCertificateResponse(
        replaceCertificate)
        .getCertificate()
        .getMetadata()
        .getId();

    api().signCertificate(
        defaultSignCertificateRequest(),
        replacedCertificateId,
        0L
    );

    final var response = api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }
}