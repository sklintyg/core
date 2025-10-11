package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateModelIdUtil.certificateModelId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateTypeInfoUtil.certificateTypeInfo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
