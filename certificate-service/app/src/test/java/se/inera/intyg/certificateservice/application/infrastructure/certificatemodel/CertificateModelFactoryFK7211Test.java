package se.inera.intyg.certificateservice.application.infrastructure.certificatemodel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.model.CertificateModelId;
import se.inera.intyg.certificateservice.model.CertificateType;
import se.inera.intyg.certificateservice.model.CertificateVersion;

class CertificateModelFactoryFK7211Test {

  private static final String FK_7211 = "fk7211";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK7211 certificateModelFactoryFK7211;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7211 = new CertificateModelFactoryFK7211();
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(FK_7211))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertEquals(expectedId, certificateModel.getId());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Intyg om graviditet";

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertEquals(expectedName, certificateModel.getName());
  }

  @Test
  void shallIncludeDescription() {
    final var expectedDescription = "Intyg om graviditet "
        + "Ungefär i vecka 20 får du ett intyg om graviditet av barnmorskan. Intyget anger "
        + "också datum för beräknad förlossning. Intyget skickar du till Försäkringskassan, "
        + "som ger besked om kommande föräldrapenning.";

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertEquals(expectedDescription, certificateModel.getDescription());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(certificateModelFactoryFK7211, "activeFrom",
        expectedActiveFrom);

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertEquals(expectedActiveFrom, certificateModel.getActiveFrom());
  }
}
