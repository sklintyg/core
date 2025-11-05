package se.inera.intyg.certificateservice.integrationtest.ts8071.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

class TS8071V1LimitedFunctionalityIT extends ActiveCertificatesIT {

  private BaseTestabilityUtilities v1Utilities;

  @DynamicPropertySource
  static void configureLimitConfig(DynamicPropertyRegistry registry) {
    registry.add("limited.certificate.functionality.configuration.path",
        () -> "classpath:/config/limited-certificate-functionality-config.json");
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
  @DisplayName("Should be able to update the V1 certificate with limited functionality before expiration.")
  void shallUpdateV1CertificateBeforeLimitedFunctionalityExpired() {
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

    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  @DisplayName("shall have update action for V1 certificate when limited functionality before expiration")
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

    assertTrue(hasUpdateAction,
        "V1 certificate should have EDIT_CERTIFICATE action available when completely limited functionality before expiration date");
  }
}

