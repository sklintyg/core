package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customUpdateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultDeleteCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetCertificatePdfRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultRenewCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultReplaceCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultRevokeCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateModelIdUtil.certificateModelId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateTypeInfoUtil.certificateTypeInfo;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class InactiveTypeIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Inaktivt intyg skall ej visas i listan med tillgängliga intygstyper")
  void shallNotReturnInactiveCertificate() {
    final var response = api().certificateTypeInfo(
        defaultCertificateTypeInfoRequest()
    );

    assertNull(
        certificateTypeInfo(response.getBody(), type()),
        "Should not contain %s as it is not active!".formatted(type())
    );
  }

  @Test
  @DisplayName("Om intygstyp inte är aktiverad skall ingen version returneras")
  void shallReturnEmptyWhenTypeIsNotActive() {
    final var response = api().findLatestCertificateTypeVersion(type());

    assertNull(
        certificateModelId(response.getBody()),
        () -> "Expected that no version should be returned but instead returned %s"
            .formatted(certificateModelId(response.getBody()))
    );
  }

  @Test
  @DisplayName("Om utkast av inaktivt intyg skapas skall felkod 400 (BAD_REQUEST) returneras")
  void shallReturn400WhenTypeIsNotActive() {
    final var response = api().createCertificate(
        defaultCreateCertificateRequest(type(), typeVersion())
    );

    assertEquals(400, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om utkast av inaktivt intyg försöker redigeras skall felkod 403 (FORBIDDEN) returneras")
  void shallNotBeAbleToEditInactiveCertificateDraft() {
    final var testCertificate = testabilityApi().addCertificate(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = certificate(testCertificate.getBody());

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .build(),
        certificateId(testCertificate.getBody())
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om utkast av inaktivt intyg försöker signeras skall felkod 403 (FORBIDDEN) returneras")
  void shallNotBeAbleToSignInactiveCertificateDraft() {
    final var testCertificate = testabilityApi().addCertificate(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = certificate(testCertificate.getBody());

    assertNotNull(certificate);

    final var response = api().signCertificate(
        defaultSignCertificateRequest(),
        certificate.getMetadata().getId(),
        certificate.getMetadata().getVersion()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om inaktivt intyg försöker ersättas skall felkod 403 (FORBIDDEN) returneras")
  void shallNotBeAbleToReplaceInactiveCertificateDraft() {
    final var testCertificate = testabilityApi().addCertificate(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = certificate(testCertificate.getBody());

    assertNotNull(certificate);

    final var response = api().replaceCertificate(
        defaultReplaceCertificateRequest(),
        certificate.getMetadata().getId()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om inaktivt intyg försöker förnyas skall felkod 403 (FORBIDDEN) returneras")
  void shallNotBeAbleToRenewInactiveCertificateDraft() {
    final var testCertificate = testabilityApi().addCertificate(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = certificate(testCertificate.getBody());

    assertNotNull(certificate);

    final var response = api().renewCertificate(
        defaultRenewCertificateRequest(),
        certificate.getMetadata().getId()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Det skall gå att läsa ett inaktivt intyg")
  void shallBeAbleToReadInactiveCertificate() {
    final var testCertificate = testabilityApi().addCertificate(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getCertificate(
        defaultGetCertificateRequest(),
        certificateId(testCertificate.getBody())
    );

    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Det skall gå att radera ett utkast av inaktivt intyg")
  void shallBeAbleToDeleteInactiveCertificate() {
    final var testCertificate = testabilityApi().addCertificate(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = certificate(testCertificate.getBody());

    assertNotNull(certificate);

    final var response = api().deleteCertificate(
        defaultDeleteCertificateRequest(),
        certificate.getMetadata().getId(),
        certificate.getMetadata().getVersion()
    );

    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Det skall gå att makulera ett signerat inaktivt intyg")
  void shallBeAbleToRevokeSignedInactiveCertificate() {
    final var testCertificate = testabilityApi().addCertificate(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    final var certificate = certificate(testCertificate.getBody());

    assertNotNull(certificate);

    final var response = api().revokeCertificate(
        defaultRevokeCertificateRequest(),
        certificate.getMetadata().getId()
    );

    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Det skall gå att skriva ut ett inaktivt intyg")
  void shallBeAbleToPrintInactiveCertificate() {
    final var testCertificate = testabilityApi().addCertificate(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    final var certificate = certificate(testCertificate.getBody());

    assertNotNull(certificate);

    final var response = api().getCertificatePdf(
        defaultGetCertificatePdfRequest(),
        certificate.getMetadata().getId()
    );

    assertEquals(200, response.getStatusCode().value());
  }
}