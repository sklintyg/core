package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.SCHEMATRON_PATH;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;

@ExtendWith(MockitoExtension.class)
class CertificateModelFactoryFK3226Test {

  private static final String TYPE = "fk3226";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK3226 certificateModelFactoryFK3226;
  private static final String LOGICAL_ADDRESS = "L-A";

  @Mock
  private CertificateActionFactory certificateActionFactory;
  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK3226 = new CertificateModelFactoryFK3226(diagnosisCodeRepository,
        certificateActionFactory);

    ReflectionTestUtils.setField(certificateModelFactoryFK3226, "fkLogicalAddress",
        LOGICAL_ADDRESS);
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shouldIncludeTypeName() {
    final var expected = new CertificateTypeName("FK3226");

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(expected, certificateModel.typeName());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Läkarutlåtande för närståendepenning";

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryFK3226,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.availableForCitizen());
  }

  @Test
  void shallIncludeCertificateSummaryProvider() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(FK3226CertificateSummaryProvider.class,
        certificateModel.summaryProvider().getClass());
  }

  @Test
  void shallIncludeCertificateText() {
    final var expectedText = CertificateText.builder()
        .text(
            "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. Har du frågor kontaktar du den som skrivit ditt intyg.")
        .type(CertificateTextType.PREAMBLE_TEXT)
        .links(Collections.emptyList())
        .build();

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(List.of(expectedText), certificateModel.texts());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan",
        LOGICAL_ADDRESS
    );

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeSchematronPath() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(SCHEMATRON_PATH, certificateModel.schematronPath());
  }

  @Test
  void shallIncludePdfSpecifications() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertNotNull(certificateModel.pdfSpecification());
  }

  @Test
  void shallIncludeCertificateActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shallIncludeMessageActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertAll(
        () -> assertNotNull(certificateModel.messageActionSpecifications()),
        () -> assertFalse(certificateModel.messageActionSpecifications().isEmpty())
    );
  }

  @Nested
  class CertificateSpecifications {

    @Test
    void shallIncludeCategoryGrund() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_1"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeUtlatandeBaseratPa() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("1"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeUtlatandeBaseratPaAnnat() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("1.3")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("1.3"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryHot() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_2")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_2"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeDiagnos() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("58")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("58"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludePatientBehandlingOchVardsituation() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionNarAktivaBehandlingenAvslutades() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52.2")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52.2"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionNarTillstandetBlevAkutLivshotande() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52.3")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52.3"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionPatagligtHotMotPatientensLivAkutLivshotande() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52.4")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52.4"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionUppskattaHurLangeTillstandetKommerVaraLivshotande() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52.5")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52.5"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionTillstandetUppskattasLivshotandeTillOchMed() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52.6")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52.6"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionPatagligtHotMotPatientensLivAnnat() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52.7")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52.7"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategorySamtycke() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_3")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_3"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionForutsattningarForAttLamnaSkriftligtSamtycke() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("53")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("53"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeMessageForutsattningarForAttLamnaSkriftligtSamtycke() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("forutsattningar")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("forutsattningar"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeIssuingUnitContactInfo() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(
          certificateModel.elementSpecificationExists(new ElementId("UNIT_CONTACT_INFORMATION")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'"
              .formatted(new ElementId("UNIT_CONTACT_INFORMATION"),
                  certificateModel.elementSpecifications())
      );
    }
  }

  @Test
  void shallIncludeCertificateActionFactory() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(certificateActionFactory, certificateModel.certificateActionFactory());
  }
}
