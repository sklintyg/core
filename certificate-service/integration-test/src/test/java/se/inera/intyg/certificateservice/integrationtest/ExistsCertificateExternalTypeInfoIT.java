package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateModelIdUtil.certificateModelId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;

public abstract class ExistsCertificateExternalTypeInfoIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  protected abstract String codeSystem();

  protected abstract String code();


  @Test
  @DisplayName("Skall returnera den senaste aktiva versionen om intygstypen med bifogat kodsystem existerar")
  void shallReturnLatestVersionWhenExternalTypeExists() {
    final var expectedCertificateModelId = CertificateModelIdDTO.builder()
        .type(type())
        .version(typeVersion())
        .build();

    final var response = api.findLatestCertificateExternalTypeVersion(
        codeSystem(), code()
    );

    assertEquals(
        expectedCertificateModelId,
        certificateModelId(response.getBody())
    );
  }

  @Test
  @DisplayName("Skall returnera ett tomt svar om intygstypen finns men det bifogade kodsystemet matchar inte")
  void shallReturnEmptyResponseIfCodeSystemDontMatch() {
    final var response = api.findLatestCertificateExternalTypeVersion(
        "invalidCodeSystem", code()
    );

    assertNull(certificateModelId(response.getBody()));
  }

  @Test
  @DisplayName("Skall returnera ett tomt svar om intygstypen inte finns")
  void shallReturnEmptyResponseIfCodeDontMatch() {
    final var response = api.findLatestCertificateExternalTypeVersion(
        codeSystem(), "invalidCode"
    );

    assertNull(certificateModelId(response.getBody()));
  }
}
