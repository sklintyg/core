package se.inera.intyg.certificateservice.integrationtest.ts8071.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customRenewCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customReplaceCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customRevokeCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customUpdateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.ts8071.v1.TS8071TestSetup.ts8071TestSetup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.ActiveCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityUtilities;

class TS8071V1CompletelyInactiveIT extends ActiveCertificatesIT {

  private BaseTestabilityUtilities v1Utilities;

  @DynamicPropertySource
  static void configureLimitConfig(DynamicPropertyRegistry registry) {
    registry.add("limited.certificate.functionality.configuration.path",
        () -> "classpath:/config/limited-certificate-functionality-config-inactive.json");
  }

  @BeforeEach
  void setUp() {
    setUpBaseIT();

    v1Utilities = ts8071TestSetup()
        .testabilityUtilities(
            TestabilityUtilities.builder()
                .api(api)
                .internalApi(internalApi)
                .testabilityApi(testabilityApi)
                .build()
        )
        .build();
  }

  @AfterEach
  void tearDown() {
    tearDownBaseIT();
  }

  @Test
  @DisplayName("Ska inte kunna uppdatera V1-intyg när det är helt inaktiverat")
  void shallNotUpdateV1CertificateWhenCompletelyInactive() {
    final var testCertificates = v1Utilities.getTestabilityUtilities().getTestabilityApi()
        .addCertificates(
            defaultTestablilityCertificateRequest(
                v1Utilities.getTestabilityCertificate().getType(),
                v1Utilities.getTestabilityCertificate().getActiveVersion()
            )
        );

    final var certificate = certificate(testCertificates);

    final var response = v1Utilities.getTestabilityUtilities().getApi().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Ska inte ha uppdatera-resurslänk för V1-intyg när det är helt inaktiverat")
  void shallNotHaveUpdateActionForV1() {
    final var testCertificates = v1Utilities.getTestabilityUtilities().getTestabilityApi()
        .addCertificates(
            defaultTestablilityCertificateRequest(
                v1Utilities.getTestabilityCertificate().getType(),
                v1Utilities.getTestabilityCertificate().getActiveVersion()
            )
        );

    final var certificate = certificate(testCertificates);

    final var hasUpdateAction = certificate.getLinks().stream()
        .anyMatch(link -> ResourceLinkTypeDTO.EDIT_CERTIFICATE.equals(link.getType()));

    assertFalse(hasUpdateAction,
        "V1-intyg ska INTE ha EDIT_CERTIFICATE-resurslänk tillgänglig när det är helt inaktiverat");
  }

  @Test
  @DisplayName("Ska inte kunna signera V1-intyg när det är helt inaktiverat")
  void shallNotSignV1CertificateWhenCompletelyInactive() {
    final var testCertificates = v1Utilities.getTestabilityUtilities().getTestabilityApi()
        .addCertificates(
            defaultTestablilityCertificateRequest(
                v1Utilities.getTestabilityCertificate().getType(),
                v1Utilities.getTestabilityCertificate().getActiveVersion()
            )
        );

    final var certificate = certificate(testCertificates);

    final var response = v1Utilities.getTestabilityUtilities().getApi().signCertificate(
        customSignCertificateRequest().build(),
        certificateId(testCertificates),
        certificate.getMetadata().getVersion()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Ska inte ha signera-resurslänk för V1-intyg när det är helt inaktiverat")
  void shallNotHaveSignActionForV1() {
    final var testCertificates = v1Utilities.getTestabilityUtilities().getTestabilityApi()
        .addCertificates(
            defaultTestablilityCertificateRequest(
                v1Utilities.getTestabilityCertificate().getType(),
                v1Utilities.getTestabilityCertificate().getActiveVersion()
            )
        );

    final var certificate = certificate(testCertificates);

    final var hasSignAction = certificate.getLinks().stream()
        .anyMatch(link -> ResourceLinkTypeDTO.SIGN_CERTIFICATE.equals(link.getType()));

    assertFalse(hasSignAction,
        "Inaktiverat intyg ska INTE ha SIGN_CERTIFICATE-resurslänk tillgänglig när det är helt inaktiverat");
  }

  @Test
  @DisplayName("Ska inte kunna skicka inaktiverat intyg när det är helt inaktiverat")
  void shallNotSendV1CertificateWhenCompletelyInactive() {
    final var testCertificates = v1Utilities.getTestabilityUtilities().getTestabilityApi()
        .addCertificates(
            defaultTestablilityCertificateRequest(
                v1Utilities.getTestabilityCertificate().getType(),
                v1Utilities.getTestabilityCertificate().getActiveVersion()
            )
        );

    final var response = v1Utilities.getTestabilityUtilities().getApi().sendCertificate(
        customSendCertificateRequest().build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Ska inte ha skicka-resurslänk för inaktiverat intyg när det är helt inaktiverat")
  void shallNotHaveSendActionForV1() {
    final var testCertificates = v1Utilities.getTestabilityUtilities().getTestabilityApi()
        .addCertificates(
            defaultTestablilityCertificateRequest(
                v1Utilities.getTestabilityCertificate().getType(),
                v1Utilities.getTestabilityCertificate().getActiveVersion()
            )
        );

    final var certificate = certificate(testCertificates);

    final var hasSendAction = certificate.getLinks().stream()
        .anyMatch(link -> ResourceLinkTypeDTO.SEND_CERTIFICATE.equals(link.getType()));

    assertFalse(hasSendAction,
        "Inaktiverat intyg ska INTE ha SEND_CERTIFICATE-resurslänk tillgänglig när det är helt inaktiverat");
  }

  @Test
  @DisplayName("Ska inte kunna makulera inaktiverat intyg när det är helt inaktiverat")
  void shallNotRevokeV1CertificateWhenCompletelyInactive() {
    final var testCertificates = v1Utilities.getTestabilityUtilities().getTestabilityApi()
        .addCertificates(
            defaultTestablilityCertificateRequest(
                v1Utilities.getTestabilityCertificate().getType(),
                v1Utilities.getTestabilityCertificate().getActiveVersion()
            )
        );

    final var response = v1Utilities.getTestabilityUtilities().getApi().revokeCertificate(
        customRevokeCertificateRequest().build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Ska inte ha makulera-resurslänk för inaktiverat intyg när det är helt inaktiverat")
  void shallNotHaveRevokeActionForV1() {
    final var testCertificates = v1Utilities.getTestabilityUtilities().getTestabilityApi()
        .addCertificates(
            defaultTestablilityCertificateRequest(
                v1Utilities.getTestabilityCertificate().getType(),
                v1Utilities.getTestabilityCertificate().getActiveVersion()
            )
        );

    final var certificate = certificate(testCertificates);

    final var hasRevokeAction = certificate.getLinks().stream()
        .anyMatch(link -> ResourceLinkTypeDTO.REVOKE_CERTIFICATE.equals(link.getType()));

    assertFalse(hasRevokeAction,
        "Inaktiverat intyg ska INTE ha REVOKE_CERTIFICATE-resurslänk tillgänglig när det är helt inaktiverat");
  }

  @Test
  @DisplayName("Ska inte kunna ersätta inaktiverat intyg när det är helt inaktiverat")
  void shallNotReplaceV1CertificateWhenCompletelyInactive() {
    final var testCertificates = v1Utilities.getTestabilityUtilities().getTestabilityApi()
        .addCertificates(
            defaultTestablilityCertificateRequest(
                v1Utilities.getTestabilityCertificate().getType(),
                v1Utilities.getTestabilityCertificate().getActiveVersion()
            )
        );

    final var response = v1Utilities.getTestabilityUtilities().getApi().replaceCertificate(
        customReplaceCertificateRequest().build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Ska inte ha ersätta-resurslänk för inaktiverat intyg när det är helt inaktiverat")
  void shallNotHaveReplaceActionForV1() {
    final var testCertificates = v1Utilities.getTestabilityUtilities().getTestabilityApi()
        .addCertificates(
            defaultTestablilityCertificateRequest(
                v1Utilities.getTestabilityCertificate().getType(),
                v1Utilities.getTestabilityCertificate().getActiveVersion()
            )
        );

    final var certificate = certificate(testCertificates);

    final var hasReplaceAction = certificate.getLinks().stream()
        .anyMatch(link -> ResourceLinkTypeDTO.REPLACE_CERTIFICATE.equals(link.getType()));

    assertFalse(hasReplaceAction,
        "Inaktiverat intyg ska INTE ha REPLACE_CERTIFICATE-resurslänk tillgänglig när det är helt inaktiverat");
  }

  @Test
  @DisplayName("Ska inte kunna förnya inaktiverat intyg när det är helt inaktiverat")
  void shallNotRenewV1CertificateWhenCompletelyInactive() {
    final var testCertificates = v1Utilities.getTestabilityUtilities().getTestabilityApi()
        .addCertificates(
            defaultTestablilityCertificateRequest(
                v1Utilities.getTestabilityCertificate().getType(),
                v1Utilities.getTestabilityCertificate().getActiveVersion()
            )
        );

    final var response = v1Utilities.getTestabilityUtilities().getApi().renewCertificate(
        customRenewCertificateRequest().build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Ska inte ha förnya-resurslänk för inaktiverat intyg när det är helt inaktiverat")
  void shallNotHaveRenewActionForV1() {
    final var testCertificates = v1Utilities.getTestabilityUtilities().getTestabilityApi()
        .addCertificates(
            defaultTestablilityCertificateRequest(
                v1Utilities.getTestabilityCertificate().getType(),
                v1Utilities.getTestabilityCertificate().getActiveVersion()
            )
        );

    final var certificate = certificate(testCertificates);

    final var hasRenewAction = certificate.getLinks().stream()
        .anyMatch(link -> ResourceLinkTypeDTO.RENEW_CERTIFICATE.equals(link.getType()));

    assertFalse(hasRenewAction,
        "Inaktiverat intyg ska INTE ha RENEW_CERTIFICATE-resurslänk tillgänglig när det är helt inaktiverat");
  }
}