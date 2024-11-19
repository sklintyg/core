package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.getCitizenCertificate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;

public abstract class SendCitizenCertificateIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  protected abstract boolean availableForCitizen();

  @Test
  @DisplayName("Om intyget är utfärdat på invånaren ska intyget skickas")
  void shallSendCertificateIfIssuedOnCitizen() {
    if (!availableForCitizen()) {
      return;
    }
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api.sendCitizenCertificate(
        SendCitizenCertificateRequest.builder()
            .personId(PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(ATHENA_REACT_ANDERSSON_ID)
                .build())
            .build(), certificateId(testCertificates)
    );

    assertTrue(getCitizenCertificate(response).getMetadata().isSent(),
        "Should set sent to true on certificate.");
  }

  @Test
  @DisplayName("Om intyget inte är tillgängligt på 1177 ska intyget inte gå att skicka")
  void shallNotSendCertificateIfNotAvailableForCitizen() {
    if (availableForCitizen()) {
      return;
    }
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api.sendCitizenCertificate(
        SendCitizenCertificateRequest.builder()
            .personId(PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(ATHENA_REACT_ANDERSSON_ID)
                .build())
            .build(), certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan invånare ska felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfNotIssuedOnCitizen() {
    if (!availableForCitizen()) {
      return;
    }
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api.sendCitizenCertificate(
        SendCitizenCertificateRequest.builder()
            .personId(PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(ALVE_REACT_ALFREDSSON_ID)
                .build())
            .build(), certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget redan är skickat ska felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfCertificateAlreadySent() {
    if (!availableForCitizen()) {
      return;
    }
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api.sendCertificate(
        defaultSendCertificateRequest(), certificateId(testCertificates)
    );

    final var response = api.sendCitizenCertificate(
        SendCitizenCertificateRequest.builder()
            .personId(PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(ALVE_REACT_ALFREDSSON_ID)
                .build())
            .build(), certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }
}