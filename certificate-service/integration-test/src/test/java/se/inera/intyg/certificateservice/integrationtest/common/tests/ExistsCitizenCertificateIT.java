package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.exists;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class ExistsCitizenCertificateIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Om intyget finns så returneras true")
  void shallReturnTrueIfCitizenCertificateExists() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().findExistingCitizenCertificate(
        certificateId(testCertificates)
    );

    assertTrue(
        exists(response.getBody()),
        "Should return true when certificate exists!"
    );
  }

  @Test
  @DisplayName("Om intyget inte finns lagrat så returneras false")
  void shallReturnFalseIfCitizenCertificateDoesnt() {
    final var response = api().findExistingCitizenCertificate("certificate-not-exists");

    assertFalse(
        exists(response.getBody()),
        "Should return false when certificate doesnt exists!"
    );
  }

}
