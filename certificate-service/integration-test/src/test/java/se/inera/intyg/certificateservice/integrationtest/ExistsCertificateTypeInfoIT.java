package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateModelIdUtil.certificateModelId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;

public abstract class ExistsCertificateTypeInfoIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  @Test
  @DisplayName("Aktiv version skall vara senaste version")
  void shallReturnLatestVersionWhenTypeExists() {
    final var expectedCertificateModelId = CertificateModelIdDTO.builder()
        .type(type())
        .version(typeVersion())
        .build();

    final var response = api.findLatestCertificateTypeVersion(type());

    assertEquals(
        expectedCertificateModelId,
        certificateModelId(response.getBody())
    );
  }
}
